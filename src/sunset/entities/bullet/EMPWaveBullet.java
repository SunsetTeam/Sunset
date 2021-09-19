package sunset.entities.bullet;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

import java.util.Random;

/** Wave bullet with optional random status duration. */
public class EMPWaveBullet extends BasicBulletType{
    public boolean randomTime = false;
    public int min;
    public int max;

    public EMPWaveBullet(float speed, float damage, String bulletSprite, int min, int max){
        super(speed, damage);
        this.sprite = bulletSprite;
    }

    public EMPWaveBullet(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
    }

    public EMPWaveBullet(float speed, float damage){
        this(speed, damage, "bullet", 0, 0);
    }

    public EMPWaveBullet(){
        this(1f, 1f, "bullet", 0, 0);
    }

    @Override
    public void update(Bullet b){
        Random r = new Random();
        int number = r.nextInt(max + 1) + min;

        if(randomTime){
            statusDuration = 0 + number;
        }
        if(min > 0.0001f){
            randomTime = true;
        }
        if(max > 0.0001f){
            randomTime = true;
        }
    }
}