import arc.files.Fi;
import arc.func.Cons3;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.math.geom.Vec2;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import sunset.ctype.ContentType;

public class SomeTests {
    static final
    Fi resources = Fi.get("tests/src/test/resources"),
            sprites = Fi.get("core/assets-raw/sprites"),
            assets = Fi.get("core/assets");

    public static void main(String[] args) {
        class TodoObject {
            Seq<String> parts = new Seq<>();
        }
        ObjectMap<ContentType, ObjectMap<String, ObjectSet<String>>> todoList = new ObjectMap<>();
        for (Fi bundleFile : assets.child("bundles").list()) {
          Seq<String> fileBuilder=new Seq<>();

            String[] split = bundleFile.readString().split("\n");
            for (String line : split) {
                if (!line.replace(" ", "").startsWith("#")&& line.contains("[#FF0000]//TODO")) {
//                    Log.info("broken line: @",line);
                    String type = line.substring(0,line.indexOf("."));
                    int fromIndex = line.indexOf(" =");
                    int endIndex = line.lastIndexOf(".", fromIndex);
                    String name=line.substring(type.length()+1, endIndex);
                    todoList.get(ContentType.valueOf(type), ObjectMap::new).get(name, ObjectSet::new).add(line.substring(endIndex+1,fromIndex));
                    fileBuilder.add("#"+line.substring(0,fromIndex)+" = [#FF0000]//TOD");
                } else {
                    fileBuilder.add(line);
                }
            }
            bundleFile.writeString(fileBuilder.toString("\n"));
//            Log.info("file: @", bundleFile.name());
        }

        for (ContentType key : todoList.keys()) {
            Log.info(key);
            for (ObjectMap.Entry<String, ObjectSet<String>> entry : todoList.get(key)) {
                String name = entry.key;
                Log.info(" |-@ (@)", name,entry.value.toString(","));
            }
        }

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


}
