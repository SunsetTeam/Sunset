package monolith.planets;

import arc.math.*;
import arc.util.*;
import arc.func.*;
import arc.struct.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.ai.Astar.*;
import mindustry.maps.generators.*;

/** 
	@author uujuju 
	other updates will be made in monolith(mayme make in a separate mod ig)
*/

public class ModularPlanetGenerator extends PlanetGenerator {
	public float minHeight = 0.1f;
	public double scale = 2, persistence =  0.7, octaves = 7;
	public Seq<Biome> biomes = new Seq<>();
	public Cons<ModularPlanetGenerator> gen = p -> p.pass((x, y) -> {p.floor = getBlock(p.sector.tile.v);});
	public Cons<Sector> genSectors = p -> {};
	public Block defaultBlock = Blocks.stone;

	// un protect the things
	public Sector sector() {return sector;}
	public Rand rand() {return rand;}
	public Tiles tiles() {return tiles;}
	public float width() {return width;}
	public float height() {return height;}

	public Block setFloor(Block floor) {return this.floor = floor;}
	public Block setBlock(Block block) {return this.block = block;}
	public Block setOre(Block ore) {return this.ore = ore;}

	public @Nullable Biome getBiome(Vec3 pos) {
		Biome res = null;
		for (Biome biome : biomes) if (biome.isValid(pos)) res = biome;
		return res;
	}

	public class Biome {
		// array tileset, i reccomend 10 - 13 blocks here
		public Block[] heightMap;
		public Seq<OreEntry> ores = Seq.with(
			new OreEntry(Blocks.oreCopper, 0.8f),
			new OreEntry(Blocks.oreLead, 0.83f)
		);

		// equator to pole interpolation
		public Interp polarInterp = Interp.one;

		// seed for noise
		public int noiseSeed = 0;

		public float 
		// height that the biome starts
		minValue = 0f,
		// height that the biome ends
		maxValue = 1f,

		// noise magnitude
		magnitude = 1f;

		/**
			@param clampHeight will override any biome before it when set to true
			clamps noise onto min and max values
			this will apply the entire noise height map onto the entire planet!
		*/
		public boolean clampHeight = false;

		public double
		// x, y and z noise offsets
		xOffset = 0,
		yOffset = 0,
		zOffset = 0,

		// noise octaves
		octaves = 3, 
		// noise persistence
		persistence = 0.5,
		// noise scale
		scale = 1;

		public float noise(Vec3 pos) {
			float noise = Simplex.noise3d(seed, octaves, persistence, scale, pos.x + xOffset, pos.y + yOffset, pos.z + zOffset) * magnitude * polarInterp.apply(Math.abs(pos.y));
			return clampHeight ? Mathf.clamp(Math.max(0, noise), minValue, maxValue) : Math.max(0, noise);
		}
		public @Nullable Block getBlock(Vec3 pos) {
			Block res = heightMap[Mathf.clamp((int) (noise(pos) * (heightMap.length - 1f)), 0, heightMap.length - 1)];
			return (noise(pos) < minValue || noise(pos) > maxValue) ? null : res;
		}
		public boolean isValid(Vec3 pos) {return (noise(pos) > minValue && noise(pos) < maxValue);}
	}

	public class OreEntry {
		public float tresh = 0.7f;
		public Block ore = Blocks.oreCopper;
		public OreEntry(Block ore, float tresh) {
			this.ore = ore;
			this.tresh = tresh;
		}
	}
	
	float rawHeight(Vec3 pos) {return Simplex.noise3d(seed, octaves, persistence, scale, pos.x, pos.y, pos.z);}
	public Block getBlock(Vec3 pos) {
		@Nullable Block res = null;
		if(getBiome(pos) != null) if(getBiome(pos).getBlock(pos) != null) res = getBiome(pos).getBlock(pos);
		return res == null ? defaultBlock : res;
	}

	@Override
	public float getHeight(Vec3 pos) {return Math.max(rawHeight(pos), minHeight);}

	@Override
	public Color getColor(Vec3 pos) {return getBlock(pos).mapColor;}

	@Override
	public void generateSector(Sector sector) {genSectors.get(sector);}

	@Override
	protected void generate() {gen.get(this);}
}