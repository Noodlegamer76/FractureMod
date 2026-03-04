package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record Polygon(List<Edge> edges, List<Vec3> vertices, Vec3 center) {

    public Polygon removeCollinearVertices(double epsilon) {
        List<Vec3> verts = new ArrayList<>(vertices());
        if (verts.size() < 3) return this;

        List<Vec3> cleaned = new ArrayList<>();
        int n = verts.size();

        for (int i = 0; i < n; i++) {
            Vec3 prev = verts.get((i - 1 + n) % n);
            Vec3 curr = verts.get(i);
            Vec3 next = verts.get((i + 1) % n);

            Vec3 d1 = curr.subtract(prev);
            Vec3 d2 = next.subtract(curr);

            double cross = (d1.x * d2.z) - (d1.z * d2.x);
            double dot = d1.x * d2.x + d1.z * d2.z;

            if (Math.abs(cross) > epsilon || dot <= 0) {
                cleaned.add(curr);
            }
        }

        List<Polygon.Edge> edges = new ArrayList<>();
        List<Vec3> vertSet = new ArrayList<>(cleaned);

        boolean ccw = computeSignedArea(cleaned) > 0;
        for (int i = 0; i < cleaned.size(); i++) {
            Vec3 start = cleaned.get(i);
            Vec3 end = cleaned.get((i + 1) % cleaned.size());

            Vec3 dir = end.subtract(start);
            int angleDegrees = Math.round((float) Math.toDegrees(Math.atan2(dir.z, dir.x)));
            if (angleDegrees == -180) angleDegrees = 180;

            Vec3 normal = ccw ? new Vec3(-dir.z, 0, dir.x).normalize() : new Vec3(dir.z, 0, -dir.x).normalize();
            edges.add(new Polygon.Edge(start, end, normal, angleDegrees));
        }

        Vec3 centroid = computeCentroid(cleaned);
        if (centroid == null) centroid = new Vec3(0,0,0);

        return new Polygon(edges, vertSet, centroid);
    }

    private static double computeSignedArea(List<Vec3> verts) {
        double area = 0;
        for (int i = 0; i < verts.size(); i++) {
            Vec3 v1 = verts.get(i);
            Vec3 v2 = verts.get((i + 1) % verts.size());
            area += (v1.x * v2.z) - (v2.x * v1.z);
        }
        return area * 0.5;
    }

    private static Vec3 computeCentroid(List<Vec3> verts) {
        double signedArea = 0.0;
        double cx = 0.0;
        double cz = 0.0;

        for (int i = 0; i < verts.size(); i++) {
            Vec3 v0 = verts.get(i);
            Vec3 v1 = verts.get((i + 1) % verts.size());
            double cross = (v0.x * v1.z) - (v1.x * v0.z);
            signedArea += cross;
            cx += (v0.x + v1.x) * cross;
            cz += (v0.z + v1.z) * cross;
        }

        signedArea *= 0.5;
        if (Math.abs(signedArea) < 1e-8) return null;

        cx /= (6.0 * signedArea);
        cz /= (6.0 * signedArea);

        return new Vec3(cx, 0, cz);
    }

    public record Edge(Vec3 start, Vec3 end, Vec3 normal, int angleDegrees, double length) {
        public Edge(Vec3 start, Vec3 end, Vec3 normal, int angleDegrees) {
            this(start, end, normal, angleDegrees, end.distanceTo(start));
        }
    }
}
