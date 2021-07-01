package test;

import bsp3d.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.io.Serializable;

/**
 *
 * @author Leo
 */
public class Triangle implements Comparable<Triangle>, Serializable {

    public Vec3 a;
    public Vec3 b;
    public Vec3 c;
    public Vec3 normal = new Vec3();
    private final Vec3 p1Tmp = new Vec3();
    
    private static Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    
    private static Color[] colors = new Color[256];
    
    static {
        for (int c = 0; c < 256; c++) {
            colors[c] = new Color(c, c, c, 255);
        }
    }
    
    public Triangle(Vec3 a, Vec3 b, Vec3 c, Vec3 n) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal = n;
    }
    
    public Triangle(Vec3 a, Vec3 b, Vec3 c) {
        this.a = a;
        this.b = b;
        this.c = c;

        p1Tmp.set(a);
        p1Tmp.sub(b);
        normal.set(c);
        normal.sub(b);
        normal.cross(p1Tmp);
    }
    
    private static Polygon polygon = new Polygon();
    
    public void draw(Graphics2D g) {
        polygon.reset();
        polygon.addPoint((int) (a.x + 0), (int) (a.y + 0));
        polygon.addPoint((int) (b.x + 0), (int) (b.y + 0));
        polygon.addPoint((int) (c.x + 0), (int) (c.y + 0));
        g.draw(polygon);
    }

    Vec3 wa = new Vec3();
    Vec3 wb = new Vec3();
    Vec3 wc = new Vec3();
    
    private static Plane plane = new Plane(new Vec3(0, 0, 1.0), new Vec3(0, 0, 0.01));
    Player playerTmp = new Player();
    
    public void draw3D(Graphics2D g, Player player) {
        wa.set(a);
        wb.set(b);
        wc.set(c);
        wa.sub(player.position);
        wb.sub(player.position);
        wc.sub(player.position);
        wa.rotateY(-player.angleY);
        wb.rotateY(-player.angleY);
        wc.rotateY(-player.angleY);
        wa.rotateX(-player.angleX);
        wb.rotateX(-player.angleX);
        wc.rotateX(-player.angleX);
        
        if (wa.z <= 0 && wb.z <= 0 && wc.z <= 0) return;
        if (wa.z <= 0 || wb.z <= 0 || wc.z <= 0) {
            Triangle cameraTriangle = new Triangle(wa, wb, wc);
            
            //plane.point.set(player.position);
            //plane.point.x += 0.01 * player.direction.x;
            //plane.point.y += 0.01 * player.direction.y;
            //plane.point.z += 0.01 * player.direction.z;
//            plane.point.z += 0.01;

            //plane.normal = player.direction;
            playerTmp.position.set(0, 0, 0);
            playerTmp.direction.set(0, 0, 1.0);
            for (Triangle triangle : plane.clipBack(cameraTriangle)) {
                triangle.draw3D(g, playerTmp);
            }

            return;
        }
        
        int px1 = (int) (300 * wa.x / wa.z);
        int py1 = (int) (300 * wa.y / wa.z);
        int px2 = (int) (300 * wb.x / wb.z);
        int py2 = (int) (300 * wb.y / wb.z);
        int px3 = (int) (300 * wc.x / wc.z);
        int py3 = (int) (300 * wc.y / wc.z);
        
        double z = (wa.z + wb.z + wc.z) * 0.3333;
        int c = (int) (255 * (1.0 - (z / 5)));
        if (c < 0) c = 0;
        if (c > 255) c = 255;
        
        
        if (px1 < -400 && px2 < -400 && px3 < -400) return;
        if (px1 > 400 && px2 > 400 && px3 > 400) return;
        if (py1 < -300 && py2 < -300 && py3 < -300) return;
        if (py1 > 300 && py2 > 300 && py3 > 300) return;
        
        polygon.reset();
        polygon.addPoint(px1, py1);
        polygon.addPoint(px2, py2);
        polygon.addPoint(px3, py3);
        
        g.setStroke(stroke);
        g.setColor(colors[c]);
        g.fill(polygon);
        g.setColor(Color.BLACK);
        g.draw(polygon);
    }

    @Override
    public String toString() {
        return "Triangle{" + "a=" + a + ", b=" + b + ", c=" + c + ", normal=" + normal + '}';
    }

    @Override
    public int compareTo(Triangle o) {
        return (int) Math.signum((o.a.z + o.b.z + o.c.z) / 3 - (a.z + b.z + c.z) / 3);
    }

}
