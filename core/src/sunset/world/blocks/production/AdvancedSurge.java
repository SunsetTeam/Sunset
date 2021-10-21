package sunset.world.blocks.production;

import mindustry.world.blocks.production.GenericCrafter;

// A GenericCrafter with a static top region
@Deprecated
public class AdvancedSurge extends GenericCrafter {

    public AdvancedSurge(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
    }

    @Deprecated
    public class StaticTopSmelterBuild extends GenericCrafterBuild {
        public void draw(StaticTopSmelterBuild entity) {
            super.draw();

        }

    }
}
