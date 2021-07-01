package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leo
 */
public class Plane implements Serializable {
    
    public Vec3 normal;
    public Vec3 point;

    public Plane(Vec3 normal, Vec3 point) {
        this.normal = normal;
        this.point = point;
    }

    public Plane(double nx, double ny, double nz
            , double px, double py, double pz) {
        
        this.normal = new Vec3(nx, ny, nz);
        this.point = new Vec3(px, py, pz);
    }
    
    public Plane(Triangle triangle) {
        this.point = triangle.a;
        this.normal = triangle.normal;
    }
    
    private final Vec3 p1Tmp = new Vec3();
    private final Vec3 p2Tmp = new Vec3();
    private final Vec3 normalTmp = new Vec3();
    
    public List<Triangle> clipFront(Triangle face) {
        normalTmp.set(normal);
        normalTmp.scale(-1);
        return clip(face, normalTmp);
    }

    public List<Triangle> clipBack(Triangle face) {
        return clip(face, normal);
    }
    
    public List<Triangle> clip(Triangle face, Vec3 planeNormal) {
        List<Triangle> triangles = new ArrayList<>();
        List<Vec3> clippedVertices = new ArrayList<>();
        Vec3[] vertices = { face.a, face.b, face.c };
        for (int i = 0; i < 3; i++) {
            p1Tmp.set(vertices[i]);
            p2Tmp.set(vertices[(i + 1) % 3]);
            Vec3 pi = new Vec3(
                    p2Tmp.x - p1Tmp.x, p2Tmp.y - p1Tmp.y, p2Tmp.z - p1Tmp.z);
            
            p1Tmp.sub(point);
            p2Tmp.sub(point);
            double dot1 = planeNormal.dot(p1Tmp);
            double dot2 = planeNormal.dot(p2Tmp);
            if (dot1 >= 0) {
                clippedVertices.add(vertices[i]);
            }

            if (dot1 * dot2 < 0) {
                pi.scale(Math.abs(dot1 / (dot1 - dot2)));
                pi.add(vertices[i]);
                clippedVertices.add(pi);
            }
        }
        Triangle t1 = null;
        Triangle t2 = null;
        if (clippedVertices.size() == 3) {
            triangles.add(t1 = new Triangle(clippedVertices.get(0)
                    , clippedVertices.get(1), clippedVertices.get(2)));
            t1.normal = face.normal;
        }
        else if (clippedVertices.size() == 4) {
            triangles.add(t1 = new Triangle(clippedVertices.get(0)
                    , clippedVertices.get(1), clippedVertices.get(2)));
            triangles.add(t2 = new Triangle(clippedVertices.get(0)
                    , clippedVertices.get(2), clippedVertices.get(3)));
            t1.normal = face.normal;
            t2.normal = face.normal;
        }
        return triangles;
    }
    
    public boolean isOnSamePlane(Triangle triangle, double tolerance) {
        Vec3[] vertices = { triangle.a, triangle.b, triangle.c };
        for (Vec3 vertex : vertices) {
            p1Tmp.set(vertex);
            p1Tmp.sub(point);
            if (Math.abs(p1Tmp.dot(normal)) > tolerance) {
                return false;
            }
        }
        return true;
    }

    public boolean isFront(Vec3 p) {
        p1Tmp.set(p);
        p1Tmp.sub(point);
        return p1Tmp.dot(normal) > 0;
    }
    
}
