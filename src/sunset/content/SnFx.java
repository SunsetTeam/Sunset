package sunset.content;


import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.graphics.*;
import sunset.graphics.SnPal;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

public class SnFx{
    public static final Effect

    enojiecraft = new Effect(30, e -> {
        randLenVectors(e.id, 6, 4f + e.fin() * 8f, (x, y) -> {
            color(Pal.heal);
            Fill.square(e.x + x, e.y + y, e.fout() + 0.5f, 45);
        });
    }),

    enojieburn = new Effect(40, e -> {
        randLenVectors(e.id, 5, 3f + e.fin() * 5f, (x, y) -> {
            color(Color.valueOf("CFEDD4"), Color.lime, e.fin());
            Fill.square(e.x + x, e.y + y, e.fout());
        });
    }),


    crystalyze = new Effect(40, e -> {
        randLenVectors(e.id, 5, 3f + e.fin() * 8f, (x, y) -> {
            color(Pal.lightTrail);
            Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.5f, 45);
        });
    }),

    crystalyzeSmall = new Effect(30, e -> {
        randLenVectors(e.id, 3, e.fin() * 5f, (x, y) -> {
            color(Pal.lightTrail);
            Fill.square(e.x + x, e.y + y, e.fout() + 0.5f, 45);
        });
    }),
    
    modSmokeCloud = new Effect(90, e -> {
        randLenVectors(e.id, 6, 4f + e.fin(), 30, 30f, (x, y) -> {
            color(SnPal.bGray);
            alpha((0.5f - Math.abs(e.fin() - 0.5f)) * 2f);
            Fill.circle(e.x + x, e.y + y, 0.5f + e.fout() * 4f);
        });
    }),
    
    modPlasticburn = new Effect(45, e -> {
        randLenVectors(e.id, 7, 2.8f + e.fin() * 5f, (x, y) -> {
            color((SnPal.gGray), Color.gray, e.fin());
            Fill.circle(e.x + x, e.y + y, e.fout());
        });
    }),
    
    modFormsmoke = new Effect(50, e -> {
        randLenVectors(e.id, 8, 6f + e.fin() * 8f, (x, y) -> {
            color(Pal.plasticSmoke, Color.lightGray, e.fin());
            Fill.square(e.x + x, e.y + y, 0.2f + e.fout() * 2f, 45);
        });
    }),
    
    modSmeltsmoke = new Effect(20f, e -> {
        randLenVectors(e.id, 7, 6.8f + e.fin() * 5f, (x, y) -> {
            color(SnPal.rGray, e.color, e.fin());
            Fill.square(e.x + x, e.y + y, 0.5f + e.fout() * 2f, 45);
        });
    }),
    
    galebardLaserCharge = new Effect(45f, e -> {
        color(Pal.lancerLaser);

        randLenVectors(e.id, 5, 3f + 9f * e.fout(), e.rotation, 100f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 4f);
        });
    }),

    galebardLaserChargeBegin = new Effect(170f, e -> {
        color(Pal.lancerLaser);
        Fill.circle(e.x, e.y, e.fin() * 9f);

        color();
        Fill.circle(e.x, e.y, e.fin() * 8f);
    }),

    hitGalebard = new Effect(12, e -> {
        color(Color.white);
        stroke(e.fout() * 7f);

        randLenVectors(e.id, 20, e.finpow() * 27f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 6 + 4f);
        });
    }),

    redBomb = new Effect(30f, 70f, e -> {
        color(SnPal.redBomb);
        stroke(e.fout() * 2f);
        float circleRad = 3f + e.finpow() * 40f;
        Lines.circle(e.x, e.y, circleRad);

        color(SnPal.redBomb);
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 6f, 100f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 3f, 35f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.5f, SnPal.redBomb, e.fout());
    }),

    CopterBomb = new Effect(25f, 60f, e -> {
        color(SnPal.copterBomb);
        stroke(e.fout() * 2f);
        float circleRad = 2f + e.finpow() * 30f;
        Lines.circle(e.x, e.y, circleRad);

        color(SnPal.copterBomb);
        for(int i = 0; i < 3; i++){
            Drawf.tri(e.x, e.y, 5f, 80f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 3; i++){
            Drawf.tri(e.x, e.y, 2f, 25f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.5f, SnPal.copterBomb, e.fout());
    }),

    heavyFlame = new Effect(64f, 80f, e -> {
        Draw.color(Pal.lightFlame, Pal.darkFlame, Color.gray, e.fin());

        randLenVectors(e.id, 8, e.finpow() * 60f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x*2, e.y + y*2, 0.65f + e.fout() * 1.9f);
        });
    }),
    typhoonShootLiquid = new Effect(15f, 80f, e -> {
        color(e.color);

        randLenVectors(e.id, 2, e.finpow() * 15f, e.rotation, 11f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.5f + e.fout() * 2.5f);
        });
    }),


    tridenCharge = new Effect(210, e -> {
        color(Pal.surge, Color.white, e.fout());
        Draw.z(Layer.block);
        float r = Mathf.degRad * e.rotation;
        float cx = e.x - Mathf.cos(r)*12f;
        float cy = e.y - Mathf.sin(r)*12f;
        while(Mathf.chance(0.075f)) {
            float a = Mathf.random(Mathf.pi);
            float dx = cx + Mathf.sin(a) * 8f;
            float dy = cy + Mathf.cos(a) * 8f;
            lightning(cx, cy, dx, dy, Pal.surge, 3, 0.5f, SnFx.lightningFast.layer(Layer.block));
        }
        Fill.circle(cx, cy, e.fin() * 8f);
    }),

    tridenHit = new Effect(30, e -> {
        Draw.z(Layer.effect);
        stroke(0);
        color(new Color(Pal.surge).a(Mathf.clamp(2*e.fout()-1)));
        Fill.circle(e.x, e.y, 112f);
        stroke(3, new Color(Pal.surge).a(Mathf.clamp(2*e.fout())));
        Lines.circle(e.x, e.y, 112f);
    }),

    missilePoint = new Effect(1, (e) -> {
        Draw.z(Layer.bullet);
        stroke(2, e.color);
        Lines.circle(e.x, e.y, (Float)e.data);
        Lines.circle(e.x, e.y, 20f);
        float a = e.time/75 - Mathf.PI/2;
        float sin = Mathf.sin(a), cos = Mathf.cos(a);
        Lines.line(e.x + sin*18f, e.y + cos*18f, e.x + sin*24f, e.y + cos*24f);
        Lines.line(e.x + cos*18f, e.y - sin*18f, e.x + cos*24f, e.y - sin*24f);
        Lines.line(e.x - sin*18f, e.y - cos*18f, e.x - sin*24f, e.y - cos*24f);
        Lines.line(e.x - cos*18f, e.y + sin*18f, e.x - cos*24f, e.y + sin*24f);
    }),

    lightningFast = new Effect(6, Fx.lightning.renderer);

    public static void lightning(float x1, float y1, float x2, float y2, Color c, int iterations, float rndScale, Effect e) {
        Seq<Vec2> lines = new Seq<>();
        lines.add(new Vec2(x1, y1));
        lines.add(new Vec2(x2, y2));
        for(int i = 0; i < iterations; i++) {
            for(int j = 0; j < lines.size-1; j+=2) {
                Vec2 v1 = lines.get(j), v2 = lines.get(j+1);
                float ang = (Angles.angle(v1.x, v1.y, v2.x, v2.y) + 90f) * Mathf.degRad;
                float sin = Mathf.sin(ang), cos = Mathf.cos(ang);
                Vec2 v = new Vec2((v1.x + v2.x) / 2, ((v1.y + v2.y) / 2));
                v.x += Mathf.random(rndScale)*sin;
                v.y += Mathf.random(rndScale)*cos;
                lines.insert(j+1, v);
            }
        }
        e.at(x1, y1, 0f, c, lines);

    }
}
