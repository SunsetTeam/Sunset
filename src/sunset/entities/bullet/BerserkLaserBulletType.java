package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;

public class BerserkLaserBulletType extends BasicBulletType {
    private float laserLength;
    public float maxLength = 100, width = 4;
    public float lengthFallOf = 0.5f;
    public Effect hitEffect = Fx.none;
    public Color[] colors = {Color.yellow, Color.gray, Color.blue};

    Color sColor = new Color();
    @Override
    public void update(Bullet b){
        Units.nearbyEnemies(b.team, b.x, b.y, width, maxLength, u -> {
            laserLength = Mathf.dst(b.x, b.y, u.x, u.y);
        });
        Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), laserLength);
    }

    @Override
    public void draw(Bullet b) {
        float rLength = b.fdata;

        float f = Mathf.curve(b.fin(), 0f, 0.2f);
        float baseLength = rLength * f;
        float cwidth = width;

        Lines.lineAngle(b.x, b.y, b.rotation(), baseLength);
        for (Color color : colors){
            Draw.color(color);
            Lines.stroke((width *= lengthFallOf) * b.fout());
            Lines.lineAngle(b.x, b.y, b.rotation(), laserLength, false);
            Tmp.v1.trns(b.rotation(), baseLength);
            Drawf.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Lines.getStroke() *1.22f, cwidth * 2f * width / 2f, b.rotation());

            Fill.circle(b.x, b.y, 1f * cwidth * b.fout());
        }

    }
}
