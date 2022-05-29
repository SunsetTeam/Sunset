package sunset.core;

import arc.*;
import arc.KeyBinds.*;
import arc.assets.*;
import arc.scene.style.*;
import mma.core.*;
import sunset.game.SnEventType.*;

import static arc.Core.bundle;
import static mindustry.Vars.*;

public class SnUI extends ModUI implements Loadable{
    public SnUI(){
        super(new KeyBind[0]);
        Core.assets.load(this);
    }

    @Override
    public void init(){
        super.init();
        SnPlanetSystem.setup();

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
}
