package sunset;

import mindustry.core.Renderer;
//import sunset.utils.V7.SunsetWaveSpawner;

public class SunsetVars{

    //public static SunsetWaveSpawner spawner;

    public static final int maxSchematicSize = 128;
    public static Renderer renderer;

    public static void init() {
        //spawner = new SunsetWaveSpawner();
        renderer.minZoom = 0.5f;
        renderer.maxZoom = 25;
    }
}
