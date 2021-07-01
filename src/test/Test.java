package test;

import java.util.List;

/**
 *
 * @author Leo
 */
public class Test {

    public static void main(String[] args) {
        Plane plane = new Plane(new Vec3(0, 0, 1), new Vec3(0, 0, 0));
        
        Vec3 a = new Vec3(-1, -1, -0.09);
        Vec3 b = new Vec3(0, 1, 0.10);
        Vec3 c = new Vec3(1, -1, 0.05);
        Triangle triangle = new Triangle(a, b, c);
        
        System.out.println("same plane " + (plane.isOnSamePlane(triangle, 0.1)));
    }
    
    public static void test() {
        
        Triangle face = new Triangle(
                new Vec3(-2.03068, -1.41322, 1.48207), 
                new Vec3(0.189345, 1.25918, -0.673156),
                new Vec3(1.93778, -1.29982, 0.778722));
        
        Vec3 planePoint = new Vec3(-0.98109, -2.11933, 1.37757);
        Vec3 planeNormal = new Vec3(-0.014935, -2.15819, 1.12255);
        planeNormal.sub(planePoint);
        planeNormal.normalize();
        
        Plane plane = new Plane(planeNormal, planePoint);
        
        List<Triangle> triangles = plane.clipBack(face);
        triangles.forEach(triangle -> System.out.println(triangle));
        
    }
    
}
