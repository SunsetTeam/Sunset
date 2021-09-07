package sunset.world.blocks;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;
import sunset.type.ContentDisplayerType;

/** Стена, которая снижает пробитие пуль. */
public class AntiPierceWall extends Wall implements ContentDisplayerType {
    /** Дополнительный штраф к пробитию. */
    public int pierceDebuff = 1;
    /** Множитель урона пули. */
    public float damageDebuff = 0.95f;
    public AntiPierceWall(String name) {
        super(name);
    }
    @Override
    public boolean showStats() { return true; }

    @Override
    public void setStats() {
        super.setStats();
    }

    @Override
    public void display(Table table) {
        if(pierceDebuff > 0) {
            table.row();
            table.add(Core.bundle.format("stat.piercedamagedebuff", (int)(100*(1-damageDebuff)))).left();
        }
        if(damageDebuff < 1) {
            table.row();
            table.add(Core.bundle.format("stat.piercedebuff", pierceDebuff)).left();
        }
        table.row();
    }

    public class AntiPierceWallBuild extends WallBuild {
        @Override
        public boolean collision(Bullet bullet) {
            super.collision(bullet);
            if(bullet.type.pierceCap != -1 && bullet.type.pierce) {
                for(int i = 0; i < pierceDebuff; i++) bullet.collided.add(id);
            }
            bullet.damage *= damageDebuff;
            return true;
        }
    }
}
