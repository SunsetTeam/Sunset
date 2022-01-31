//1x1 4
//2x2 6
//3x3 7
//4x4 7
//5x5 5+1
//6x6 3
//7x7 1+1

    empHit = new Effect(35, e -> {
        randLenVectors(e.id, 6, 4f + e.fin() * 8f, (x, y) -> {
            Draw.color(Color.valueOf("7FFFD4"), Color.valueOf("32D0DC"), e.fin());
            float circleRad = 2f + e.fin() * 10f;
            Lines.circle(e.x, e.y, circleRad);
        });
    }),

    empShootSmall = new Effect(60, e -> {
        randLenVectors(e.id, 35, 5f + e.fin() * 9, (x, y) -> {
            Interp interp = Interp.linear;
            float ifin = e.fin(Interp.linear);
            Draw.color(SnPal.emp1, SnPal.emp2, ifin);
            Lines.stroke(interp.apply(5, 10, e.fin()));
        });
    }),

    empShootBig = new Effect(90, e -> {
        randLenVectors(e.id, 35, 5f + e.fin() * 9, (x, y) -> {
            Interp interp = Interp.linear;
            float ifin = e.fin(Interp.linear);
            Draw.color(SnPal.emp1, SnPal.emp2, ifin);
            Lines.stroke(interp.apply(5, 20, e.fin()));
        });
    }),

    empWave = new Effect(30, e -> {
        randLenVectors(e.id, 10, 5f + e.fin() * 5f, (x, y) -> {
            color(SnPal.emp1, SnPal.emp2, e.fin());
            Fill.square(e.x + x, e.y + y, e.fout());
        });
        /*randLenVectors(e.id, 5, 3f + e.fin() * 5f, (x, y) -> {
            color(Color.valueOf("CFEDD4"), Color.lime, e.fin());
            Fill.square(e.x + x, e.y + y, e.fout());
        });*/
    }),


    fakeLightning = new Effect(10f, 500f, e -> {
        Object[] data = (Object[]) e.data;

        float length = (float) data[0];
        int tileLength = Mathf.round(length / tilesize);

        Lines.stroke((float) data[1] * e.fout());
        Draw.color(e.color, Color.white, e.fin());

        for (int i = 0; i < tileLength; i++) {
            float offsetXA = i == 0 ? 0f : Mathf.randomSeed(e.id + (i * 6413), -4.5f, 4.5f);
            float offsetYA = (length / tileLength) * i;

            int f = i + 1;

            float offsetXB = f == tileLength ? 0f : Mathf.randomSeed(e.id + (f * 6413), -4.5f, 4.5f);
            float offsetYB = (length / tileLength) * f;

            Tmp.v1.trns(e.rotation, offsetYA, offsetXA);
            Tmp.v1.add(e.x, e.y);

            Tmp.v2.trns(e.rotation, offsetYB, offsetXB);
            Tmp.v2.add(e.x, e.y);

            Lines.line(Tmp.v1.x, Tmp.v1.y, Tmp.v2.x, Tmp.v2.y, false);
            Fill.circle(Tmp.v1.x, Tmp.v1.y, Lines.getStroke() / 2f);
            Drawf.light((Team) data[2], Tmp.v1.x, Tmp.v1.y, Tmp.v2.x, Tmp.v2.y, (float) data[1] * 3f, e.color, 0.4f);
        }

        Fill.circle(Tmp.v2.x, Tmp.v2.y, Lines.getStroke() / 2);
    }).layer(Layer.bullet + 0.01f),


        @Override
        protected void shoot(BulletType type) {
            /*if(chargeTime > 0){
                tr.trns(rotation, shootLength);
                chargeBeginEffect.at(x + tr.x, y + tr.y, rotation);
                chargeSound.at(x + tr.x, y + tr.y, 1);

                for(int i = 0; i < chargeEffects; i++){
                    Time.run(Mathf.random(chargeMaxDelay), () -> {
                        if(!isValid()) return;
                        tr.trns(rotation, shootLength);
                        chargeEffect.at(x + tr.x, y + tr.y, rotation);
                    });
                }

                charging = true;

                Time.run(chargeTime, () -> {
                    if(!isValid()) return;
                    tr.trns(rotation, shootLength);
                    recoil = recoilAmount;
                    heat = 1f;
                    bullet(type, rotation + Mathf.range(inaccuracy));
                    effects();
                    charging = false;
                });

                //when burst spacing is enabled, use the burst pattern
            }else if(burstSpacing > 0.0001f){
                for(int i = 0; i < shots; i++){
                    Time.run(burstSpacing * i, () -> {
                        if(!isValid() || !hasAmmo()) return;

                        recoil = recoilAmount;

                        tr.trns(rotation, shootLength, Mathf.range(xRand));
                        bullet(type, rotation + Mathf.range(inaccuracy));
                        effects();
                        useAmmo();
                        recoil = recoilAmount;
                        heat = 1f;
                    });
                }

            }else{
                if(alternate){
                    float i = (shotCounter % shots) - (shots-1)/2f;

                    tr.trns(rotation - 90, spread * i + Mathf.range(xRand), shootLength);
                    bullet(type, rotation + Mathf.range(inaccuracy));
                }else{
                    tr.trns(rotation, shootLength, Mathf.range(xRand));

                    for(int i = 0; i < shots; i++)
                        bullet(type, rotation + Mathf.range(inaccuracy + type.inaccuracy) + (i - (int)(shots / 2f)) * spread);
                }

                shotCounter++;
                recoil = recoilAmount;
                heat = 1f;
                effects();
                useAmmo();
            }*/

            for(int i = 0; i < delay.size; i++) {
                counter.set(i, counter.get(i) + 1);
                if(Objects.equals(counter.get(i), delay.get(i))) {
                    counter.set(i, 0);
                    //for (int t = 0; t < shots; t++) {
                        powerShot.get(i).get(this).run();
                    //}
                }
            }
        }

        @Override
        public void displayBars(Table bars) {
            super.displayBars(bars);

            for(int i = 0; i < delay.size; i++) {
                final int j = i;
                bars.add(new Bar(() -> Core.bundle.format("bar.sunset-powerReload") + counter.get(j) + " / " + delay.get(j), () -> Pal.lancerLaser.cpy().lerp(Pal.place, Mathf.absin(Time.time, 20, (counter.get(j) / (delay.get(j) * 2.5f)))), () -> (counter.get(j) / (delay.get(j) * 1f)))).growX();
                bars.row();
            }
        }

        public <T extends Building> void setPowerShot(Func<T, Runnable> shot, int delay, String name) {
            if(shot != null) {
                powerShot.add((Func<Building, Runnable>) shot);
                this.delay.add(delay);
            }
        }

        public Seq<Integer> delay = new Seq<>();
        public Seq<Func<Building, Runnable>> powerShot = new Seq<>();

        @Override
        public void created() {
            for(int i = 0; i < delay.size; i++) counter.add(0);
            super.created();
        }

        public Seq<Integer> counter = new Seq<>();

        //for (int i = 0; i < shots; i++) {
            setPowerShot(entity -> () -> {
                new LightningBulletType() {{
                    inaccuracy = 360;
                    shootCone = 360;
                    targetAir = false;
                    targetGround = true;
                    drawLight = true;
                    spread = 18f;
                    damage = 320;
                    shootEffect = Fx.lancerLaserShoot;
                    smokeEffect = Fx.none;
                    lightningColor = SnPal.redfire1;
                }}.create(entity, entity.x, entity.y, (float) Math.random());//problem with this angle
            }, 3, Core.bundle.get("stat.sunset.powerShot"));
        //}


package sunset.world.blocks.defense.turrets;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.annotations.Annotations.Load;
import mindustry.graphics.Layer;

import static arc.Core.atlas;

public class AnimationTurret360 extends Turret360{
@Load("@-side-h-" + 2)
public TextureRegion[] sideRegionsHorizontal = new TextureRegion[2];
@Load("@-side-v-" + 2)
public TextureRegion[] sideRegionsVertical = new TextureRegion[2];
@Load("@-turret")
public TextureRegion turretRegion;

    public float pullTime = 60f, closeTime = 90f;
    //public float baseLightSpacing = 30f, holyLightDelay = 20f, holyLightSpacing = 10f;
    public float xOpen = 3f, yOpen = -2f;
    //public Color lightColor = Pal.surge;
    //public float lightOpactiy = 0.7f;

    public AnimationTurret360(String name) {
        super(name);
        recoilAmount = 0;
    }

    public class AT360 extends T360 {
        protected float chargeTimer/*, charge*/;
        //protected boolean animation;

        @Override
        public void draw() {

            float totalTime = chargeTime + closeTime;
            float openAmount = Mathf.curve(chargeTimer, 0f, pullTime);
            float closeAmount = Mathf.curve(chargeTimer, chargeTime, totalTime);
            float openX = xOpen * Interp.pow2Out.apply(openAmount) - xOpen * Interp.pow2In.apply(closeAmount);
            float openY = yOpen * Interp.pow2Out.apply(openAmount) - yOpen * Interp.pow2In.apply(closeAmount);

            Tmp.v1.trns(rotation - 90, -openX, y);//left
            Tmp.v2.trns(rotation - 90, openX, y);//right
            Tmp.v3.trns(rotation - 90, x, openY);//up
            Tmp.v4.trns(rotation - 90, x, -openY);//down

            float[] sXPre1 = {Tmp.v1.x, Tmp.v2.x};
            float[] sYPre1 = {Tmp.v1.y, Tmp.v2.y};
            float[] sX1 = {sXPre1[0] + x, sXPre1[1] + x};
            float[] sY1 = {sYPre1[0] + y, sYPre1[1] + y};

            float[] sXPre2 = {Tmp.v3.x, Tmp.v4.x};
            float[] sYPre2 = {Tmp.v3.y, Tmp.v4.y};
            float[] sX2 = {sXPre2[0] + x, sXPre2[1] + x};
            float[] sY2 = {sYPre2[0] + y, sYPre2[1] + y};

            tr2.trns(rotation, -recoil);
            float tx = x + tr2.x, ty = y + tr2.y;

            Draw.z(Layer.turret);
            Draw.rect(turretRegion, tx, ty, rotation - 90f);
            for (int i = 0; i < 2; i++) {
                Draw.rect(sideRegionsHorizontal[i], sX1[i], sY1[i], rotation - 90f);
                Draw.rect(sideRegionsVertical[i], sX2[i], sY2[i], rotation - 90f);
            }

            if (atlas.isFound(heatRegion) && heat > 0.00001f) {
                Draw.color(heatColor, heat);
                Draw.blend(Blending.additive);
                Draw.rect(heatRegion, tx, ty, rotation - 90f);
                Draw.blend();
                Draw.color();
            }
        }
    }

}
