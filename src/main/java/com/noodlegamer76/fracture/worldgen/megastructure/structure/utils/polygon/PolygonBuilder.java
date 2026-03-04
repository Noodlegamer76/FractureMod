package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PolygonBuilder {
    private final Vec3 origin;
    private final List<Vec3> vertices = new ArrayList<>();

    public PolygonBuilder(Vec3 origin) {
        this.origin = origin;
    }

    public void addNextVertex(Vec3 vertex) {
        vertices.add(vertex);
    }

    @Nullable
    public Polygon build() {
        if (vertices.size() < 2) {
            FractureMod.LOGGER.error("Polygon must have at least 2 vertices");
            return null;
        }

        List<Vec3> allVerts = new ArrayList<>();
        allVerts.add(origin);
        allVerts.addAll(vertices);

        boolean ccw = computeSignedArea(allVerts) > 0;

        List<Polygon.Edge> edges = new ArrayList<>();
        List<Vec3> vertList = new ArrayList<>(allVerts);

        for (int i = 0; i < allVerts.size(); i++) {
            Vec3 start = allVerts.get(i);
            Vec3 end = allVerts.get((i + 1) % allVerts.size());

            Vec3 dir = end.subtract(start);

            int angleDegrees = Math.round((float) Math.toDegrees(Math.atan2(dir.z, dir.x)));

            if (angleDegrees == -180) angleDegrees = 180;

            Vec3 normal;
            if (ccw) {
                normal = new Vec3(-dir.z, 0, dir.x);
            } else {
                normal = new Vec3(dir.z, 0, -dir.x);
            }

            normal = normal.normalize();

            edges.add(new Polygon.Edge(start, end, normal, angleDegrees));
        }

        Vec3 centroid = computeCentroid(allVerts);
        if (centroid == null) return null;

        return new Polygon(edges, vertList, centroid);
    }

    private double computeSignedArea(List<Vec3> verts) {
        double area = 0;
        for (int i = 0; i < verts.size(); i++) {
            Vec3 v1 = verts.get(i);
            Vec3 v2 = verts.get((i + 1) % verts.size());
            area += (v1.x * v2.z) - (v2.x * v1.z);
        }
        return area * 0.5;
    }

    public Vec3 computeCentroid() {
        return computeCentroid(vertices);
    }

    public Vec3 computeCentroid(List<Vec3> verts) {
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

        if (Math.abs(signedArea) < 1e-8) {
            return null;
        }

        cx /= (6.0 * signedArea);
        cz /= (6.0 * signedArea);

        return new Vec3(cx, 0, cz);
    }

    /**
     * Checks if the new edge (a -> b) intersects any existing edge.
     */
    public boolean intersectsExistingEdge(Vec3 a, Vec3 b) {
        for (int i = 0; i < vertices.size() - 2; i++) {
            Vec3 p1 = vertices.get(i);
            Vec3 p2 = vertices.get(i + 1);

            if (linesIntersect(a.x, a.z, b.x, b.z, p1.x, p1.z, p2.x, p2.z)) {
                return true;
            }
        }
        return false;
    }

    private boolean linesIntersect(double x1, double y1, double x2, double y2,
                                   double x3, double y3, double x4, double y4) {
        double denom = (y4 - y3)*(x2 - x1) - (x4 - x3)*(y2 - y1);
        if (denom == 0) return false;

        double ua = ((x4 - x3)*(y1 - y3) - (y4 - y3)*(x1 - x3)) / denom;
        double ub = ((x2 - x1)*(y1 - y3) - (y2 - y1)*(x1 - x3)) / denom;

        return ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1;
    }


    public Vec3 getOrigin() {
        return origin;
    }

    public Vec3 getLastVertex() {
        if (vertices.isEmpty()) return origin;
        return vertices.get(vertices.size() - 1);
    }

    public Vec3 getPrevVertex() {
        if (vertices.size() < 2) return origin;
        return vertices.get(vertices.size() - 2);
    }

    public int size() {
        return vertices.size();
    }

    public void removeLastVertex() {
        vertices.remove(vertices.size() - 1);
    }
}