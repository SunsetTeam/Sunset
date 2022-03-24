package sunset.type.weapons;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import sunset.ai.weapon.*;
import sunset.content.*;
import sunset.utils.*;

import static arc.graphics.Color.*;

//TODO так много generic'ов... Может на Kotlin перевести?
public class ChainWeapon<Targetc extends Posc & Healthc & Teamc> extends WeaponExt implements StatValue{
    public static final UnitData.DataKey<ObjectMap<WeaponMount, Seq>> chainWeaponDataKey = UnitData.dataKey(ObjectMap::new); // I HATE JAVA'S GENERICS!!! (1)
    public int maxChainLength = 1;
    public float range = 120f;
    public float damageTick = 1f;
    public float healTick = 4f;
    public float buildingBuff = 1f;
    public float rangeFactor = 0.9f;
    public float statsFactor = 0.9f;
    public float laserLayer = Layer.bullet;
    public Color chainColor = coral.cpy();
    public boolean draw = false;
    TextureRegion laser, laserEnd;

    public ChainWeapon(String name){
        super(name);
        // Should never shoot using the standard method.
        ai = new EmptyWeaponAI();
        bullet = SnBullets.emptyBullet;
        firstShotDelay = Float.MAX_VALUE;
        reload = Float.MAX_VALUE;
        recoil = 0;
    }

    Posc[] target = new Posc[1]; // WHERE IS GENERIC ARRAY?! I HATE JAVA'S GENERICS!!! (2) 
    public Targetc getFirstTarget(WeaponMount mount, Unit unit) {
        Vec2 wpos = Tmp.v1.set(Utils.mountX(unit, mount), Utils.mountY(unit, mount));
        ChainWeapon weapon = (ChainWeapon)mount.weapon;
        target[0] = (Targetc)Units.closest(null, wpos.x, wpos.y, weapon.range, u -> { // I HATE JAVA'S GENERICS!!! (3)
            if(unit == u) return false;
            if(u.team != unit.team && weapon.damageTick == 0) return false;
            if(u.team == unit.team && (weapon.healTick == 0 || !u.damaged())) return false;
            return mount.weapon.rotate || Angles.within(wpos.angleTo(u), unit.rotation + mount.rotation, mount.weapon.shootCone);
        });
        if(target[0] != null) return (Targetc)target[0]; // I HATE JAVA'S GENERICS!!! (4)
        if(weapon.buildingBuff != 0f) {
            Units.nearbyBuildings(wpos.x, wpos.y, weapon.range, b -> {
                if(b.team.isEnemy(unit.team)) return;
                if(!mount.weapon.rotate && !Angles.within(wpos.angleTo(b), unit.rotation + mount.rotation, mount.weapon.shootCone)) return;
                if(target != null && Mathf.chance(0.1)) return; // a bit of randomness
                target[0] = (Targetc)b; // I HATE JAVA'S GENERICS!!! (5)
            });
        }
        return (Targetc)target[0]; // I HATE JAVA'S GENERICS!!! (6)
    }

    @Override
    public void load(){
        super.load();
        laser = Core.atlas.find("parallax-laser");
        laserEnd = Core.atlas.find("parallax-laser-end");
    }

    private Seq<Targetc> getTargets(WeaponMount mount, Unit unit){
        ObjectMap<WeaponMount, Seq<Targetc>> data = (ObjectMap<WeaponMount, Seq<Targetc>>)(Object)chainWeaponDataKey.get(unit); // I HATE JAVA'S GENERICS!!! (7)
        if(data == null) return null;
        return data.get(mount, Seq::new);
    }

    float[] stats = new float[3];
    @Override
    public void update(Unit unit, WeaponMount mount){
        Seq<Targetc> targets = getTargets(mount, unit);
        if(targets == null) return;
        updateTargets(targets, mount, unit);
        stats[0] = damageTick;
        stats[1] = healTick;
        stats[2] = buildingBuff;
        targets.each(t -> {
            if(unit.team().isEnemy(t.team())) {
                t.damageContinuousPierce(stats[0]);
            } else {
                t.heal(stats[1] * Time.delta);
                if(t instanceof Building) ((Building)t).applyBoost(stats[2] + 1, 60);
            }
            stats[0] *= statsFactor;
            stats[1] *= statsFactor;
            stats[2] *= statsFactor;
        });
    }

    float[] currentRange = new float[1];
    Posc[] currentTarget = new Posc[1]; // WHERE IS GENERIC ARRAY?! I HATE JAVA'S GENERICS!!! (8) 
    private void updateTargets(Seq<Targetc> targets, WeaponMount mount, Unit unit){
        ChainWeapon weapon = (ChainWeapon)mount.weapon;
        targets.clear();
        currentRange[0] = range;
        currentTarget[0] = getFirstTarget(mount, unit);
        for(int unitsLeft = maxChainLength; unitsLeft > 0 && currentTarget[0] != null; unitsLeft--){
            currentRange[0] *= rangeFactor;
            targets.add((Targetc)currentTarget[0]); // I HATE JAVA'S GENERICS!!! (9)
            Tmp.v1.set(currentTarget[0]);

            Targetc сt = (Targetc)Units.closest(null, currentTarget[0].x(), currentTarget[0].y(), currentRange[0], t -> { // I HATE JAVA'S GENERICS!!! (10) 
                if(t == unit || targets.contains((Targetc)t)) return false;
                if(!Tmp.v1.within(t, currentRange[0])) return false;
                if(!unit.team.isEnemy(t.team)) return t.damaged() && weapon.healTick != 0; 
                return weapon.damageTick != 0;
            });
            if(сt == null && weapon.buildingBuff != 0f) {
                Units.nearbyBuildings(currentTarget[0].x(), currentTarget[0].y(), weapon.range, b -> {
                    if(b.team.isEnemy(unit.team)) return;
                    if(!Tmp.v1.within(b, currentRange[0])) return;
                    if(currentTarget != null && Mathf.chance(0.1)) return; // a bit of randomness
                    currentTarget[0] = (Targetc)b; // I HATE JAVA'S GENERICS!!! (11)
                });
            } else {
                currentTarget[0] = сt;
            }
            unitsLeft--;
        }
    }

    @Override
    public boolean customDraw(){
        return true;
    }

    private void drawWeapon(WeaponMount mount, Unit unit, float rotation){
        Weapon weapon = mount.weapon;
        Vec2 wpos = Tmp.v1.set(Utils.mountX(unit, mount), Utils.mountY(unit, mount));

        if(weapon.shadow > 0) Drawf.shadow(wpos.x, wpos.y, weapon.shadow);

        if(weapon.outlineRegion.found()){
            float z = Draw.z();
            if(!weapon.top) Draw.z(z - unit.type.outlineRadius);
            Draw.rect(weapon.outlineRegion, wpos,
            weapon.outlineRegion.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
            weapon.region.height * Draw.scl, rotation);
            Draw.z(z);
        }

        Draw.rect(weapon.region, wpos,
        weapon.region.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
        weapon.region.height * Draw.scl, rotation);

        if(weapon.heatRegion.found() && mount.heat > 0){
            Draw.color(weapon.heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(weapon.heatRegion, wpos,
            weapon.heatRegion.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
            weapon.heatRegion.height * Draw.scl,
            rotation);
            Draw.blend();
            Draw.color();
        }
    }

    @Override
    public void preDraw(WeaponMount mount, Unit unit){
        Seq<Targetc> units = getTargets(mount, unit);
        Vec2 weaponPos = Tmp.v1.set(Utils.mountX(unit, mount), Utils.mountY(unit, mount));
        float angle = (units == null || units.isEmpty()) ? unit.rotation : weaponPos.angleTo(units.get(0));
        if(units != null && !units.isEmpty()){
            float z = Draw.z();
            Draw.z(laserLayer); //TODO как-то пофиксить эффекты луча
            Draw.mixcol(chainColor, 0.4f);
            Drawf.laser(unit.team, laser, laserEnd, weaponPos.x, weaponPos.y, units.get(0).x(), units.get(0).y());
            for(int i = 0; i < units.size - 1; i++){
                Drawf.laser(unit.team, laser, laserEnd,
                            units.get(i).x(), units.get(i).y(),
                            units.get(i + 1).x(), units.get(i + 1).y());
            }
            Draw.mixcol();
            Draw.z(z);
        }
        if(draw) drawWeapon(mount, unit, angle - 90);
    }

    @Override
    public void postDraw(WeaponMount mount, Unit unit) {}

    @Override
    public void display(Table table){
        table.left().defaults().padRight(3).left();
        if(damageTick > 0){
            table.row();
            table.add(Core.bundle.format("bullet.damagesec", Strings.autoFixed(damageTick * 60, 1)));
        }
        if(healTick > 0){
            table.row();
            table.add(Core.bundle.format("bullet.healsec", Strings.autoFixed(healTick * 60, 1)));
        }
        if(buildingBuff > 0){
            table.row();
            table.add(Core.bundle.format("bullet.boost", (int)(buildingBuff * 100)));
        }
        if(maxChainLength != 1){
            table.row();
            table.add(Core.bundle.format("bullet.maxchain", maxChainLength));
        }
    }
}
