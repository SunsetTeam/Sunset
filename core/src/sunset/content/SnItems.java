package sunset.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;
import sunset.type.Ammo;

public class SnItems implements ContentList {
    public static Item
    //items
    //azaria
    fors, erius, anzar, naturite, nedirium, reneubite, nobium, planatrium, yobut, yantai, enojii, zerinium,
    //other
    flameid, coldent,
    lightBurstMissile,
    raMissile, empMissile, lightningMissile, lightMissile,
    burstMissile, detonatorMissile, pointMissile, spotMissile;

    @Override
    public void load() {
        //azaria
        fors = new Item("fors", Color.valueOf("ed606e")) {{
            cost = 3f;
            hardness = 3;
        }};
        erius = new Item("erius", Color.valueOf("a0d2d6")) {{
            cost = 3f;
        }};
        anzar = new Item("anzar", Color.valueOf("feb380")) {{
            cost = 3f;
        }};
        naturite = new Item("naturite", Color.valueOf("f3e979")) {{
            cost = 3f;
        }};
        nedirium = new Item("nedirium", Color.valueOf("ffffff")) {{
            cost = 3f;
        }};
        reneubite = new Item("reneubite", Color.valueOf("87ceeb")) {{
            cost = 3f;
        }};
        nobium = new Item("nobium", Color.valueOf("c1c4cb")) {{
            cost = 3f;
        }};
        planatrium = new Item("planatrium", Color.valueOf("b287ec")) {{
            cost = 3f;
        }};
        yobut = new Item("yobut", Color.valueOf("ff0000")) {{
            cost = 3f;
        }};
        yantai = new Item("yantai", Color.valueOf("fbff93")) {{
            cost = 3f;
        }};
        enojii = new Item("enojii", Color.valueOf("99f2c7")) {{
            cost = 3f;
        }};
        zerinium = new Item("zerinium", Color.valueOf("ffffff")) {{
            cost = 3f;
        }};
        flameid = new Item("flameid", Color.valueOf("ffffff")) {{
            cost = 3f;
        }};
        coldent = new Item("coldent", Color.valueOf("ffffff")) {{
           cost = 3f;
        }};
        // test
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