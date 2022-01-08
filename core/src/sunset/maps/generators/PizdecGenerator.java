package sunset.maps.generators;

import arc.func.*;
import arc.graphics.*;
import arc.math.geom.*;
import mindustry.maps.generators.*;

public class PizdecGenerator extends PlanetGenerator{
    private final Func2<PizdecGenerator,Vec3,Float> heightProv;
    private final Func2<PizdecGenerator,Vec3,Color> colorFunc;

    public PizdecGenerator(Func2<PizdecGenerator, Vec3, Float> heightProv, Func2<PizdecGenerator, Vec3, Color> colorFunc){
        this.heightProv = heightProv;
        this.colorFunc = colorFunc;
    }

    public PizdecGenerator(Func2<PizdecGenerator, Vec3, Float> heightProv){
        this(heightProv,(param1, param2) -> Color.gray);
    }

    @Override
    public float getHeight(Vec3 position){
        return heightProv.get(this,position);
    }

    @Override
    public Color getColor(Vec3 position){
        return colorFunc.get(this,position);
    }
}
