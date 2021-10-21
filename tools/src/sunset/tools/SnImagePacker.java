package sunset.tools;

import mindustry.ctype.Content;
import mindustry.ctype.MappableContent;
import mma.ModListener;
import mma.ModVars;
import mma.tools.ModImagePacker;
import sunset.*;
import sunset.gen.*;

import static mma.ModVars.listener;

public class SnImagePacker extends ModImagePacker {

    public SnImagePacker() {
    }

    @Override
    protected void start() throws Exception {
        SnVars.create();
        listener=new ModListener();
        super.start();
    }

    @Override
    protected void load() {
        super.load();
    }

    @Override
    protected void postCreatingContent() {
        super.postCreatingContent();
        ModVars.listener.init();
    }

    @Override
    protected void runGenerators() {
        new SnGenerators();
    }

    @Override
    protected void checkContent(Content content) {
        super.checkContent(content);
//        Log.info("content: @",content);
        if (content instanceof MappableContent){
            SnContentRegions.loadRegions((MappableContent) content);
        }
    }

    public static void main(String[] args) throws Exception {
        new SnImagePacker();
    }

}
