package sunset.content;

import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.ctype.ContentList;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.type.Planet;
import sunset.maps.generators.BurnoutGenerator;
import sunset.maps.generators.AzariaGenerator;
import sunset.maps.generators.RimeGenerator;

public class SnPlanets implements ContentList{
    public static Planet
    magma, burnout, azaria, rime;

    @Override
    public void load(){
        magma = new Planet("magma", Planets.sun, 0, 3.6f){{
            bloom = true;
            accessible = false;
            hasAtmosphere = true;
            orbitRadius = 54;
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

        burnout = new Planet("burnout", SnPlanets.magma, 2, 0.7f){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new BurnoutGenerator();
            radius = 0.8f;
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.19f;
            sectorApproxRadius = 3;
            orbitRadius = 16;
            orbitTime = 17f;
            rotateTime = 15f*30f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("E3B3E3");
            atmosphereColor = Color.valueOf("BB4E17");
            hasAtmosphere = true;
            alwaysUnlocked = true;
        }};

        azaria = new Planet("azaria", SnPlanets.magma, 3, 1f){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new AzariaGenerator();
            radius = 1.05f;
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.4f;
            orbitRadius = 21.3f;
            orbitTime = 24.1f;
            rotateTime = 15f*30f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3E3BA");
            atmosphereColor = Color.valueOf("68181C");
            hasAtmosphere = true;
            alwaysUnlocked = true;
        }};

        rime = new Planet("rime", SnPlanets.magma, 3, 0.9f){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new RimeGenerator();
            radius = 1;
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.28f;
            orbitRadius = 29;
            orbitTime = 36f;
            rotateTime = 15f*30f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3DDE3");
            atmosphereColor = Color.valueOf("CFF7F5");
            hasAtmosphere = true;
            alwaysUnlocked = true;
        }};
    }
}
