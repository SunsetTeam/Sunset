package sunset.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Puddle;
import mindustry.graphics.Drawf;
import mindustry.type.Liquid;

import static mindustry.entities.Puddles.maxLiquid;

public class SayangLiquid extends Liquid {
    public int puddles1 = 5;
    public int puddles2 = 2;
    public float puddleRot1 = 0.14f;
    public float puddleRot2 = 0.05f;
    public Color color1;


    public SayangLiquid(String name, Color color, Color color1){
        super(name);
        this.color = new Color(color);
        this.color1 = color1;
    }

    @Override
    public void drawPuddle(Puddle puddle) {
        super.drawPuddle(puddle);
        float z = Draw.z();
        float amount = puddle.amount, x = puddle.x, y = puddle.y;
        float f = Mathf.clamp(amount / (maxLiquid / 1.5f));
        float smag = puddle.tile.floor().isLiquid ? 0.8f : 0f, sscl = 25f;

        Draw.color(Tmp.c1.set(color).shiftValue(-0.05f));
        Fill.circle(x + Mathf.sin(Time.time + id * 532, sscl, smag), y + Mathf.sin(Time.time + id * 53, sscl, smag), f * 8f);

        float length = f * 6f;
        Draw.color(color1);
        for(int i = 0; i < puddles1; i++){
            Tmp.v1.trns(rand.random(360f) + Time.time * puddleRot1 * rand.random(3.5f, 6.8f), rand.random(length));
            float vx = x + Tmp.v1.x, vy = y + Tmp.v1.y;
            float r = (Time.time + rand.random(80)) % 80;
            r = Math.abs((r * 2f) - 80) / 80;

            Fill.circle(
                    vx + Mathf.sin(Time.time + i * 532, sscl, smag),
                    vy + Mathf.sin(Time.time + i * 53, sscl, smag),
                    f * rand.random(1f, 3.1f) * r);
        }

        Draw.z(z + 0.10f);
        Draw.color(color1, color, 0.15f);
        for(int i = 0; i < 2; i++){
            Tmp.v1.trns(rand.random(360f) + Time.time * puddleRot2 * rand.random(2.5f, 5.8f), rand.random(length));
            float vx = x + Tmp.v1.x, vy = y + Tmp.v1.y;
            float r = (Time.time + rand.random(140)) % 120;
            r = Math.abs((r * 2f) - 120) / 120;

            Fill.circle(
                    vx + Mathf.sin(Time.time + i * 532, sscl, smag),
                    vy + Mathf.sin(Time.time + i * 53, sscl, smag),
                    f * rand.random(1.95f, 4.2f) * r);

        }

        Draw.color();
        Draw.z(z);

        if(lightColor.a > 0.001f && f > 0){
            Drawf.light(x, y, 30f * f, lightColor, color.a * f * 0.8f);
        }
    }
}
