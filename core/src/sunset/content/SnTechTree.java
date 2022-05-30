package sunset.content;

import arc.struct.Seq;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.node;
import static sunset.content.blocks.SnCore.*;
import static sunset.content.blocks.SnCrafting.*;
import static sunset.content.SnItems.*;
import static sunset.content.SnGas.*;
import static sunset.content.SnLiquids.*;

public class SnTechTree{

    public static void load(){
        SnPlanets.azaria.techTree = nodeRoot("azaria", testCore, () -> {
            //region crafting
            node(crystallizer, () -> {
                node(anzarCaster, () -> {
                    node(nobiumCaster, () -> {
                        node(nobiumMagnetizer, () -> {
                            node(enojieSynthesizer, () -> {
                            });
                            node(zeriniumZavod, () -> {
                            });
                        });
                    });
                    node(collider, () -> {
                    });
                });
            });
            //endregion crafting

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
