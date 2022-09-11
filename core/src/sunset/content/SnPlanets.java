package sunset.content;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import sunset.graphics.SnPal;
import sunset.maps.generators.AzariaGenerator;
import sunset.maps.generators.BurnoutGenerator;
import sunset.maps.generators.HycleGenerator;
import sunset.maps.generators.RimeGenerator;
import sunset.type.planets.SnPlanet;

public class SnPlanets{
    public static Planet
    //stars
    aquilae,
    //planets
    azaria, burnout, rime, 
    //satellites
    hycle;

    public static void load(){
        //region stars
        aquilae = new SnPlanet("UV Aquilae", null, 5f, 0){{
            bloom = true;
            accessible = false;
            hasAtmosphere = true;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.1f;
            atmosphereColor = Color.valueOf("f1f9f1");
            meshLoader = () -> new SunMesh(
            this, 4,
            10, 0.7, 1.9, 1.4, 1.6,
                    1.18f,
            Color.valueOf("a9a39f"),
            Color.valueOf("b5afaa"),
            Color.valueOf("b5afaa"),
            Color.valueOf("c1c7c1"),
            Color.valueOf("f1f9f1"),
            Color.valueOf("d7ded7")

            );
           lightColor = Color.valueOf("f1f9f1");
        }};
        //endregion stars
        //region planets
        azaria = new SnPlanet("azaria", aquilae, 1f, 3){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new AzariaGenerator();
            radius = 1.03f;
            atmosphereRadIn = 0.03f;
            atmosphereRadOut = 0.4f;
            orbitRadius = 63f;
            //orbitTime = 30f;
            rotateTime = Time.toHours;
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

            ruleSetter = r -> {
                r.waveTeam = Team.crux;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.lighting = true;
                r.coreDestroyClear = false;
                r.onlyDepositCore = false;
            };
            hiddenItems.addAll(Items.serpuloItems).removeAll(SnItems.azariaItems);
            hiddenItems.addAll(Items.erekirItems).removeAll(SnItems.azariaItems);
        }};

        burnout = new SnPlanet("burnout", aquilae, 0.7f, 3){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new BurnoutGenerator();
            radius = 0.87f;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.17f;
            sectorApproxRadius = 3;
            orbitRadius = 39;
            //orbitTime = 45f;
            rotateTime = 2f * Time.toHours;
            accessible = true;
            startSector = 20;
            //lightColor = Color.valueOf("E3B3E3");
            atmosphereColor = Color.valueOf("BB4E17");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("D65318");
        }};

        rime = new SnPlanet("rime", aquilae, 0.9f, 3){{
            meshLoader = () -> new HexMesh(this, 6);
            generator = new RimeGenerator();
            radius = 1;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.32f;
            orbitRadius = 91;
            //orbitTime = 40f;
            rotateTime = 40f * Time.toMinutes;
            accessible = true;
            startSector = 1;
            //lightColor = Color.valueOf("B3DDE3");
            atmosphereColor = Color.valueOf("39DACF");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("00A6FF");
        }};
        //endregion planets
        //region satellites
        hycle = new SnPlanet("hycle", azaria, 0.4f, 2){{
            meshLoader = () -> new HexMesh(this, 5);
            generator = new HycleGenerator();
            radius = 0.4f;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.04f;
            orbitRadius = 9f;
            orbitTime = 13f;
            rotateTime = 24f * Time.toHours;
            accessible = true;
            startSector = 16;
            //lightColor = Color.valueOf("B3E3BA");
            atmosphereColor = Color.valueOf("8A8A8A");
            hasAtmosphere = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("B5B5B5");
            camRadius = 0.7f;
        }};
        //endregion satellites
    }
}
