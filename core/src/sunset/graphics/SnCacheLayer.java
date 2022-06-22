package sunset.graphics;

import arc.struct.*;
import mindustry.graphics.*;
import mindustry.graphics.CacheLayer.*;

public class SnCacheLayer{
    public static CacheLayer

    burheyna;


    /** Loads default cache layers. */
    public static void init(){
        addLiquids(
        burheyna = new ShaderLayer(SnShaders.burheyna)/*,
        mud = new ShaderLayer(Shaders.mud),
        tar = new ShaderLayer(Shaders.tar),
        slag = new ShaderLayer(Shaders.slag),
        arkycite = new ShaderLayer(Shaders.arkycite),
        cryofluid = new ShaderLayer(Shaders.cryofluid),
        space = new ShaderLayer(Shaders.space),
        normal = new CacheLayer(),
        walls = new CacheLayer()*/
        );
    }

    private static void addLiquids(CacheLayer... cacheLayers){
        Seq<CacheLayer> layers = Seq.with(CacheLayer.all);
        for(int i = 0; i < cacheLayers.length; i++){
            layers.insert(CacheLayer.normal.id+i,cacheLayers[i]);
        }
        CacheLayer.all=layers.toArray(CacheLayer.class);
        CacheLayer.add();
    }
}
