package sunset.content;

import arc.graphics.*;
import arc.math.*;
import mindustry.ctype.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import sunset.graphics.*;
import sunset.maps.generators.*;

public class SnPlanets implements ContentList{
    public static Planet
    //stars
    magma,
    //planets
    burnout, azaria, rime;

    @Override
    public void load(){
        //region stars
        magma = new Planet("magma", null, 4f, 0){{
            bloom = true;
            accessible = false;
            hasAtmosphere = true;
            orbitRadius = 145;
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
           // lightColor = Color.valueOf("F5E14E");
        }};
        //endregion stars
        //region planets
        burnout = new Planet("burnout", SnPlanets.magma, 0.7f, 3){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new BurnoutGenerator();
            radius = 0.87f;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.19f;
            sectorApproxRadius = 3;
            orbitRadius = 33;
            orbitTime = 45f;
            rotateTime = 10f * 120f;
            accessible = true;
            startSector = 20;
            //lightColor = Color.valueOf("E3B3E3");
            atmosphereColor = Color.valueOf("BB4E17");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("D65318");
        }};

        azaria = new Planet("azaria", SnPlanets.magma, 1f, 3){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new AzariaGenerator();
            radius = 1.03f;
            atmosphereRadIn = 0.03f;
            atmosphereRadOut = 0.5f;
            orbitRadius = 58f;
            orbitTime = 30f;
            rotateTime = 18f * 120f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3E3BA");
            atmosphereColor = Color.valueOf("68181C");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = SnPal.azaria.cpy().a(0.5f);
            cloudMeshLoader = () -> new MultiMesh(
            new HexSkyMesh(this, 11, 0.11f, 0.13f, 5, new Color().set(SnPal.azariaClouds).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f)
            );
        }};

        rime = new Planet("rime", SnPlanets.magma, 0.9f, 3){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new RimeGenerator();
            radius = 1;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.35f;
            orbitRadius = 85;
            orbitTime = 40f;
            rotateTime = 24f * 120f;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3DDE3");
            atmosphereColor = Color.valueOf("39DACF");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("00A6FF");
        }};
        //endregion planets

    }
}
