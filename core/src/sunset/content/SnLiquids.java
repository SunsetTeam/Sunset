package sunset.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class SnLiquids implements ContentList{
    public static Liquid burheyna;

    @Override
    public void load(){
     
        burheyna = new Liquid("burheyna", Color.valueOf("962222")){{
            viscosity = 0.8f;
            flammability = 0.5f;
            explosiveness = 0.8f;
            heatCapacity = 0.5f;
            barColor = Color.valueOf("871818");
            effect = StatusEffects.wet;
        }};
    }
}
