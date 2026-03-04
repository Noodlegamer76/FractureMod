package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class WallGenerator {

    public static Polygon generate(Settings settings, RandomSource randomSource) {
        Random random = new Random();
        random.setSeed(randomSource.nextLong());

        double minX = settings.minX(), minZ = settings.minZ();
        double maxX = settings.maxX(), maxZ = settings.maxZ();
        double edgeLength = settings.edgeLength();
        double snapStep = Math.toRadians(settings.angle());
        int numDirs = (int) Math.round((Math.PI * 2.0) / snapStep);
        int opposite = numDirs / 2;

        Vec3 origin = snap(new Vec3((minX + maxX) * 0.5, 0, (minZ + maxZ) * 0.5), edgeLength);
        Vec3 current = origin;
        int currentDirIndex = random.nextInt(numDirs);

        Set<String> usedEdges = new HashSet<>();
        Map<String, Integer> degree = new HashMap<>();
        PolygonBuilder builder = new PolygonBuilder(origin);
        int edgeCount = 0;
        int consecutiveTurns = 0;

        for (int step = 0; step < settings.maxTries(); step++) {
            double progress = (double) step / settings.maxTries();

            boolean canClose = edgeCount >= 4 && canReachOrigin(current, origin, numDirs, snapStep, edgeLength, usedEdges);
            if (canClose) {
                float closeChance = progress > 0.75f ? 1.0f : (float) (progress * 0.2);
                if (random.nextFloat() < closeChance) {
                    stepOntoOrigin(current, origin, numDirs, snapStep, edgeLength, usedEdges, degree);
                    return builder.build();
                }
            }

            int[] dirOrder = progress > 0.55
                    ? homingDirectionOrder(numDirs, currentDirIndex, current, origin, snapStep, edgeLength, consecutiveTurns, opposite, usedEdges, random)
                    : exploringDirectionOrder(numDirs, currentDirIndex, consecutiveTurns, opposite, snapStep, edgeLength, current, usedEdges, random);

            Vec3 chosen = null;
            int chosenDir = currentDirIndex;

            for (int dir : dirOrder) {
                Vec3 candidate = snap(moveInDir(current, dir, snapStep, edgeLength), edgeLength);
                String candKey = vertKey(candidate);

                if (candKey.equals(vertKey(origin)) && edgeCount < 4) continue;
                if (degree.getOrDefault(candKey, 0) >= 2) continue;
                if (!inBounds(candidate, minX, minZ, maxX, maxZ)) continue;
                if (usedEdges.contains(edgeKey(current, candidate))) continue;

                chosen = candidate;
                chosenDir = dir;
                break;
            }

            if (chosen == null) return null;

            boolean turned = chosenDir != currentDirIndex;
            consecutiveTurns = turned ? consecutiveTurns + 1 : 0;

            usedEdges.add(edgeKey(current, chosen));
            degree.merge(vertKey(current), 1, Integer::sum);
            degree.merge(vertKey(chosen), 1, Integer::sum);
            edgeCount++;

            if (vertKey(chosen).equals(vertKey(origin))) {
                return builder.build();
            }

            builder.addNextVertex(chosen);
            currentDirIndex = chosenDir;
            current = chosen;
        }

        return null;
    }

    private static void stepOntoOrigin(Vec3 current, Vec3 origin, int numDirs, double snapStep,
                                       double edgeLength, Set<String> usedEdges, Map<String, Integer> degree) {
        String origKey = vertKey(origin);
        for (int dir = 0; dir < numDirs; dir++) {
            Vec3 candidate = snap(moveInDir(current, dir, snapStep, edgeLength), edgeLength);
            if (vertKey(candidate).equals(origKey) && !usedEdges.contains(edgeKey(current, candidate))) {
                usedEdges.add(edgeKey(current, candidate));
                degree.merge(vertKey(current), 1, Integer::sum);
                degree.merge(vertKey(candidate), 1, Integer::sum);
                return;
            }
        }
    }

    private static int[] exploringDirectionOrder(int numDirs, int currentDir, int consecutiveTurns,
                                                 int opposite, double snapStep, double edgeLength, Vec3 current, Set<String> usedEdges, Random random) {
        List<Integer> weighted = new ArrayList<>();
        for (int delta = 0; delta < numDirs; delta++) {
            int absDir = (currentDir + delta + numDirs) % numDirs;
            int minDelta = Math.min(delta, numDirs - delta);

            if (minDelta == opposite) continue;

            boolean straightIsParallel = hasParallelAdjacentEdge(current, absDir, numDirs, snapStep, edgeLength, usedEdges);

            int weight;
            if (minDelta == 0) {
                weight = straightIsParallel ? 1 : (consecutiveTurns > 0 ? 24 : 15);
            } else if (minDelta == 1) {
                weight = straightIsParallel ? 12 : (consecutiveTurns >= 2 ? 1 : 2);
            } else {
                weight = 1;
            }

            for (int w = 0; w < weight; w++) weighted.add(absDir);
        }
        return shuffle(weighted, random);
    }

    private static int[] homingDirectionOrder(int numDirs, int currentDir, Vec3 current, Vec3 origin,
                                              double snapStep, double edgeLength, int consecutiveTurns, int opposite, Set<String> usedEdges, Random random) {
        double currentDist = current.distanceTo(origin);
        List<Integer> weighted = new ArrayList<>();

        for (int dir = 0; dir < numDirs; dir++) {
            int delta = Math.min(Math.abs(dir - currentDir), numDirs - Math.abs(dir - currentDir));
            int minDelta = Math.min(delta, numDirs - delta);

            if (minDelta == opposite) continue;

            Vec3 next = snap(moveInDir(current, dir, snapStep, edgeLength), edgeLength);
            double nextDist = next.distanceTo(origin);

            boolean straightIsParallel = minDelta == 0 && hasParallelAdjacentEdge(current, dir, numDirs, snapStep, edgeLength, usedEdges);

            int weight;
            if (minDelta == 0) {
                weight = straightIsParallel ? 1 : (consecutiveTurns > 0 ? 24 : 15);
            } else if (minDelta == 1) {
                weight = straightIsParallel ? 12 : (consecutiveTurns >= 2 ? 1 : 2);
            } else {
                weight = 1;
            }

            if (nextDist < currentDist) weight += 4;

            for (int w = 0; w < weight; w++) weighted.add(dir);
        }

        return shuffle(weighted, random);
    }

    private static boolean hasParallelAdjacentEdge(Vec3 from, int dir, int numDirs,
                                                   double snapStep, double edgeLength, Set<String> usedEdges) {
        int perpCw  = (dir + 1) % numDirs;
        int perpCcw = (dir - 1 + numDirs) % numDirs;

        for (int perp : new int[]{ perpCw, perpCcw }) {
            Vec3 sideFrom = snap(moveInDir(from, perp, snapStep, edgeLength), edgeLength);
            Vec3 sideTo   = snap(moveInDir(sideFrom, dir, snapStep, edgeLength), edgeLength);
            if (usedEdges.contains(edgeKey(sideFrom, sideTo))) return true;
        }
        return false;
    }

    private static int[] shuffle(List<Integer> weighted, Random random) {
        Collections.shuffle(weighted, random);
        List<Integer> deduped = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (int d : weighted) if (seen.add(d)) deduped.add(d);
        return deduped.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean canReachOrigin(Vec3 current, Vec3 origin, int numDirs,
                                          double snapStep, double edgeLength, Set<String> usedEdges) {
        String origKey = vertKey(origin);
        for (int dir = 0; dir < numDirs; dir++) {
            Vec3 candidate = snap(moveInDir(current, dir, snapStep, edgeLength), edgeLength);
            if (vertKey(candidate).equals(origKey) && !usedEdges.contains(edgeKey(current, candidate)))
                return true;
        }
        return false;
    }

    private static Vec3 moveInDir(Vec3 from, int dirIndex, double snapStep, double edgeLength) {
        double angle = dirIndex * snapStep;
        return new Vec3(from.x + Math.cos(angle) * edgeLength, 0, from.z + Math.sin(angle) * edgeLength);
    }

    private static Vec3 snap(Vec3 v, double step) {
        return new Vec3(Math.round(v.x / step) * step, 0, Math.round(v.z / step) * step);
    }

    private static boolean inBounds(Vec3 v, double minX, double minZ, double maxX, double maxZ) {
        return v.x >= minX && v.x <= maxX && v.z >= minZ && v.z <= maxZ;
    }

    private static String edgeKey(Vec3 a, Vec3 b) {
        String ka = vertKey(a), kb = vertKey(b);
        return ka.compareTo(kb) <= 0 ? ka + "|" + kb : kb + "|" + ka;
    }

    private static String vertKey(Vec3 v) {
        return Math.round(v.x * 1000) + "," + Math.round(v.z * 1000);
    }

    public record Settings(int minX, int minZ, int maxX, int maxZ, double edgeLength, int angle, int maxTries) {}
}