package sunset.content;

import mma.tools.*;

import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.TechTree.nodeRoot;
import static sunset.content.blocks.SnCore.*;
import static sunset.content.SnItems.*;
import static sunset.content.SnGas.*;
import static sunset.content.SnLiquids.*;

public class SnTechTree{

    public static void load(){
        SnPlanets.azaria.techTree = nodeRoot("azaria", testCore, () -> {
            //region items and liquids
            nodeProduce(fors, () -> {
                nodeProduce(burheyna, () -> {
                    nodeProduce(messira, () -> {
                    });
                });

                nodeProduce(erius, () -> {
                    nodeProduce(nedirium, () -> {
                        nodeProduce(nobium, () -> {
                            nodeProduce(planatrium, () -> {
                                nodeProduce(arhelinium, () -> {
                                });
                                nodeProduce(enojie, () -> {
                                });
                                nodeProduce(zerinium, () -> {
                                });
                            });
                        });
                        nodeProduce(reneubite, () -> {
                        });
                    });

                    nodeProduce(hyneris, () -> {
                    });
                });

                nodeProduce(gyner, () -> {
                });

                nodeProduce(azalia_bud, () -> {
                    nodeProduce(sayang, () -> {
                        nodeProduce(yantai, () -> {
                        });
                        nodeProduce(nabunium, () -> {
                        });
                    });
                });

                nodeProduce(naturite, () -> {
                    nodeProduce(anzar, () -> {
                    });
                });
            });
        });
        //endregion turrets
        //endregion blocks
    }
}
