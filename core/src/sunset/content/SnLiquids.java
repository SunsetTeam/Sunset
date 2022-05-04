package sunset.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class SnLiquids implements ContentList {
    public static Liquid burheyna, sayang, messira, nabunium;

    @Override
    public void load() {
     
        burheyna = new Liquid("burheyna", Color.valueOf("f54b68")) {
            {
                viscosity = 0.7f;
                explosiveness = 0.1f;
                heatCapacity = 0.5f;
                barColor = Color.valueOf("d14960");
                effect = StatusEffects.wet;
            }};
        sayang = new Liquid("sayang", Color.valueOf("ffe66a")) {
            {
               viscosity = 0.7f;
               explosiveness = 0.1f;
               heatCapacity = 0.5f;
               barColor = Color.valueOf("ffffff");
        }};
        messira = new Liquid("messira", Color.valueOf("3ddcd3")) {
            {
                viscosity = 0.7f;
                explosiveness = 0.1f;
                heatCapacity = 0.5f;
                barColor = Color.valueOf("ffffff");
            }};
            nabunium = new Liquid("nabunium", Color.valueOf("70de73")) {
                {
                viscosity = 0.7f;
                explosiveness = 0.1f;
                heatCapacity = 0.5f;
                barColor = Color.valueOf("ffffff");
        }};
    }
}
