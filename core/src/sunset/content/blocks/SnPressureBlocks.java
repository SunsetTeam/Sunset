package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import pl.world.blocks.distribution.PressurePipe;
import pl.world.blocks.distribution.PressureValve;
import pl.world.blocks.pressure.Pressure;
import pl.world.blocks.sandbox.PressureSource;
import sunset.content.SnItems;

import static mindustry.type.ItemStack.*;

public class SnPressureBlocks {
  public static Block
  pressurePipe,
  pressureSource,
  pressureValve;

  public static void load() {
    pressurePipe = new PressurePipe("pressure-pipe") {{
      requirements(Category.liquid, with(
              SnItems.erius, 1,
              Items.silicon, 1
      ));
      health = 120;
    }};
    pressureValve = new PressureValve("pressure-valve") {{
      requirements(Category.liquid, with(
              SnItems.erius, 4,
              Items.silicon, 6,
              Items.graphite, 3
      ));
      health = 160;
      pressureC = new Pressure() {{
        internalSize = 4f;
      }};
    }};
    pressureSource = new PressureSource("pressure-source") {{
      category = Category.liquid;
    }};
  }
}
