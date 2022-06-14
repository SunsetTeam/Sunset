package sunset.tools;

import mindustry.ctype.*;
import mma.*;
import mma.tools.*;
import sunset.*;
import sunset.core.*;
import sunset.gen.*;

import static mma.ModVars.listener;

public class SnImagePacker extends ModImagePacker{

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

}
