package sunset.type.planets;

import mindustry.graphics.g3d.*;
import mindustry.type.*;

public class SnPlanet extends Planet{
    public SnPlanet(String name, Planet parent, float radius){
        super(name, parent, radius);
    }

    public SnPlanet(String name, Planet parent, float radius, int sectorSize){
        super(name, parent, radius, 0);
        if(sectorSize > 0){
            grid = PlanetGrid.create(sectorSize);

            sectors.ensureCapacity(grid.tiles.length);
            for(int i = 0; i < grid.tiles.length; i++){
                sectors.add(new SnSector(this, grid.tiles[i]));
            }

            sectorApproxRadius = sectors.first().tile.v.dst(sectors.first().tile.corners[0].v);
        }
    }
}
