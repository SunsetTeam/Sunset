package sunset.game;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.scene.Element;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.struct.StringMap;
import arc.util.Nullable;
import jdk.internal.jrtfs.JrtFileAttributeView;
import mindustry.Vars;
import mindustry.maps.Map;

public abstract class SnAchievement {
    public static final StringMap emptyTags = new StringMap() {
        @Override
        public boolean containsValue(Object value, boolean identity) {
            return false;
        }

        @Override
        public boolean containsKey(String key) {
            return false;
        }

        @Override
        public String put(String key, String value) {
            return null;
        }
    };
    public final String name;
    public final String[] oldNamed;
    @Nullable
    public SnAchievement parent;
    public Seq<SnAchievement> children;

    public SnAchievement(String name, String... oldNamed) {
        this.name = name;
        this.oldNamed = oldNamed;
    }

    protected static String settingsKey(String name) {
        return "sn-achievement-" + name;
    }

    protected static boolean unlocked(String name) {
        return Core.settings.getBool(unlockKey(name));
    }

    public static String unlockKey(String name) {
        return settingsKey(name) + "-unlock";
    }

    public final String settingsKey() {
        return settingsKey(name);
    }

    public void afterLoad() {
        boolean oldUnlocked = false;
        for (String s : oldNamed) {
            oldUnlocked |= unlocked(s);
            Core.settings.remove(unlockKey(s));
        }
        if (oldUnlocked) {
            unlocked();
        }

    }

    public void unlock() {
        Core.settings.put(unlockKey(), true);
    }

    public final String unlockKey() {
        return unlockKey(name);
    }

    public boolean unlocked() {
        return Core.settings.getBool(unlockKey());
    }

    protected StringMap worldTags() {
        Map map = Vars.state.map;
        if (map == null) {
            emptyTags.clear();
            return emptyTags;
        }
        return map.tags;
    }

    protected abstract boolean trigger();

    public void check() {
        if (!unlocked() && trigger()) {
            unlock();
        }
    }

    public void display(Table table) {

    }
/*content.uiIcon*/
    public abstract TextureRegion region();

    public boolean locked(){
        return !unlocked();
    }

    public abstract void save();

    public boolean hasMoveInfo() {
        return false;
    }

    public String localizedName() {
        return Core.bundle.get("sn-achievements-"+name+".name");
    }

    public String description() {
        return Core.bundle.getOrNull("sn-achievements-"+name+".description");
    }
}
