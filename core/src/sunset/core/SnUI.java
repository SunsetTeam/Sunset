package sunset.core;

import arc.*;
import arc.assets.*;
import arc.scene.style.*;
import arc.util.*;
import sunset.game.SnEventType.*;

import static arc.Core.bundle;
import static mindustry.Vars.*;

public class SnUI implements Loadable, Disposable, ApplicationListener{
    public SnUI(){
        Core.assets.load(this);
    }

    @Override
    public void init(){

        Core.app.post(() ->
        Core.app.post(() -> Core.app.post(() -> {
//            SnFonts.loadContentIcons();
        }))
        );
        Events.on(AchievementReveiveEvent.class, e -> {
            ui.hudfrag.showToast(new TextureRegionDrawable(e.achievement.region()), iconLarge, bundle.format("sunset-achievement.received", e.achievement.localizedName()));
        });
//        Events.on(ClientLoadEvent.class,e-);
    }

    @Override
    public void loadSync(){
    }

    @Override
    public void dispose(){

    }
}
