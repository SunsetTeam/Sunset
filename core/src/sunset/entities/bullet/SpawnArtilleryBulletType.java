package sunset.entities.bullet;

import arc.scene.ui.layout.Table;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.gen.Bullet;
import mindustry.type.UnitType;

public class SpawnArtilleryBulletType extends ArtilleryBulletType {
    public UnitType unitType;

    public SpawnArtilleryBulletType(float speed, float damage){
        super(speed, damage);
    }

    @Override
    public void hit(Bullet b) {
        super.hit(b);
        unitType.spawn(b.team, b.x, b.y);
    }
}