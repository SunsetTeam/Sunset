package sunset.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;
import sunset.type.SayangLiquid;

public class SnLiquids {
    public static Liquid burheyna, sayang, messira, nabunium;
    //your stepfa
    public static void load() {
     
        burheyna = new Liquid("burheyna", Color.valueOf("f54b68")) {
            {
                viscosity = 0.7f;
                explosiveness = 0.1f;
                heatCapacity = 0.5f;
                barColor = Color.valueOf("d14960");
                effect = StatusEffects.wet;
            }};
        sayang = new SayangLiquid("sayang", Color.valueOf("fdc25c"), Color.valueOf("CA8C20")) {
            {
               viscosity = 0.7f;
               explosiveness = 0.1f;
               heatCapacity = 0.5f;
               barColor = Color.valueOf("fdc25c");
        }};
        messira = new Liquid("messira", Color.valueOf("40e2c6")) {
            {
                viscosity = 0.7f;
                explosiveness = 0.1f;
                heatCapacity = 0.5f;
                barColor = Color.valueOf("40e2c6");
            }};
            nabunium = new Liquid("nabunium", Color.valueOf("70de73")) {
                {
                viscosity = 0.7f;
                explosiveness = 0.1f;
                heatCapacity = 0.5f;
                barColor = Color.valueOf("61de65");
        }};
    }
}
