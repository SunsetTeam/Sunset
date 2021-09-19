package sunset.content;


import arc.func.Floatc2;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.effect.WaveEffect;
import mindustry.graphics.*;
import sunset.utils.Utils;
import sunset.graphics.SnPal;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

import static arc.math.Angles.angle;
import static arc.math.Angles.randLenVectors;

public class SnFx{
    public static final Effect

    enojiecraft = new Effect(60, e -> {
        randLenVectors(e.id, 6, 4f + e.fin() * 8f, (x, y) -> {
            color(Pal.heal);
            Fill.square(e.x + x, e.y + y, e.fout() + 0.5f, 45);  
            float circleRad = 2f + e.fin() * 15f;
            Lines.circle(e.x, e.y, circleRad);
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
        randLenVectors(e.id, 6, 4f + e.fin() * 1.3f, 30, 30f, (x, y) -> {
            color(SnPal.bGray);
            alpha((0.5f - Math.abs(e.fin() - 0.5f)) * 2f);
            Fill.square(e.x + x, e.y + y, 0.5f + e.fout() * 4f);
        });
    }),

    modPlasticburn = new Effect(45, e -> {
        randLenVectors(e.id, 7, 2.8f + e.fin() * 5f, 25, 30f,(x, y) -> {
            color((SnPal.gGray), Color.gray, e.fin());
            Fill.square(e.x + x, e.y + y, e.fout() * 3.1f, 45);
        });
    }),

    modFormsmoke = new Effect(50, e -> {
        randLenVectors(e.id, 8, 6f + e.fin() * 8f, (x, y) -> {
            color(Pal.plasticSmoke, Color.lightGray, e.fin());
            Fill.rect(e.x + x, e.y + y, 0.2f + e.fout() * 2f, 45);
        });
    }),

    weaverSmeltsmoke = new Effect(20f, e -> {
        randLenVectors(e.id, 7, 6.8f + e.fin() * 5f, (x, y) -> {
            color(Color.valueOf("C78D04"), e.color, e.fin());
            Fill.square(e.x + x, e.y + y, 0.5f + e.fout() * 2f, 45);
        });
    }),

    cultivatorSmeltsmoke = new Effect(20f, e -> {
        randLenVectors(e.id, 7, 6.8f + e.fin() * 5f, (x, y) -> {
            color(Color.valueOf("5841A6"), e.color, e.fin());
            Fill.square(e.x + x, e.y + y, 0.5f + e.fout() * 2f, 45);
        });
    }),

    galebardLaserCharge = new Effect(35f, e -> {
        color(Pal.meltdownHit);

        randLenVectors(e.id, 5, 3f + 9f * e.fout(), e.rotation, 100f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 4f);
        });
    }),

    galebardLaserChargeBegin = new Effect(160f, e -> {
        color(Pal.meltdownHit);
        Fill.circle(e.x, e.y, e.fin() * 8f);

        color();
        Fill.circle(e.x, e.y, e.fin() * 7f);
    }),

    galebardShoot = new Effect(70f, e -> {
        color(Pal.meltdownHit);
        stroke(e.fout() * 7f);
        Lines.circle(e.x, e.y, e.fin() * 50f);
        Lines.square(e.x, e.y, e.fin() * 40f, 65); 

        for(int i : Mathf.signs){
            Drawf.tri(e.x, e.y, 14.0f * e.fout(), 80f, e.rotation + 90f * i);
            Drawf.tri(e.x, e.y, 13.0f * e.fout(), 50f, e.rotation + 20f * i);
            Drawf.tri(e.x, e.y, 7.0f * e.fout(), 70f, e.rotation + 12f * i);
            Drawf.tri(e.x, e.y, 8.0f * e.fout(), 110f, e.rotation + 125f * i);
            }
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
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 5f, 80f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 2f, 25f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.5f, SnPal.copterBomb, e.fout());
    }),

    heavyCopterBomb = new Effect(23f, 55f, e -> {
        color(SnPal.copterBomb);
        stroke(e.fout() * 2f);
        float squareRad = 2f + e.finpow() * 25f;
        float circleRad = 2f + e.finpow() * 30f;
        Lines.square(e.x, e.y, e.fin() * 50.0F, 90);
        Lines.square(e.x, e.y, e.fin() * 40.0F, 45);
        Lines.circle(e.x, e.y, circleRad);

        color(SnPal.copterBomb);
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 6f, 100f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 3f, 40f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, squareRad * 1.5f, SnPal.copterBomb, e.fout());
    }),

    bigCopterBomb = new Effect(30f, 65f, e -> {
        color(SnPal.copterBomb);
        stroke(e.fout() * 2f);
        float squareRad = 2f + e.finpow() * 35f;
        float circleRad = 2f + e.finpow() * 40f;
        Lines.square(e.x, e.y, e.fin() * 60.0F, 90 * e.rotation);
        Lines.square(e.x, e.y, e.fin() * 50.0F, 45 * e.rotation);

        Lines.circle(e.x, e.y, circleRad);

        color(SnPal.copterBomb);
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 5f, 70f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, squareRad * 1.5f, SnPal.copterBomb, e.fout());
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
        final Color c1 = Color.valueOf("eaecff"), c2 = Color.valueOf("ffeaec");
        float r = Mathf.degRad * e.rotation;
        float cx = e.x - Mathf.cos(r)*12f;
        float cy = e.y - Mathf.sin(r)*12f;
        Draw.z(Layer.block);
        enegrySphere(e.id, e.time, e.fin(), Mathf.PI / 120f, Mathf.PI / 30f, 8f, 1.5f, c1, c2, cx, cy);
    });
    //region Energy sphere utils
    public static void enegrySphere(long seed, float time, float fin,
                                     float minSpeed, float maxSpeed, float mainRadius, float miniRadius,
                                     Color c1, Color c2, float x, float y) {
        Seq<Vec2> vecs = new Seq<>();
        Utils.randVectors(seed, 8, minSpeed, maxSpeed, (xx, yy) -> vecs.add(new Vec2(xx, yy)));
        vecs.each(vec -> {
            float size = (vec.len() * time) % Mathf.PI2;
            if(size < Mathf.PI) return;
            miniEnergySphere(fin, mainRadius, miniRadius, c1, c2, x, y, vec.angleRad(), Mathf.PI2 - size);
        });
        color(Pal.surge, Color.white, 1 - fin);
        Fill.circle(x, y, fin * mainRadius);
        vecs.each(vec -> {
            float size = (vec.len()* time) % Mathf.PI2;
            if(size >= Mathf.PI) return;
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
    //endregion Energy sphere utils
    public static final Effect tridenHit = new Effect(30, e -> {
        Draw.z(Layer.effect);
        stroke(0);
        color(new Color(Pal.surge).a(Mathf.clamp(2*e.fout()-1)));
        Fill.circle(e.x, e.y, 112f);
        stroke(3, new Color(Pal.surge).a(Mathf.clamp(2*e.fout())));
        Lines.circle(e.x, e.y, 112f);
    }),

    shootWheel5Flame = new Effect(40f, 140f, e -> {
        color(Pal.lightFlame, Pal.darkFlame, Color.gray, e.fin());

        randLenVectors(e.id, 24, e.finpow() * 140f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 1.35f + e.fout() * 2.35f);
        });
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

    stunTrail = new Effect(40, e -> {
        Draw.z(Layer.flyingUnit + 1);
        Draw.color(Color.white);
        Fill.circle(e.x, e.y, 3 * e.fout());
        Draw.color();
    }),

    stunExplode = new Effect(60 * 2.25f, e -> {
        Draw.z(Layer.flyingUnit + 1);
        Draw.color(Color.white, Mathf.pow(e.fout(), 3f));
        Fill.circle(e.x, e.y, 59f);
        Draw.color();
    }),

    stuff = new Effect(75, e -> {
        float s = (float)e.data;
        float f = s * 2 * Mathf.sin(Mathf.pow(e.fin(),0.5f) * Mathf.PI2 * 4);
        float sin = Mathf.sinDeg(e.rotation + 90) * f;
        float cos = Mathf.cosDeg(e.rotation + 90) * f;
        Draw.color(e.color, e.fout());
        Fill.circle(e.x + cos, e.y + sin, s * e.fout());
        Fill.circle(e.x - cos, e.y - sin, s * e.fout());
    }),

    lightningFast = new Effect(6, Fx.lightning.renderer),

    missileLaunchSmall = new Effect(75, e -> {
        Draw.z(Layer.groundUnit);
        final int[] p = {0};
        Utils.randVectors(e.id, 16, 16 * e.fin(), 52 * Mathf.pow(e.fin(), 0.55f), (x, y) -> {
            float a = Mathf.pow(e.fout(), 0.35f);
            if(p[0] == 0) Draw.color(Pal.lightishGray, a);
            if(p[0] == 1) Draw.color(Pal.gray, a);
            if(p[0] == 2) Draw.color(Pal.darkestGray, a);
            Fill.circle(e.x + x, e.y + y, 0.75f+3.25f*Mathf.pow(e.fin(), 0.7f));
            p[0] = (p[0] + 1) % 3;
        });
    }),

    missileLaunchMedium = new Effect(90, e -> {
        Draw.z(Layer.groundUnit);
        final int[] p = {0};
        Utils.randVectors(e.id, 24, 28 * e.fin(), 72 * Mathf.pow(e.fin(), 0.55f), (x, y) -> {
            float a = Mathf.pow(e.fout(), 0.5f);
            if(p[0] == 0) Draw.color(Pal.lightishGray, a);
            if(p[0] == 1) Draw.color(Pal.gray, a);
            if(p[0] == 2) Draw.color(Pal.darkestGray, a);
            Fill.circle(e.x + x, e.y + y, 1.5f+4.5f*Mathf.pow(e.fin(), 0.7f));
            p[0] = (p[0] + 1) % 3;
        });
    }),

    missileLaunchLarge = new Effect(105, e -> {
        Draw.z(Layer.groundUnit);
        final int[] p = {0};
        Utils.randVectors(e.id, 32, 36 * e.fin(), 86 * Mathf.pow(e.fin(), 0.55f), (x, y) -> {
            float a = Mathf.pow(e.fout(), 0.7f);
            if(p[0] == 0) Draw.color(Pal.lightishGray, a);
            if(p[0] == 1) Draw.color(Pal.gray, a);
            if(p[0] == 2) Draw.color(Pal.darkestGray, a);
            Fill.circle(e.x + x, e.y + y, 2f+6f*Mathf.pow(e.fin(), 0.7f));
            p[0] = (p[0] + 1) % 3;
        });
    }),

    sunriseMissileExplosion = new Effect(40, e -> {
        color(Pal.missileYellow);

        e.scaled(8, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 5f + i.fin() * 35f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 20, 4f + 45f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f + 0.8f);
            Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.3f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 10, 2f + 20f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.4f);
        });

        color(Pal.missileYellowBack);
        stroke(e.fout());

        randLenVectors(e.id + 1, 6, 1f + 29f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 6f);
        });
    }),

    sparkMissileExplosion = new Effect(40, e -> {
        color(Pal.missileYellow);

        e.scaled(9, i -> {
            stroke(5f * i.fout());
            Lines.circle(e.x, e.y, 6f + i.fin() * 40f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 24, 5f + 50f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 1f);
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.6f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 10, 3f + 25f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f + 0.7f);
        });

        color(Pal.missileYellowBack);
        stroke(e.fout());

        randLenVectors(e.id + 1, 8, 2f + 35f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 3f + e.fout() * 7f);
        });
    }),

    dissectorMissileExplosion = new Effect(40, e -> {
        color(Pal.missileYellow);

        e.scaled(11, i -> {
            stroke(7f * i.fout());
            Lines.circle(e.x, e.y, 8f + i.fin() * 50f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 35, 8f + 60f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 8f + 2f);
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 1f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 14, 4f + 30f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 7f + 0.9f);
        });

        color(Pal.bulletYellow);

        randLenVectors(e.id, 7, 3f + 25f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f + 0.8f);
        });


        color(Pal.missileYellowBack);
        stroke(e.fout());

        randLenVectors(e.id + 4, 13, 4f + 45f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 5f + e.fout() * 10f);
        });
    }),

    artMissileExplosion = new Effect(40, e -> {
        color(Pal.missileYellow);

        e.scaled(10, i -> {
            stroke(6f * i.fout());
            Lines.circle(e.x, e.y, 7f + i.fin() * 45f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 30, 7f + 55f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 7f + 1.3f);
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f + 0.7f);
        });

        color(SnPal.aGray);

        randLenVectors(e.id, 10, 3f + 25f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 0.9f);
        });

        color(Pal.bulletYellow);

        randLenVectors(e.id, 5, 2f + 20f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.6f);
        });

        color(Pal.missileYellowBack);
        stroke(e.fout());

        randLenVectors(e.id + 3, 10, 3f + 40f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 4f + e.fout() * 8f);
        });
    }),

    statusField = new Effect(30, e -> {
        Draw.z(Layer.flyingUnit);
        float val = Mathf.pow(e.fin(), 0.3f);
        Color c = Pal.boostFrom.cpy().lerp(Pal.boostTo, e.fout());
        Draw.color(c, Mathf.pow(e.fout(), 2f));
        Fill.circle(e.x, e.y, e.rotation * val);
        stroke(1.5f, Pal.boostTo);
        Lines.circle(e.x, e.y, e.rotation * val);
    }),

    torpedoTrail = new Effect(30, e -> {
       color(Color.lightGray);
       randLenVectors(e.id, 15, 2 + e.fin() * 5, (x, y) -> {
           Fill.circle(e.x+x, e.y+y, e.fin() * 2);
       });
    }),
        berserkLaserHitSmall = new Effect(20, e -> {
        Draw.color(Color.valueOf("CCCDDA"));
        Floatc2 floatc2 = new Floatc2() {
            @Override
            public void get(float v, float v1) {
                Fill.poly(e.x + v, e.y + v1, 4, 1.5f + e.fout() * 2f);
            }
        };
        Angles.randLenVectors(e.id, 4, e.finpow() * 20, e.rotation, 360, floatc2);
        Draw.color(Color.valueOf("FFFFFF"));
        Floatc2 floatc21 = new Floatc2() {
            @Override
            public void get(float v, float v1) {
                float angl = Mathf.angle(v, v1);
                Lines.lineAngle(e.x +v, e.y + v1, angl, e.fout() * 1.5f);
            }
        };
        Angles.randLenVectors(e.id, 4, e.finpow() * 20, e.rotation, 360, floatc21);
    }),

    empWave = new WaveEffect(){{
        lifetime = 35;
        sizeFrom = 35;
        sizeTo = 0;
        strokeFrom = 0;
        strokeTo = 0;
        colorFrom = Color.valueOf("7FFFD4");
        colorTo = Color.valueOf("7FFFD4");
    }};
}
