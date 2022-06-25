package sunset.world.blocks.defense.turrets;

import mindustry.gen.Buildingc;
import mindustry.gen.Player;
import mindustry.world.Tile;

public interface MissileSileBuild extends Buildingc {
    boolean tryShoot(float shootX, float shootY);

    default void drawControlled() {

    }
    float maxRadius();
     void drawActiveTerritory();
    void tapTile(Player player, Tile tile);
}
