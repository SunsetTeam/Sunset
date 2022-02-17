package sunset.world.blocks.laser;

/** Wrap class for link between start self (See LaserModule) and end block.
 * @see LaserModule */
public class LaserLink {
    public LaserBlock.LaserBlockBuild build;
    public int pos;

    public LaserLink(int pos){
        this.pos = pos;
    }
}
