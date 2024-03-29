package sunset.core;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.func.Func;
import arc.scene.Group;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;
import mindustry.world.Block;
import mma.*;
import mma.customArc.cfunc.Couple;

import java.lang.reflect.Field;

import static arc.Core.bundle;
import static arc.Core.settings;
import static mindustry.Vars.ui;

public class SnSettings implements ApplicationListener {
    public SettingsTable snSettings;
    public ObjectMap<Couple<Block, OrderedMap<String, Func<Building, Bar>>>, Func<Building, Bar>> reloadBarBlock = new ObjectMap<>();

    public SnSettings() {
    }

    public <T extends Building> void registerReloadBarBlock(Block block, Func<T, Bar> prov) {
        if (ModVars.packSprites)return;
        OrderedMap<String, Func<Building, Bar>> map;
        try {

            Field field = Block.class.getDeclaredField("barMap");
            field.setAccessible(true);
            //noinspection unchecked
            map = (OrderedMap<String, Func<Building, Bar>>) field.get(block);
        } catch (Exception e) {
            map = null;
        }
        //noinspection unchecked
        reloadBarBlock.put(Couple.of(block, map), (Func<Building, Bar>) prov);

    }

    public boolean reloadBar() {
        return settings.getBool("sn-reloadbar", false);
    }

    public void reloadBar(boolean reloadBar) {
        settings.put("sn-reloadbar", reloadBar);
        updateReloadBars();
    }

    public boolean advancedShadows() {
        return settings.getBool("sn-advancedShadows", false);
    }

    public void advancedShadows(boolean advancedShadows) {
        settings.put("sn-advancedShadows", advancedShadows);
    }

    public void updateReloadBars() {
        boolean bar = reloadBar();
        final String barName = "reload";
        for (ObjectMap.Entry<Couple<Block, OrderedMap<String, Func<Building, Bar>>>, Func<Building, Bar>> entry : reloadBarBlock) {
            Block block = entry.key.o1;
            OrderedMap<String, Func<Building, Bar>> map = entry.key.o2;
            Func<Building, Bar> barFunc = entry.value;
            boolean contains = false;
            if (map != null) {
                contains = map.containsKey(barName);
            } else {
                try {
                    block.removeBar(barName);
                } catch (Exception ignored) {

                }
            }
            if (bar && !contains) {
                block.addBar(barName, barFunc);
            } else if (!bar && contains) {
                block.removeBar(barName);
            }
        }
    }

    public void init() {
        if (!Vars.headless){
            updateReloadBars();
            ui.settings.game.checkPref("sn-reloadbar",false,this::reloadBar);
            ui.settings.graphics.checkPref("sn-advancedshadows",false,this::advancedShadows);
        }
    }

}
