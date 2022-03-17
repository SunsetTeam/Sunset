package sunset.graphics;

import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Time;

public class Drawm {
    private static Rand rand = new Rand();
    private static Vec2 pos = new Vec2();

    public static void drawTrident(float atX, float atY, long seed, float globalScl) { 
        draw(atX, atY, seed, 15, 
             0.66f, 1.66f, // timeScl
             3f, 5.5f, // len
             1.5f, 3f, // rad
             globalScl); 
    }

    private static void draw(float atX, float atY, long seed, int count, float timeSclMin, float timeSclMax, float lenMin, float lenMax, float radMin, float radMax, float globalScl) {
        rand.setSeed(seed);

        for(int i = 0; i < count; i++) {
            float timeScl = rand.nextFloat() * (timeSclMax-timeSclMin) + timeSclMin;
            float len = rand.nextFloat() * (lenMax-lenMin) + lenMin;
            float rad = rand.nextFloat() * (radMax-radMin) + radMin;
            float angle = rand.nextFloat() * Mathf.PI2;
            float vecScl = Mathf.sin(timeScl * Time.time / 60);
            float sizeScl = Mathf.cos(timeScl * Time.time / 60) / 5f + 0.9f;
            pos.set(len * vecScl, 0).setAngleRad(angle).scl(globalScl);
            Fill.circle(pos.x + atX, pos.y + atY, rad * globalScl * sizeScl);
        }
    }
}
