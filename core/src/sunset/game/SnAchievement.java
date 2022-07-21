package sunset.game;

import arc.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.*;
import mindustry.maps.*;
import sunset.game.SnEventType.*;

public abstract class SnAchievement{
    public static final StringMap emptyTags = new StringMap(){
        @Override
        public boolean containsValue(Object value, boolean identity){
            return false;
        }

        @Override
        public boolean containsKey(String key){
            return false;
        }

        @Override
        public String put(String key, String value){
            return null;
        }
    };
    public final String name;
    public final String[] oldNamed;
    //    @Nullable
    public SnAchievementTab parent;
//    public Seq<SnAchievement> children;

    public SnAchievement(String name, String... oldNamed){
        this.name = name;
        this.oldNamed = oldNamed;
    }

    protected static String settingsKey(String name){
        return "sn-achievement-" + name;
    }

    protected static boolean unlocked(String name){
        return Core.settings.getBool(unlockKey(name));
    }

    public static String unlockKey(String name){
        return settingsKey(name) + "-unlock";
    }

    public final String settingsKey(){
        return settingsKey(name);
    }

    public void afterLoad(){
        boolean oldUnlocked = false;
        for(String s : oldNamed){
            oldUnlocked |= unlocked(s);
            Core.settings.remove(unlockKey(s));
        }
        if(oldUnlocked){
            unlocked();
        }

    }

    public void unlock(){
        Core.settings.put(unlockKey(), true);
    }

    public final String unlockKey(){
        return unlockKey(name);
    }

    public boolean unlocked(){
        return Core.settings.getBool(unlockKey());
    }

    protected StringMap worldTags(){
        Map map = Vars.state.map;
        if(map == null){
            emptyTags.clear();
            return emptyTags;
        }
        return map.tags;
    }

    protected abstract boolean trigger();

    public void check(){
        if(Vars.headless) return;
        if(!unlocked() && trigger()){
            unlock();
            onUnlocked();
            Events.fire(new AchievementReveiveEvent(this));
        }
    }

    protected void onUnlocked(){

    }

    protected String stringProgress(){
        return unlocked() ? "1/1" : "0/1";
    }

    public void display(Table table){
        table.image(region());
        table.table(info -> {
            info.add(localizedName()).grow().row();
            info.add(description()).grow();
        }).grow();
        table.label(() -> {
//            return unlocked() ? "@completed" : stringProgress();
            return (unlocked() ? "[accent]" :"")+ stringProgress();
        });
    }

    /*content.uiIcon*/
    public abstract TextureRegion region();

    public boolean locked(){
        return !unlocked();
    }

    public void save(){

    }

    public boolean hasMoveInfo(){
        return false;
    }

    public String localizedName(){
        return Core.bundle.get("sn-achievements-" + name + ".name");
    }

    public String description(){
        return Core.bundle.getOrNull("sn-achievements-" + name + ".description");
    }
}
