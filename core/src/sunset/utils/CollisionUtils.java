package sunset.utils;

import arc.math.geom.*;

public class CollisionUtils{

    private static Vec2 l1 = new Vec2();
    private static Rect r1 = new Rect();
    private static Rect r2 = new Rect();

    public static boolean checkCollide(Rect hitboxA, Rect hitboxB, float aLastX, float aLastY, float aX, float aY, float bLastX, float bLastY, float bX, float bY){
        r1.set(hitboxA);
        r2.set(hitboxB);

        /*float aLastX = a.lastX();
        float aLastY = a.lastY();
        float aX = a.getX();
        float aY = a.getY();

        float bLastX = b.lastX();
        float bLastY = b.lastY();
        float bX = b.getX();
        float bY = b.getY();*/
        r1.x += (aLastX - aX);
        r1.y += (aLastY - aY);
        r2.x += (bLastX - bX);
        r2.y += (bLastY - bY);

        float vax = aX - aLastX;
        float vay = aY - aLastY;
        float vbx = bX - bLastX;
        float vby = bY - bLastY;

        if(/*a != b && a.collides(b) && b.collides(a)*/true){
            l1.set(aX, aY);
            boolean collide = r1.overlaps(r2) || collide(r1.x, r1.y, r1.width, r1.height, vax, vay,
            r2.x, r2.y, r2.width, r2.height, vbx, vby, l1);
            if(collide){
//                a.collision(b, l1.x, l1.y);
//                b.collision(a, l1.x, l1.y);
                return true;
            }
        }
        return false;
    }

    private static boolean collide(float x1, float y1, float w1, float h1, float vx1, float vy1,
                            float x2, float y2, float w2, float h2, float vx2, float vy2, Vec2 out){
        float px = vx1, py = vy1;

        vx1 -= vx2;
        vy1 -= vy2;

        float xInvEntry, yInvEntry;
        float xInvExit, yInvExit;

        if(vx1 > 0.0f){
            xInvEntry = x2 - (x1 + w1);
            xInvExit = (x2 + w2) - x1;
        }else{
            xInvEntry = (x2 + w2) - x1;
            xInvExit = x2 - (x1 + w1);
        }

        if(vy1 > 0.0f){
            yInvEntry = y2 - (y1 + h1);
            yInvExit = (y2 + h2) - y1;
        }else{
            yInvEntry = (y2 + h2) - y1;
            yInvExit = y2 - (y1 + h1);
        }

        float xEntry = xInvEntry / vx1;
        float xExit = xInvExit / vx1;
        float yEntry = yInvEntry / vy1;
        float yExit = yInvExit / vy1;

        float entryTime = Math.max(xEntry, yEntry);
        float exitTime = Math.min(xExit, yExit);

        if(entryTime > exitTime || xExit < 0.0f || yExit < 0.0f || xEntry > 1.0f || yEntry > 1.0f){
            return false;
        }else{
            float dx = x1 + w1 / 2f + px * entryTime;
            float dy = y1 + h1 / 2f + py * entryTime;

            out.set(dx, dy);

            return true;
        }
    }
}
