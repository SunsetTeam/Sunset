package sunset.content;

import mindustry.ctype.ContentList;
import mma.type.TechTreeManager;

import static mindustry.content.Items.*;
import static mindustry.content.Liquids.oil;
import static sunset.content.SnItems.*;
import static sunset.content.SnLiquids.burheyna;

public class SnTechTree extends TechTreeManager implements ContentList {
    @Override
    public void load() {
        //region items and liquids
        nodeProduce(lead, () -> {
            nodeProduce(fors, () -> {
                nodeProduce(nobium, () -> {

                });
            });
            nodeProduce(planatrium, () -> {
                nodeProduce(enojie, () -> {

                });
            });
            nodeProduce(flameid, () -> {

            });
            nodeProduce(coldent, () -> {

            });
        });
        nodeProduce(titanium, () -> {
            nodeProduce(reneubite, () -> {

            });
        });
        nodeProduce(sand, () -> {
            nodeProduce(naturite, () -> {

            });
        });
        nodeProduce(oil, () -> {
            nodeProduce(burheyna, () -> {

            });
        });
        //endregion items and liquids
    }
}
