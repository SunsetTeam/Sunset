package sunset.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;
import sunset.type.SnItem;

public class SnItems {
    public static Item
    // region azaria
    //raw
    fors, erius, nedirium, planatrium,
    //crafting
    zarium, naturite, lenothium, nobium, azaliaBud, yantai, entirium, zerinium;
    //endregion azaria
    //other
    //flameid, coldent, testItem;

    public static final Seq<Item> azariaItems = new Seq<>();

    public static void load() {
        //region azaria
        fors = new Item("fors", Color.valueOf("d74354")) {{
            cost = 1.4f;
            hardness = 3;
        }};
        erius = new Item("erius", Color.valueOf("a0d2d6")) {{
            cost = 1.2f;
            hardness = 2;
        }};
        naturite = new Item("naturite", Color.valueOf("f9f688")) {{
            cost = 1.8f;
        }};
        zarium = new Item("anzar", Color.valueOf("feb380")) {{
            cost = 2f;
        }};
        azaliaBud = new Item("azalia-bud", Color.valueOf("a52f49")) {{
            cost = 4f;
            flammability = 0.6f;
        }};
        nedirium = new Item("nedirium", Color.valueOf("ffd6f2")) {{
            cost = 3f;
            hardness = 4;
        }};
        nobium = new Item("nobium", Color.valueOf("fbffda")) {{
            cost = 5f;
        }};
        lenothium = new Item("reneubite", Color.valueOf("b0fbff")) {{
            cost = 3.5f;
        }};
        yantai = new Item("yantai", Color.valueOf("ffed93")) {{
            cost = 4.3f;
        }};
        planatrium = new Item("planatrium", Color.valueOf("c997e2")) {{
            cost = 5f;
            hardness = 4;
            explosiveness = 0.2f;
            radioactivity = 1f;
        }};
        entirium = new Item("enojie", Color.valueOf("61f1aa")) {{
            cost = 6f;
            radioactivity = 0.8f;
            explosiveness = 0.01f;
        }};
        zerinium = new Item("zerinium", Color.valueOf("c6d8d9")) {{
            cost = 5f;
        }};
        //endregion azaria

        //region other
        /*flameid = new Item("flameid", Color.valueOf("ffffff")) {{
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

        azariaItems.addAll(
                fors, erius, naturite, anzar, nedirium, reneubite, nobium, planatrium, azaliaBud,
                yantai, enojie, zerinium
        );*/
    }
}
