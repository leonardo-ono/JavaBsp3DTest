package bsp3d;

import test.Vec3;
import wavefront.WavefrontParser;

/**
 *
 * @author Leo
 */
public class TestBsp {

    public static void main(String[] args) throws Exception {
        WavefrontParser.load("/res/Doom_E1M1.obj", 1);
        System.out.println(WavefrontParser.obj.faces.size());
        
        Node node = new Node();
        node.preProcess(0, WavefrontParser.obj.faces);

        //Vec3 player = new Vec3(0, 0, 0);
        //node.transverse(player, null);
    }
    
}
