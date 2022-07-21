package sunset.tools;

import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ctype.*;
import mma.*;
import mma.tools.*;
import mma.type.pixmap.*;
import sunset.*;
import sunset.core.*;
import sunset.gen.*;

import static mma.ModVars.listener;


public class SnImagePacker extends ModImagePacker{

    public static PixmapProcessor snProcessor = new SunsetPixmapProcessor(processor);

    public SnImagePacker(){
    }

    public static void main(String[] args) throws Exception{
        new SnImagePacker();
    }

    @Override
    protected void start() throws Exception{
        unitOutlines = true;
        SnVars.create();
        SnVars.settings = new SnSettings();
        listener = new ModListener();
        super.start();
    }

    @Override
    protected void load(){
        super.load();
    }

    @Override
    protected void postCreatingContent(){
        super.postCreatingContent();
        ModVars.listener.init();
    }

    @Override
    protected void preCreatingContent(){
        super.preCreatingContent();
        SnEntityMapping.init();
    }

    @Override
    protected void runGenerators(){
        new SnGenerators();
    }

    @Override
    protected void checkContent(Content content){
        super.checkContent(content);
//        Log.info("content: @",content);
        if(content instanceof MappableContent){
            SnContentRegions.loadRegions((MappableContent)content);
        }
    }

    static class SunsetPixmapProcessor implements PixmapProcessor{
        private final PixmapProcessor parentPixmapProcessor;

        SunsetPixmapProcessor(PixmapProcessor parentPixmapProcessor){
            this.parentPixmapProcessor = parentPixmapProcessor;
        }


        @Override
        public void saveScaled(Pixmap pix, String name, int size){
            parentPixmapProcessor.saveScaled(pix, name, size);
        }

        @Override
        public void save(Pixmap pixmap, String path){
            parentPixmapProcessor.save(pixmap, path);
            Fi file = Fi.get(path + ".png");
            System.out.println("try to save " + path);
            String key = file.nameWithoutExtension();
            if(cache.containsKey(key)){
                PackIndex index = cache.get(key);
                index.file = file;
                if(index.pixmap != null){
                    index.pixmap.dispose();
                    index.pixmap = new Pixmap(index.file);
                    GenRegion region = (GenRegion)index.region;
                    region.path = file;
                    region.width = index.pixmap.width;
                    region.height = index.pixmap.height;
                    region.invalid = false;
                /*
                index.region = new GenRegion(name, index.file) {

                    {
                        width = index.pixmap.width;
                        height = index.pixmap.height;
                        u2 = v2 = 1f;
                        u = v = 0f;
                    }
                };*/
                }
            }else{
                cache.put(key, new PackIndex(file));
            }

        }

        @Override
        public Pixmap get(String name){
            return parentPixmapProcessor.get(name);
        }

        @Override
        public boolean has(String name){
            return parentPixmapProcessor.has(name);
        }

        @Override
        public Pixmap get(TextureRegion region){
            return parentPixmapProcessor.get(region);
        }

        @Override
        public void replace(String name, Pixmap image){
            parentPixmapProcessor.replace(name, image);
        }

        @Override
        public void replace(TextureRegion name, Pixmap image){
            parentPixmapProcessor.replace(name, image);
        }

        @Override
        public void delete(String name){
            parentPixmapProcessor.delete(name);
        }
    }
}
