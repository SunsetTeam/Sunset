package sunset.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;
import sunset.type.Ammo;

public class SnItems implements ContentList {
    public static Item
    //items
    //raw
    fors, planatrium, flameid, coldent,
    //crafted
    naturite, nobium, anzar, enojie, reneubite,
    //ammo
    lightBurstMissile,
    raMissile, empMissile, lightningMissile, lightMissile,
    burstMissile, detonatorMissile, pointMissile, spotMissile;
    @Override
    public void load() {
        //region items
        //region raw
        fors = new Item("fors", Color.valueOf("d15361")) {{
            cost = 3f;
            hardness = 3;
        }};
        planatrium = new Item("planatrium", Color.valueOf("a759af")) {{
            cost = 5f;
            hardness = 4;
            explosiveness = 0.2f;
            radioactivity = 1f;
        }};
        flameid = new Item("flameid", Color.valueOf("EA8878")) {{
            cost = 4f;
            flammability = 0.6f;
            hardness = 4;
        }};
        coldent = new Item("coldent", Color.valueOf("BAF4F5FF")) {{
            cost = 4f;
            hardness = 4;
        }};
        //endregion raw
        //region crafted
        naturite = new Item("naturite", Color.valueOf("e4d071")) {{
            flammability = 0.3f;
            cost = 3f;
        }};
        nobium = new Item("nobium", Color.valueOf("eeeded")) {{
            cost = 5f;
        }};
        anzar = new Item("anzar", Color.valueOf("feb380")){{
            cost = 4f;
        }};
        enojie = new Item("enojie", Color.valueOf("75e0ac")) {{
            radioactivity = 0.8f;
            explosiveness = 0.06f;
            cost = 6f;
        }};
        reneubite = new Item("reneubite", Color.valueOf("87ceeb")) {{
            cost = 3f;
            flammability = 0.8f;
            explosiveness = 0.7f;
        }};
        //endregion crafted
        //endregion items
        //region ammo

        /*lightBurstMissile = new Ammo("light-burst-missile");
        raMissile = new Ammo("ra-missile");
        empMissile = new Ammo("emp-missile");
        lightningMissile = new Ammo("lightning-missile");
        lightMissile = new Ammo("light-missile");
        burstMissile = new Ammo("burst-missile");
        detonatorMissile = new Ammo("detonator-missile");
        pointMissile = new Ammo("point-missile");
        spotMissile = new Ammo("spot-missile");
        */
        //endregion ammo
    }
}