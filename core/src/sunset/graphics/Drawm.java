package sunset.graphics;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;

public class Drawm{
    public static void energySphere(long seed, float time, float fin, int amount,
                                    float minSpeed, float maxSpeed, float mainRadius, float miniRadius,
                                    Color c1, Color c2, float x, float y) {
        Seq<Vec2> vecs = new Seq<>();
        Angles.randLenVectors(seed, amount, minSpeed, maxSpeed, (xx, yy) -> vecs.add(new Vec2(xx, yy)));
        vecs.each(vec -> {
            float size = (vec.len() * time) % Mathf.PI2;
            if (size < Mathf.PI) return;
            miniEnergySphere(fin, mainRadius, miniRadius, c1, c2, x, y, vec.angleRad(), Mathf.PI2 - size);
        });
        color(Pal.surge, Color.white, 1 - fin);
        Fill.circle(x, y, fin * mainRadius);
        vecs.each(vec -> {
            float size = (vec.len() * time) % Mathf.PI2;
            if (size >= Mathf.PI) return;
            miniEnergySphere(fin, mainRadius, miniRadius, c2, c1, x, y, vec.angleRad(), size);
        });
    }

    private static void miniEnergySphere(float fin, float mainRadius, float miniRadius, Color c1, Color c2, float x, float y, float angle, float size) {
        float _size = (Mathf.sin(size) * 0.15f + 0.85f) * fin;
        float len = Mathf.cos(size) * fin * (mainRadius + miniRadius);
        float bx = Mathf.sin(angle) * len;
        float by = Mathf.cos(angle) * len;
        color(c1, c2, Mathf.sin(size));
        Fill.circle(x + bx, y + by, _size * miniRadius);
    }

}
