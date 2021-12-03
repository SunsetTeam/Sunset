package sunset.content;

import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.ctype.ContentList;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.type.Planet;
import sunset.graphics.SnPal;
import sunset.maps.generators.AzariaGenerator;
import sunset.maps.generators.BurnoutGenerator;
import sunset.maps.generators.RimeGenerator;

public class SnPlanets implements ContentList {
    public static Planet
            //stars
            magma,
            //planets
            burnout, azaria, rime;

    @Override
    public void load() {
        //region stars
        magma = new Planet("magma", Planets.sun, 3.6f, 0) {{
            bloom = true;
            accessible = false;
            hasAtmosphere = true;
            orbitRadius = 75;
            meshLoader = () -> new SunMesh(
                this, 7,
                10, 0.7, 1.9, 1.4, 1.6,
                0.9f,
                Color.valueOf("FF7700"),
                Color.valueOf("FFD738"),
                Color.valueOf("FF5500"),
                Color.valueOf("FF5500"),
                Color.valueOf("FFB514"),
                Color.valueOf("F5EA58")
            );
            lightColor = Color.valueOf("F5E14E");
        }};
        //endregion stars
        //region planets
        burnout = new Planet("burnout", SnPlanets.magma, 0.7f, 2) {{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new BurnoutGenerator();
            radius = 0.87f;
            atmosphereRadIn = 0.019f;
            atmosphereRadOut = 0.19f;
            sectorApproxRadius = 3;
            orbitRadius = 20;
            orbitTime = 17f;
            rotateTime = 15f*30f;
            accessible = true;
            startSector = 20;
            //lightColor = Color.valueOf("E3B3E3");
            atmosphereColor = Color.valueOf("BB4E17");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("D65318");
        }};

        azaria = new Planet("azaria", SnPlanets.magma, 1f, 3) {{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new AzariaGenerator();
            radius = 1.03f;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.4f;
            orbitRadius = 29f;
            orbitTime = 24.1f;
            rotateTime = 15f*30f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3E3BA");
            atmosphereColor = Color.valueOf("68181C");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = SnPal.azaria.cpy().a(0.5f);
        }};

        rime = new Planet("rime", SnPlanets.magma, 0.9f, 3) {{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new RimeGenerator();
            radius = 1;
            atmosphereRadIn = 0.016f;
            atmosphereRadOut = 0.28f;
            orbitRadius = 40;
            orbitTime = 36f;
            rotateTime = 15f*30f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3DDE3");
            atmosphereColor = Color.valueOf("59C2BBFF");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("00A6FF");
        }};
        //endregion planets
    }
}