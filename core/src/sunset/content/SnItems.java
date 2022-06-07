package sunset.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;
import sunset.type.Ammo;
import sunset.type.SnItem;

public class SnItems {
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

    public static final Seq<Item> azariaItems = new Seq<>();

    public static void load() {
        //region azaria
        fors = new Item("fors", Color.valueOf("ed606e")) {{
            cost = 1.4f;
            hardness = 3;
        }};
        erius = new Item("erius", Color.valueOf("a0d2d6")) {{
            cost = 1.2f;
            hardness = 2;
        }};
        naturite = new Item("naturite", Color.valueOf("dff988")) {{
            cost = 1.8f;
        }};
        anzar = new Item("anzar", Color.valueOf("feb380")) {{
            cost = 2f;
        }};
        nedirium = new Item("nedirium", Color.valueOf("e2ffd6")) {{
            cost = 3f;
            hardness = 4;
        }};
        reneubite = new Item("reneubite", Color.valueOf("b0fbff")) {{
            cost = 3.5f;
        }};
        nobium = new Item("nobium", Color.valueOf("e2fbee")) {{
            cost = 5f;
        }};
        planatrium = new Item("planatrium", Color.valueOf("b287ec")) {{
            cost = 5f;
            hardness = 5;
            explosiveness = 0.2f;
            radioactivity = 1f;
        }};
        azalia_bud = new Item("azalia_bud", Color.valueOf("a52f49")) {{
            cost = 4f;
            flammability = 0.6f;
        }};
        yantai = new Item("yantai", Color.valueOf("ffed93")) {{
            cost = 4.3f;
        }};
        enojie = new Item("enojie", Color.valueOf("99f2c7")) {{
            cost = 6f;
            radioactivity = 0.8f;
            explosiveness = 0.01f;
        }};
        zerinium = new Item("zerinium", Color.valueOf("c1c4cb")) {{
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

        azariaItems.addAll(
                fors, erius, naturite, anzar, nedirium, reneubite, nobium, planatrium, azalia_bud,
                yantai, enojie, zerinium
        );
    }
}