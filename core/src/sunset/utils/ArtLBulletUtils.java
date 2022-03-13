package sunset.utils;

import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.core.World;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.graphics.Layer;

import static sunset.entities.bullet.ArtilleryLightningBulletType.effectLifetime;
import static sunset.entities.bullet.ArtilleryLightningBulletType.lColor;

public class ArtLBulletUtils {//todo: добавить свечение
    private static final Vec2 tmp1 = new Vec2(), tmp2 = new Vec2();
    private static Building furthest;
    public static final BulletType hitter = new BulletType() {{
        despawnEffect = hitEffect = shootEffect = smokeEffect = trailEffect = Fx.none;
        collidesTeam = hittable = false;
        hitSize = 0;
        speed = 0.0001f;
        drawSize = 120;
        lifetime = 10;
        absorbable = collides = collidesAir = collidesGround = collidesTiles = true;
        status = StatusEffects.shocked;
        statusDuration = 10;
    }
        @Override
        public void draw(Bullet b) {}
        @Override
        public void drawLight(Bullet b) {}
    };
    public static final Effect posLightning = (new Effect(effectLifetime, 1200.0f, e -> {
        if (!(e.data instanceof Seq)) return;
        Seq<Vec2> lines = e.data();
        Draw.color(lColor, e.color, e.fin());
        Lines.stroke(e.fout());
        Fill.circle(lines.first().x, lines.first().y, Lines.getStroke() / 2f);
        for (int i = 0; i < lines.size - 1; i++) {
            Vec2 cur = lines.get(i);
            Vec2 next = lines.get(i + 1);
            Lines.line(cur.x, cur.y, next.x, next.y, false);
        }
        /*for(Vec2 p : lines){
            Fill.circle(p.x, p.y, Lines.getStroke() / 2f);
        }*/
    })).layer(Layer.effect - 0.001f);

    //create lightning to the enemies in range.
    public static void createLength(@Nullable Bullet owner, Team team, Position from, float length, float angle, Color color, boolean createLightning, float damage, int boltLen, float width, int boltNum, Cons<Position> movement) {
        create(owner, team, from, tmp2.trns(angle, length).add(from), color, createLightning, damage, boltLen, width, boltNum, movement);
    }
    //A create method that with a Bullet owner.
    public static void create(Entityc owner, Team team, Position from, Position target, Color color, boolean createLightning, float damage, int boltLen, float width, int boltNum, Cons<Position> movement) {
        Position sureTarget = findInterceptedPoint(from, target, team);
        movement.get(sureTarget);
        if (createLightning) {
            if (owner instanceof Bullet) {
                Bullet b = (Bullet)owner;
                for (int i = 0; i < b.type.lightning; i++)
                    Lightning.create(b, color, b.type.lightningDamage < 0.0F ? b.damage : b.type.lightningDamage, sureTarget.getX(), sureTarget.getY(), b.rotation() + Mathf.range(b.type.lightningCone / 2.0F) + b.type.lightningAngle, b.type.lightningLength + Mathf.random(b.type.lightningLengthRand));
            }
            else for(int i = 0; i < 3; i++)Lightning.create(team, color, damage <= 0 ? 1f : damage, sureTarget.getX(), sureTarget.getY(), Mathf.random(360f), boltLen);
        }
        float realDamage = damage;
        if (realDamage <= 0) {
            if (owner instanceof Bullet) {
                Bullet b = (Bullet)owner;
                realDamage = b.damage > 0 ? b.damage : 1;
            } else realDamage = 1;
        }
        hitter.create(owner, team, sureTarget.getX(), sureTarget.getY(), 1).damage(realDamage);
        createEffect(from, sureTarget, color, boltNum, width);
    }
    //Compute the proper hit position.
    public static Position findInterceptedPoint(Position from, Position target, Team fromTeam) {
        furthest = null;
        return Geometry.raycast(
                World.toTile(from.getX()),
                World.toTile(from.getY()),
                World.toTile(target.getX()),
                World.toTile(target.getY()),
                (x, y) -> (furthest = Vars.world.build(x, y)) != null && furthest.team() != fromTeam && furthest.block().insulated
        ) && furthest != null ? furthest : target;
    }
    public static void createEffect(Position from, Position to, Color color, int boltNum, float width) {
        if (Vars.headless) return;
        if (boltNum < 1) {
            Fx.chainLightning.at(from.getX(), from.getY(), 0, color, new Vec2().set(to));
        } else {
            float dst = from.dst(to);
            Seq<Vec2> p = null;
            for (int i = 0; i < boltNum; i++) {
                float len = getBoltRandomRange();
                float randRange = len * 5;
                FloatSeq randomArray = new FloatSeq();
                for (int num = 0; num < dst / (Vars.tilesize * 0.6f * len) + 1; num++) {
                    randomArray.add(Mathf.range(randRange) / (num * 0.025f + 1));
                }
                createBoltEffect(color, width, p = computeVectors(randomArray, from, to));
            }
        }
    }
    //ProvSet the range of lightning's randX.
    private static float getBoltRandomRange() {
        return Mathf.random(1f, 7f);
    }
    //create lightning effect.
    public static void createBoltEffect(Color color, float width, Seq<Vec2> vets) {
        posLightning.at((vets.first().x + vets.peek().x) / 2f, (vets.first().y + vets.peek().y) / 2f, width, color, vets);
    }
    private static Seq<Vec2> computeVectors(FloatSeq randomVec, Position from, Position to) {
        int param = randomVec.size;
        float angle = from.angleTo(to);
        Seq<Vec2> lines = new Seq<>(param);
        tmp1.trns(angle, from.dst(to) / (param - 1));
        lines.add(new Vec2().set(from));
        for (int i = 1; i < param - 2; i ++) lines.add(new Vec2().trns(angle - 90, randomVec.get(i)).add(tmp1, i).add(from.getX(), from.getY()));
        lines.add(new Vec2().set(to));
        return lines;
    }
}
