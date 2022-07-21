package sunset.core;

import arc.*;
import arc.KeyBinds.*;
import arc.assets.*;
import arc.graphics.g2d.*;
import arc.scene.style.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mma.core.*;
import mma.graphics.*;
import mma.utils.*;
import sunset.game.SnEventType.*;
import sunset.graphics.*;
import sunset.world.blocks.defense.turrets.*;

import static arc.Core.bundle;
import static mindustry.Vars.*;

public class SnUI extends ModUI implements Loadable{
    public SnUI(){
        super(new KeyBind[0]);
        Core.assets.load(this);
        setupMissileSiloUI();
    }

    private void setupMissileSiloUI(){
        Events.run(Trigger.draw, () -> {
            Unit unit = player.unit();
            if(!(unit instanceof BlockUnitc blockUnitc) || !(blockUnitc.tile() instanceof MissileSileBuild build)){
                return;
            }
            Draw.draw(Layer.overlayUI,()->{
                float radius = build.maxRadius();
                float distance = radius / 4f;
                float time = Time.time;
//                System.out.println("Time: "+time);
                SnShaders.radarCircleLine.setWorld(Pal.remove, Tmp.v1.set(unit.aimX, unit.aimY), (time )%(radius+distance*2f)-distance, distance);
                SnShaders.radarCircleLine.lightColor.a=0.75f;
                Draw.color(Pal.remove,0.25f);
                AFill.donut(unit.aimX,unit.aimY,0f,radius);
                Draw.shader();
                Draw.color(Pal.remove);
                AFill.donut(unit.aimX,unit.aimY,radius,radius+3);
//                Draw.color(Pal.remove);
//                AFill.donut(unit.aimX,unit.aimY,tilesize*1.5f-3,tilesize*1.5f);
            });
        });
    }

    @Override
    public void init(){
        super.init();
        ManyPlanetSystems.init();

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
