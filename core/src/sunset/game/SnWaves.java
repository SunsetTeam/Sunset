package sunset.game;

import arc.func.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.type.*;
import sunset.content.*;

import static mindustry.content.UnitTypes.mega;

public class SnWaves{
    public final int waveVersion;
    private UnitType[][] species;

    public SnWaves(){
        this(-2);
        species(
        line(SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind),
        line(SnUnitTypes.torpedo1, SnUnitTypes.torpedo1, SnUnitTypes.torpedo1, SnUnitTypes.torpedo1, SnUnitTypes.torpedo1),
        line(SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind),
        line(SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind),
        line(SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind),
        line(SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind, SnUnitTypes.wind)
        );
    }

    public SnWaves(int waveVersion){
        this.waveVersion = waveVersion;
    }

    protected UnitTypeLine line(UnitType t1, UnitType t2, UnitType t3, UnitType t4, UnitType t5, UnitType... extra){
        return new UnitTypeLine(t1, t2, t3, t4, t5, extra);
    }

    public SnWaves species(UnitTypeLine... species){
        this.species = new UnitType[species.length][];
        for(int i = 0; i < this.species.length; i++){
            this.species[i] = species[i].array;
        }
        return this;
    }

    public Seq<SpawnGroup> generate(float difficulty){
        //apply power curve to make starting sectors easier
        return generate(Mathf.pow(difficulty, 1.12f), new Rand(), false);
    }

    public Seq<SpawnGroup> generate(float difficulty, Rand rand, boolean attack){
        return generate(difficulty, rand, attack, false);
    }

    public Seq<SpawnGroup> generate(float difficulty, Rand rand, boolean attack, boolean airOnly){
        return generate(difficulty, rand, attack, airOnly, false);
    }

    public Seq<SpawnGroup> generate(float difficulty, Rand rand, boolean attack, boolean airOnly, boolean naval){
        UnitType[][] species = new UnitType[this.species.length][];
        for(int i = 0; i < species.length; i++){
            UnitType[] root = this.species[i];
            species[i] = new UnitType[root.length];
            System.arraycopy(root, 0, species[i], 0, root.length);
        }
        if(airOnly){
            species = Structs.filter(UnitType[].class, species, v -> v[0].flying);
        }

        if(naval){
            species = Structs.filter(UnitType[].class, species, v -> v[0].flying || v[0].naval);
        }else{
            species = Structs.filter(UnitType[].class, species, v -> !v[0].naval);
        }

        UnitType[][] fspec = species;

        //required progression:
        //- extra periodic patterns

        Seq<SpawnGroup> out = new Seq<>();

        //max reasonable wave, after which everything gets boring
        int cap = 150;

        float shieldStart = 30, shieldsPerWave = 20 + difficulty * 30f;
        float[] scaling = {1, 2f, 3f, 4f, 5f};

        Intc createProgression = start -> {
            //main sequence
            UnitType[] curSpecies = Structs.random(fspec);
            int curTier = 0;

            for(int i = start; i < cap; ){
                int f = i;
                int next = rand.random(8, 16) + (int)Mathf.lerp(5f, 0f, difficulty) + curTier * 4;

                float shieldAmount = Math.max((i - shieldStart) * shieldsPerWave, 0);
                int space = start == 0 ? 1 : rand.random(1, 2);
                int ctier = curTier;

                //main progression
                out.add(new SpawnGroup(curSpecies[Math.min(curTier, curSpecies.length - 1)]){{
                    unitAmount = f == start ? 1 : 6 / (int)scaling[ctier];
                    begin = f;
                    end = f + next >= cap ? never : f + next;
                    max = 13;
                    unitScaling = (difficulty < 0.4f ? rand.random(2.5f, 5f) : rand.random(1f, 4f)) * scaling[ctier];
                    shields = shieldAmount;
                    shieldScaling = shieldsPerWave;
                    spacing = space;
                }});

                //extra progression that tails out, blends in
                out.add(new SpawnGroup(curSpecies[Math.min(curTier, curSpecies.length - 1)]){{
                    unitAmount = 3 / (int)scaling[ctier];
                    begin = f + next - 1;
                    end = f + next + rand.random(6, 10);
                    max = 6;
                    unitScaling = rand.random(2f, 4f);
                    spacing = rand.random(2, 4);
                    shields = shieldAmount / 2f;
                    shieldScaling = shieldsPerWave;
                }});

                i += next + 1;
                if(curTier < 3 || (rand.chance(0.05) && difficulty > 0.8)){
                    curTier++;
                }

                //do not spawn bosses
                curTier = Math.min(curTier, 3);

                //small chance to switch species
                if(rand.chance(0.3)){
                    curSpecies = Structs.random(fspec);
                }
            }
        };

        createProgression.get(0);

        int step = 5 + rand.random(5);

        while(step <= cap){
            createProgression.get(step);
            step += (int)(rand.random(15, 30) * Mathf.lerp(1f, 0.5f, difficulty));
        }

        int bossWave = (int)(rand.random(50, 70) * Mathf.lerp(1f, 0.5f, difficulty));
        int bossSpacing = (int)(rand.random(25, 40) * Mathf.lerp(1f, 0.5f, difficulty));

        int bossTier = difficulty < 0.6 ? 3 : 4;

        //main boss progression
        out.add(new SpawnGroup(Structs.random(species)[bossTier]){{
            unitAmount = 1;
            begin = bossWave;
            spacing = bossSpacing;
            end = never;
            max = 16;
            unitScaling = bossSpacing;
            shieldScaling = shieldsPerWave;
            effect = StatusEffects.boss;
        }});

        //alt boss progression
        out.add(new SpawnGroup(Structs.random(species)[bossTier]){{
            unitAmount = 1;
            begin = bossWave + rand.random(3, 5) * bossSpacing;
            spacing = bossSpacing;
            end = never;
            max = 16;
            unitScaling = bossSpacing;
            shieldScaling = shieldsPerWave;
            effect = StatusEffects.boss;
        }});

        int finalBossStart = 120 + rand.random(30);

        //final boss waves
        out.add(new SpawnGroup(Structs.random(species)[bossTier]){{
            unitAmount = 1;
            begin = finalBossStart;
            spacing = bossSpacing / 2;
            end = never;
            unitScaling = bossSpacing;
            shields = 500;
            shieldScaling = shieldsPerWave * 4;
            effect = StatusEffects.boss;
        }});

        //final boss waves (alt)
        out.add(new SpawnGroup(Structs.random(species)[bossTier]){{
            unitAmount = 1;
            begin = finalBossStart + 15;
            spacing = bossSpacing / 2;
            end = never;
            unitScaling = bossSpacing;
            shields = 500;
            shieldScaling = shieldsPerWave * 4;
            effect = StatusEffects.boss;
        }});

        //add megas to heal the base.
        if(attack && difficulty >= 0.5){
            int amount = Mathf.random(1, 3 + (int)(difficulty * 2));

            for(int i = 0; i < amount; i++){
                int wave = Mathf.random(3, 20);
                out.add(new SpawnGroup(mega){{
                    unitAmount = 1;
                    begin = wave;
                    end = wave;
                    max = 16;
                }});
            }
        }

        //shift back waves on higher difficulty for a harder start
        int shift = Math.max((int)(difficulty * 14 - 5), 0);

        for(SpawnGroup group : out){
            group.begin -= shift;
            group.end -= shift;
        }

        return out;
    }

    private static class UnitTypeLine{
        private final UnitType[] array;

        private UnitTypeLine(UnitType t1, UnitType t2, UnitType t3, UnitType t4, UnitType t5, UnitType... extra){
            array = new UnitType[extra.length + 5];
            UnitType[] types = {t1, t2, t3, t4, t5};
            System.arraycopy(types, 0, array, 0, types.length);
            System.arraycopy(extra, 0, array, 5, extra.length);
        }
    }
}
