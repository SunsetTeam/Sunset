package sunset.type.unitTypes;

import arc.Core;
import mindustry.gen.EntityMapping;
import mindustry.graphics.MultiPacker;
import mindustry.type.UnitType;
import mma.ModVars;

public class SnUnitType extends UnitType {
    public SnUnitType(String name) {
        super(name);
        outlines= ModVars.packSprites;
        if (constructor == null) {
            constructor = EntityMapping.map(name);
        }
    }

    @Override
    public void load() {
        super.load();
//        loadIcon();
        shadowRegion = Core.atlas.find(name + "-shadow", shadowRegion);
    }

    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);
    }
}
