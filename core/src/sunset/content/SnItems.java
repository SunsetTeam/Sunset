package sunset.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;
import sunset.type.Ammo;
import sunset.type.SnItem;

public class SnItems implements ContentList {
    public static Item
    // region azaria
    //raw
    fors, erius, nedirium, planatrium,
    //crafting
    anzar, naturite,  reneubite, nobium,  azalia_bud, yantai, enojie, zerinium,
    //endregion azaria
    //other
    flameid, coldent, testItem,
    lightBurstMissile,
    raMissile, empMissile, lightningMissile, lightMissile,
    burstMissile, detonatorMissile, pointMissile, spotMissile;

    @Override
    public void load() {
        //region azaria
        fors = new Item("fors", Color.valueOf("ed606e")) {{
            cost = 2f;
            hardness = 3;
        }};
        erius = new Item("erius", Color.valueOf("a0d2d6")) {{
            cost = 2f;
        }};
        anzar = new Item("anzar", Color.valueOf("feb380")) {{
            cost = 3f;
        }};
        naturite = new Item("naturite", Color.valueOf("f3e979")) {{
            cost = 2f;
        }};
        nedirium = new Item("nedirium", Color.valueOf("ffffff")) {{
            cost = 3f;
        }};
        reneubite = new Item("reneubite", Color.valueOf("87ceeb")) {{
            cost = 3f;
        }};
        nobium = new Item("nobium", Color.valueOf("c1c4cb")) {{
            cost = 5f;
        }};
        planatrium = new Item("planatrium", Color.valueOf("b287ec")) {{
            cost = 5f;
            hardness = 4;
            explosiveness = 0.2f;
            radioactivity = 1f;
        }};
        azalia_bud = new Item("azalia_bud", Color.valueOf("ff0000")) {{
            cost = 4f;
            flammability = 0.6f;
        }};
        yantai = new Item("yantai", Color.valueOf("fbff93")) {{
            cost = 5f;
        }};
        enojie = new Item("enojie", Color.valueOf("99f2c7")) {{
            cost = 6f;
            radioactivity = 0.8f;
            explosiveness = 0.01f;
        }};
        zerinium = new Item("zerinium", Color.valueOf("ffffff")) {{
            cost = 5f;
        }};
        //endregion azaria

        //region other
        flameid = new Item("flameid", Color.valueOf("ffffff")) {{
            cost = 4f;
            flammability = 0.6f;
            hardness = 4;
        }};
        coldent = new Item("coldent", Color.valueOf("ffffff")) {{
            cost = 4f;
            hardness = 4;
        }};
        //endregion other
        // test
        testItem = new SnItem("test-item", Color.valueOf("000000")){{
            testStat1 = 1f;
            testStat2 = 0.54f;
            testStat3 = 0.71f;
        }};
        /*

        endregion crafted
        endregion items
        region ammo

        lightBurstMissile = new Ammo("light-burst-missile");
        raMissile = new Ammo("ra-missile");
        empMissile = new Ammo("emp-missile");
        lightningMissile = new Ammo("lightning-missile");
        lightMissile = new Ammo("light-missile");
        burstMissile = new Ammo("burst-missile");
        detonatorMissile = new Ammo("detonator-missile");
        pointMissile = new Ammo("point-missile");
        spotMissile = new Ammo("spot-missile");

        //endregion ammo

        */
    }
}