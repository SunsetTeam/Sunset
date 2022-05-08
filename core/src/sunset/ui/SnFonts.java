package sunset.ui;

import arc.*;
import arc.Graphics.Cursor.*;
import arc.assets.*;
import arc.files.*;
import arc.freetype.*;
import arc.freetype.FreeTypeFontGenerator.*;
import arc.freetype.FreetypeFontLoader.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.Font.*;
import arc.graphics.g2d.PixmapPacker.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.math.geom.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.game.*;

import java.util.*;

public class SnFonts{
    /*private static final String mainFont = "fonts/font.woff";
    private static final ObjectSet<String> unscaled = ObjectSet.with("iconLarge");
    private static ObjectIntMap<String> unicodeIcons = new ObjectIntMap<>();
    private static ObjectMap<String, String> stringIcons = new ObjectMap<>();
    private static ObjectMap<String, TextureRegion> largeIcons = new ObjectMap<>();
    private static TextureRegion[] iconTable;
    private static int lastCid;

    public static Font def36;
    public static Font def72;
    public static Font outline36;
    public static Font outline72;
//    public static Font icon;
//    public static Font iconLarge;
//    public static Font tech;

    *//** Called from a static context to make the cursor appear immediately upon startup.*//*
    public static void loadSystemCursors(){
        SystemCursor.arrow.set(Core.graphics.newCursor("cursor", cursorScale()));
        SystemCursor.hand.set(Core.graphics.newCursor("hand", cursorScale()));
        SystemCursor.ibeam.set(Core.graphics.newCursor("ibeam", cursorScale()));

        Core.graphics.restoreCursor();
    }

    public static int cursorScale(){
        return 1;
    }

    public static void loadFonts(){
        largeIcons.clear();
//        FreeTypeFontParameter param = ;

        Core.assets.load("default36", Font.class, new FreeTypeFontLoaderParameter(mainFont, fontParameter(18*2))).loaded = f -> def36 = f;
        Core.assets.load("default72", Font.class, new FreeTypeFontLoaderParameter(mainFont, fontParameter(18*4))).loaded = f -> def72 = f;
        *//*Core.assets.load("icon", Font.class, new FreeTypeFontLoaderParameter("fonts/icon.ttf", new FreeTypeFontParameter(){{
            size = 30;
            incremental = true;
            characters = "\0";
        }})).loaded = f -> icon = f;
        Core.assets.load("iconLarge", Font.class, new FreeTypeFontLoaderParameter("fonts/icon.ttf", new FreeTypeFontParameter(){{
            size = 48;
            incremental = false;
            characters = "\0" + Iconc.all;
            borderWidth = 5f;
            borderColor = Color.darkGray;
        }})).loaded = f -> iconLarge = f;*//*
    }

    public static void loadContentIcons(){
        Log.info("____@____@____@____@____",def36, def72, outline36, outline72);
        Seq<Font> fonts = Seq.with(def36, def72, outline36, outline72);
        Texture uitex = Core.atlas.find("logo").texture;
//        int size = (int)(mindustry.ui.Fonts.def.getData().lineHeight / mindustry.ui.Fonts.def.getData().scaleY);

        try(Scanner scan = new Scanner(Core.files.internal("icons/icons.properties").read(512))){
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String[] split = line.split("=");
                String[] nametex = split[1].split("\\|");
                String character = split[0], texture = nametex[1];
                int ch = Integer.parseInt(character);
                TextureRegion region = Core.atlas.find(texture);

                if(region.texture != uitex){
                    continue;
                }

                unicodeIcons.put(nametex[0], ch);
                stringIcons.put(nametex[0], ((char)ch) + "");

                Glyph glyph = new Glyph();
                glyph.id = ch;
                glyph.srcX = 0;
                glyph.srcY = 0;
                glyph.u = region.u;
                glyph.v = region.v2;
                glyph.u2 = region.u2;
                glyph.v2 = region.v;
                glyph.xoffset = 0;
                glyph.kerning = null;
                glyph.fixedWidth = true;
                glyph.page = 0;
                fonts.each(f -> {
                    int size = (int)(f.getData().lineHeight / f.getData().scaleY);
                    glyph.width = size;
                    glyph.height = (int)((float)region.height / region.width * size);
                    glyph.yoffset = -size;
                    glyph.xadvance = size;
                    f.getData().setGlyph(ch, glyph);
                });
            }
        }

        iconTable = new TextureRegion[512];
        iconTable[0] = Core.atlas.find("error");
        lastCid = 1;

        Vars.content.each(c -> {
            if(c instanceof UnlockableContent u){
                TextureRegion region = Core.atlas.find(u.name + "-icon-logic");
                if(region.found()){
                    iconTable[u.iconId = lastCid++] = region;
                }
            }
        });

        for(Team team : Team.baseTeams){
            if(Core.atlas.has("team-" + team.name)){
                team.emoji = stringIcons.get(team.name, "");
            }
        }
    }

    *//** Called from a static context for use in the loading screen.*//*
    public static void loadDefaultFont(){
        int max = Gl.getInt(Gl.maxTextureSize);

        UI.packer = new PixmapPacker(max >= 4096 ? 4096 : 2048, 2048, 2, true);
        Core.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(Core.files::internal));
        Core.assets.setLoader(Font.class, null, new FreetypeFontLoader(Core.files::internal){
            ObjectSet<FreeTypeFontParameter> scaled = new ObjectSet<>();

            @Override
            public Font loadSync(AssetManager manager, String fileName, Fi file, FreeTypeFontLoaderParameter parameter){
                if(fileName.equals("outline")){
                    parameter.fontParameters.borderWidth = Scl.scl(2f);
                    parameter.fontParameters.spaceX -= parameter.fontParameters.borderWidth;
                }

                if(!scaled.contains(parameter.fontParameters) && !unscaled.contains(fileName)){
                    parameter.fontParameters.size = (int)(Scl.scl(parameter.fontParameters.size));
                    scaled.add(parameter.fontParameters);
                }

                parameter.fontParameters.magFilter = TextureFilter.linear;
                parameter.fontParameters.minFilter = TextureFilter.linear;
                parameter.fontParameters.packer = UI.packer;
                return super.loadSync(manager, fileName, file, parameter);
            }
        });

//        FreeTypeFontParameter param = ;

        Core.assets.load("outline36", Font.class, new FreeTypeFontLoaderParameter(mainFont, new FreeTypeFontParameter(){{
            borderColor = Color.darkGray;
            incremental = true;
            size = 18*2;
        }})).loaded = t -> outline36 = t;
        Core.assets.load("outline72", Font.class, new FreeTypeFontLoaderParameter(mainFont, new FreeTypeFontParameter(){{
            borderColor = Color.darkGray;
            incremental = true;
            size = 18*4;
        }})).loaded = t -> outline72 = t;

       *//* Core.assets.load("tech", Font.class, new FreeTypeFontLoaderParameter("fonts/tech.ttf", new FreeTypeFontParameter(){{
            size = 18;
        }})).loaded = f -> {
            tech = f;
            tech.getData().down *= 1.5f;
        };*//*
    }

    *//** Merges the UI and font atlas together for better performance. *//*
    public static void mergeFontAtlas(TextureAtlas atlas){
        //grab all textures from the ui page, remove all the regions assigned to it, then copy them over to UI.packer and replace the texture in this atlas.

        //grab old UI texture and regions...
        Texture texture = atlas.find("logo").texture;

        Page page = UI.packer.getPages().first();

        Seq<AtlasRegion> regions = atlas.getRegions().select(t -> t.texture == texture);
        for(AtlasRegion region : regions){
            //get new pack rect
            page.setDirty(false);
            Rect rect = UI.packer.pack(region.name, atlas.getPixmap(region), region.splits, region.pads);

            //set new texture
            region.texture = UI.packer.getPages().first().getTexture();
            //set its new position
            region.set((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
            //add old texture
            atlas.getTextures().add(region.texture);
            //clear it
            region.pixmapRegion = null;
        }

        //remove old texture, it will no longer be used
        atlas.getTextures().remove(texture);
        texture.dispose();
        atlas.disposePixmap(texture);

        page.setDirty(true);
        page.updateTexture(TextureFilter.linear, TextureFilter.linear, false);
    }

    static FreeTypeFontParameter fontParameter(int fontSize){
        return new FreeTypeFontParameter(){{
            this.size =fontSize;
            shadowColor = Color.darkGray;
            shadowOffsetY = 2;
            incremental = true;
        }};
    }*/
}
