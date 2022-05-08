package sunset.content;

import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import sunset.game.*;

public class SnAchivements implements ContentList{
    @Override
    public void load(){
        new SnAchievementTab("test"){{
            achievement(new SnAchievement("first-copper"){
                @Override
                protected boolean trigger(){
                    if(Vars.state.isPlaying()){
                        CoreBuild core = Vars.player.team().core();
                        return core != null && core.items.has(Items.copper);
                    }
                    return false;
                }

                @Override
                public TextureRegion region(){
                    return Items.copper.uiIcon;
                }
            });
        }};
    }

}
