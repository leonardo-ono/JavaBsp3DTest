package bsp3d;

import java.io.Serial;
import java.io.Serializable;
import test.Vec3;

/**
 *
 * @author Leo
 */
public class Player implements Serializable {
    
    public Vec3 position = new Vec3(0, 0, -2);
    public Vec3 direction = new Vec3(0, 0, -1);
    public double angleY = 0;
    public double angleX = 0;    

    public void updateDirection() {
        direction.set(0, 0, 1);
        direction.rotateY(angleY);
        direction.rotateX(angleX);
        System.out.println("player direction " + direction);
    }
    
}
