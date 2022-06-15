package sunset.type;

import mindustry.world.*;
import mma.type.pixmap.*;

public interface ImageDrawBlockGenerator{
    default void preGenerate(Block block, PixmapProcessor processor){};
    default void postGenerate(Block block, PixmapProcessor processor){};
}
