package sunset.entities.bullet;


import arc.graphics.Color;
import mindustry.Vars;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Trail;

public class TorpedoBulletType extends BasicBulletType {
	public void update(Bullet b) {
		if(!Vars.world.tileWorld(b.x, b.y).floor().isLiquid) b.remove();
			if(b.data == null) b.data = new Trail(15);
			if(b.data == null)((Trail)b.data).update(b.x, b.y);
	}
	public void draw(Bullet b) {
		((Trail)b.data).draw(Color.lightGray, 15);
	}
}
