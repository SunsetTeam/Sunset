import arc.files.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mma.type.pixmap.*;
import sunset.utils.*;

public class SomeTests{
    static final
    Fi resources = Fi.get("tests/src/test/resources"),
    sprites = Fi.get("core/assets-raw/sprites"),
    assets = Fi.get("core/assets");

    public static void main(String[] args){
        Log.info("Start testing");
        Fi file = resources.child("rotor-big-1.png");
//        Fi file = resources.child("rotor-big-1.png");
        Pixmap pixmap = PixmapIO.readPNG(file);

        resources.child("out/result.png").writePng(fullRotor(pixmap, 1000));

//        resources.child("rotor-full.png").writePng(pixmap);
//        rotatePixmap(root,2);
    }

    private static Pixmap fullRotor(Pixmap rotreg, int count){
        int size = Math.max(rotreg.width, rotreg.height);
        Pixmap fullreg = new Pixmap(size, size);
        for(int i = 0; i < count; i++){
            Log.info(i+'/'+count);
            PixmapProcessor.drawCenter(fullreg, PixmapRotator.rotate(rotreg, i / (float)count * 360f));
        }
        return fullreg;
    }

    static Pixmap rotate(Pixmap pixmap, float angle){
        final int size = Math.max(pixmap.width, pixmap.height);
        Pixmap rotated = new Pixmap(size, size);
        Vec2 center = new Vec2(pixmap.width / 2f, pixmap.height / 2f);
        ObjectSet<String> coordinates = new ObjectSet<>();
        Cons3<Integer, Integer, Color> blendSet = (x, y, color) -> {
            x += (size - pixmap.width) / 2;
            y += (size - pixmap.height) / 2;
            Color set = Tmp.c4.set(rotated.get(x, y));
            if(!coordinates.add(x + "_" + y)){
                Log.info("twice(@;@)", x, y);
            }
            float a = set.a;
            set.lerp(color, color.a).a = a + color.a;
            if(color.a > 0){
            }
            if(set.a == 0) return;
            rotated.set(x, y, set);
        };
        rotated.fill(Color.clearRgba);
        pixmap.each((x, y) -> {
            Color color = Tmp.c1.set(pixmap.get(x, y));
            if(color.a == 0) return;
            Tmp.v1.set(x, y).rotateAround(center, angle);
            float x1 = Tmp.v1.x;
            float y1 = Tmp.v1.y;
            int roundX = (int)x1;
            int roundY = (int)y1;
            float dx = x1 % 1f;
            float dy = y1 % 1f;
            if(dx == 0 && dy == 0){
                blendSet.get(roundX, roundY, color);
//                rotated.set(roundX, roundY, color.rgba());
                return;
            }
            Color tmp = Tmp.c2;
            tmp.set(color).a((1f - dx) * (1f - dy));
            blendSet.get(roundX, roundY, tmp);

            tmp.set(color).a(dx * (1f - dy));
            blendSet.get(roundX + 1, roundY, tmp);

            tmp.set(color).a(dx * dy);
            blendSet.get(roundX + 1, roundY + 1, tmp);

            tmp.set(color).a((1f - dx) * dy);
            blendSet.get(roundX, roundY + 1, tmp);
        });
        return rotated;
    }


}
