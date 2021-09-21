    const charger = multiLib.MultiCrafter(GenericCrafter, GenericCrafter.GenericCrafterBuild, "charger", [
    {
        input: {
            liquids: ["water/12"],
            power: 2,
                    items: ["lead/1"]
        },
        output: {
            items: ["adc-charged-lead/1"]
        },
        craftTime: 90
    },
    {
        input: {
            liquids: ["water/8"],
            power: 1.8,
                    items: ["adc-charged-lead/3"]
        },
        output: {
            items: ["adc-charged-stick/3"]
        },
        craftTime: 70
    },
    {
        input: {
            liquids: ["slag/12"],
            power: 2.4,
                    items: ["adc-charged-lead/2"]
        },
        output: {
            items: ["adc-charged-ingot/2"]
        },
        craftTime: 120
    },
    {
        input: {
            liquids: ["slag/24"],
            power: 3.2,
                    items: ["adc-charged-lead/2", "adc-charged-stick/2", "adc-charged-ingot/2"]
        },
        output: {
            items: ["adc-charged-cylinder/4"],
            power: 1
        },
        craftTime: 180
    },
            ], {
    },

    function Extra() {
        this.draw=function(){
            let region1 = Core.atlas.find("adc-charger-top")
            Draw.rect(region1, this.x, this.y, this.totalProgress * 2);
            let region2 = Core.atlas.find("adc-charger")
            Draw.rect(region2, this.x, this.y);
            if(this.warmup > 0 && flameColor.a > 0.001){
                Lines.stroke(circleStroke * this.warmup);

                var si = Mathf.absin(flameRadiusScl, flameRadiusMag);
                var a = alpha * this.warmup;
                Draw.blend(Blending.additive);

                Draw.color(midColor, a);
                Fill.circle(this.x, this.y, flameRad + si);

                Draw.color(flameColor, a);
                Lines.circle(this.x, this.y, (flameRad + circleSpace + si) * this.warmup);

                Lines.stroke(particleStroke * this.warmup);

                var base = (Time.time / particleLife);
                rand.setSeed(this.id);
                for(var i = 0; i < particles; i++){
                    var fin = (rand.random(1) + base) % 1, fout = 1 - fin;
                    var angle = rand.random(360);
                    var len = particleRad * Interp.pow2Out.apply(fin);
                    Lines.lineAngle(this.x + Angles.trnsx(angle, len), this.y + Angles.trnsy(angle, len), angle, particleLen * fout * this.warmup);
                }

                Draw.blend();
                Draw.reset();
            }
        }
    });

const flameColor = Color.valueOf("f58349");
    const midColor = Color.valueOf("f2d585");
    const flameRad = 1;
    const circleSpace = 2;
    const flameRadiusScl = 3;
    const flameRadiusMag = 0.3;
    const circleStroke = 1.5;

    const alpha = 0.68;
    const particles = 25;
    const particleLife = 40;
    const particleRad = 7;
    const particleStroke = 1.1;
    const particleLen = 3;

