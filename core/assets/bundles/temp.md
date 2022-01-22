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