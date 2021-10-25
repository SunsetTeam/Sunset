import arc.files.Fi;
import arc.func.Cons3;
import arc.func.Func3;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.math.geom.Vec2;
import arc.struct.ObjectSet;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import mma.type.pixmap.PixmapProcessor;
import sunset.utils.PixmapRotator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SomeTests {
    static final
    Fi resources = Fi.get("tests/src/test/resources"),
            assets = Fi.get("core/assets-raw/sprites");

    public static void main(String[] args) {
        PixmapProcessor processor = PixmapProcessor.staticProcessor;
        Fi out = resources.child("out");
        out.deleteDirectory();

        generateFull(processor);

        Pixmap rotreg = new Pixmap(resources.child("rotor-big-1.png"));

        int size = Math.max(rotreg.width, rotreg.height);
        Time.mark();
        for (int i = 3; i <= 4; i++) {
            Pixmap fullreg = new Pixmap(size, size);

//        processor.drawCenter(fullreg, rotreg);
//        processor.drawCenter(fullreg, rotate(rotreg, 360f / 3f));
            for (int j = 0; j < i; j++) {
                if (j%100==0){
//                    Log.info("@/@",j,i);
                }
                processor.drawCenter(fullreg, PixmapRotator.rotate(rotreg, j/(float)i*360f));
            }
//        processor.drawCenter(fullreg, rotate__(rotreg, 120*2));
//        processor.drawCenter(fullreg, rotate__(rotreg, 0));

//            out.child("full-"+i+".png").writePng(fullreg);
            out.child("full-"+i+"-outline.png").writePng(processor.outline(fullreg));
        }
        System.out.println(Time.elapsed()+"ms");

//        resources.child("rotor-full.png").writePng(pixmap);
//        rotatePixmap(root,2);
    }

    static Pixmap rotate(Pixmap pixmap, float angle) {
        final int size = Math.max(pixmap.width, pixmap.height);
        Pixmap rotated = new Pixmap(size, size);
        Vec2 center = new Vec2(pixmap.width / 2f, pixmap.height / 2f);
        ObjectSet<String> coordinates = new ObjectSet<>();
        Cons3<Integer, Integer, Color> blendSet = (x, y, color) -> {
            x += (size - pixmap.width) / 2;
            y += (size - pixmap.height) / 2;
            Color set = Tmp.c4.set(rotated.get(x, y));
            if (!coordinates.add(x + "_" + y)) {
                Log.info("twice(@;@)", x, y);
            }
            float a = set.a;
            set.lerp(color, color.a).a = a + color.a;
            if (color.a > 0) {
            }
            if (set.a == 0) return;
            rotated.set(x, y, set);
        };
        rotated.fill(Color.clearRgba);
        pixmap.each((x, y) -> {
            Color color = Tmp.c1.set(pixmap.get(x, y));
            if (color.a == 0) return;
            Tmp.v1.set(x, y).rotateAround(center, angle);
            float x1 = Tmp.v1.x;
            float y1 = Tmp.v1.y;
            int roundX = (int) x1;
            int roundY = (int) y1;
            float dx = x1 % 1f;
            float dy = y1 % 1f;
            if (dx == 0 && dy == 0) {
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

    static Pixmap rotate_(Pixmap image, float angle) {
        final int size = Math.max(image.width, image.height);

        Pixmap rotated = new Pixmap(size, size);
        PixmapProcessor.staticProcessor.drawCenter(rotated, image);
        Pixmap pixmap = rotated.copy();
        rotated.fill(Color.clearRgba);
        Vec2 center = new Vec2(rotated.width / 2f, rotated.height / 2f);
        ObjectSet<String> coordinates = new ObjectSet<>();
        Color tmp = Tmp.c2;
        Color color = Tmp.c1;
        Func3<Integer, Integer, Float, Boolean> canColor = (x, y, val) -> {
            if (pixmap.in(x, y)) {

                tmp.set(pixmap.get(x, y)).a *= val;
                float a = color.a;
                float ta = tmp.a;
//                color.add(tmp.mula(ta)).a=a+ ta;
                color.lerp(tmp.r, tmp.g, tmp.b, color.a, tmp.a).a = a + ta;
//                color.lerp(tmp, tmp.a).a = tmp.a + a;
                return true;
            }
            return false;
        };
        rotated.fill(Color.clearRgba);
        pixmap.each((x, y) -> {
            int sx = x;
            int sy = y;
//            x -= (size - pixmap.width) / 2;
//            y -= (size - pixmap.height) / 2;
//            if (color.a == 0) return;
            Tmp.v1.set(x, y).rotateAround(center, -angle);
//            Tmp.v1.sub((size - pixmap.width) / 2f, (size - pixmap.height) / 2f);
            float x1 = Tmp.v1.x;
            float y1 = Tmp.v1.y;
            int rx = (int) x1;
            int ry = (int) y1;
            float dx = x1 % 1f;
            float dy = y1 % 1f;
            if (!pixmap.in(rx, ry) && !pixmap.in(rx + 1, ry) && !pixmap.in(rx + 1, ry + 1) && !pixmap.in(rx, ry + 1)) {
                return;
            } else {
//                rotated.set(sx, sy, Color.black);
//                if (true) return;
            }
            color.set(Color.white).a = 0;
            canColor.get(rx, ry, dx * dy);
            canColor.get(rx + 1, ry, (1f - dx) * dy);
            canColor.get(rx + 1, ry + 1, (1f - dx) * (1f - dy));
            canColor.get(rx, ry + 1, dx * (1f - dy));

            rotated.set(sx, sy, color.rgba());
//            canColor.get(rx, ry + 1, tmp);
        });
        return rotated;
    }
    private static void generateFull(PixmapProcessor processor) {
        Pixmap root = new Pixmap(resources.child("rotor-big_1.png"));

        int centerOffset = 11;
        Pixmap pixmap = new Pixmap(root.width, root.height);
        pixmap.draw(root);
//        pixmap.draw(root.flipY(), 0, 48 - 11, true);
//        pixmap = processor.drawScaleAt(pixmap, root.flipY(), 0, - (48-11-11));
        pixmap = processor.drawScaleAt(root.flipY(), pixmap, 0, (48 - centerOffset - centerOffset));
        int h = 0;
        for (int y = 0; y < pixmap.height; y++) {
            for (int x = 0; x < pixmap.width; x++) {
                if (pixmap.getA(x, y) != 0) {
                    h = y;
                    y = pixmap.height;
                    break;
                }
            }
        }
        if (h > 0) {
            Pixmap other = new Pixmap(pixmap.width, pixmap.height - h);
            other.draw(pixmap, 0, -h);
            pixmap = other;
            resources.child("rotor-full.png").writePng(pixmap);

            other.fill(Color.clearRgba);
            other.draw(root, 0, other.height / 2 - centerOffset);
        }
        resources.child("rotor-big-1.png").writePng(pixmap);
    }

}
