package sunset;

import arc.assets.Loadable;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.gen.Groups;
import sunset.utils.V7.SunsetWaveSpawner;

public class SunsetVars extends Vars implements Loadable{
    public static SunsetWaveSpawner spawner;

    public static final int maxSchematicSize = 128;
    public static Renderer renderer;

    public static void init() {
        Groups.init();
        spawner = new SunsetWaveSpawner();
        renderer.minZoom = 0.5f;
        renderer.maxZoom = 25;
    }
}
