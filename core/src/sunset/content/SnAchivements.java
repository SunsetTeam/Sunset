package sunset.content;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import gas.content.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import sunset.game.*;

import java.util.*;

public class SnAchivements{
    public static void load(){
        new SnAchievementTab("sn-achievements.items"){{
            for(Item item : Vars.content.items()){
                achievement(new SnAchievement("first-"+item.name){
                    @Override
                    protected boolean trigger(){
                        if(Vars.state.isPlaying()){
                            CoreBuild core = Vars.player.team().core();
                            return core != null && core.items.has(item);
                        }
                        return false;
                    }

                    @Override
                    public TextureRegion region(){
                        return item.uiIcon;
                    }
                });
            }
        }};
        new SnAchievementTab("sn-achievements.liquids"){{
            for(Liquid liquid : Vars.content.liquids()){
                if(liquid.gas){
                    continue;
                }
                achievement(new SnAchievement("first-"+liquid.name){
                    @Override
                    protected boolean trigger(){
                        /*if(Vars.state.isPlaying()){
                            CoreBuild core = Vars.player.team().core();
                            return core != null && core.items.has(liquid);
                        }*/
                        return liquid.unlocked();
                    }

                    @Override
                    public TextureRegion region(){
                        return liquid.uiIcon;
                    }
                });
            }
        }};
        new SnAchievementTab("sn-achievements.gasses"){{
            for(UnlockableContent gas : Vars.content.liquids().select(liquid -> liquid.gas).<UnlockableContent>as().addAll(Gasses.all())){
                achievement(new SnAchievement("first-"+gas.name){
                    @Override
                    protected boolean trigger(){
                        return gas.unlocked();
                    }

                    @Override
                    public TextureRegion region(){
                        return gas.uiIcon;
                    }
                });
            }
        }};
        resetAchievements();
    }

    private static void resetAchievements(){
        HashMap<String, Object> values = Reflect.get(Settings.class, Core.settings, "values");
        for(String key : values.keySet().toArray(new String[0])){
            if(key.startsWith("sn-achievement")){
                Core.settings.remove(key);
            }
        }
    }

}
