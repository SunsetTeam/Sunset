package sunset.ui;

import arc.*;
import arc.graphics.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.mod.Mods.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mma.*;

import java.lang.reflect.*;

import static mindustry.Vars.*;
import static mma.ModVars.*;

public class SunsetInfoDialog extends BaseDialog{
    private static final Runnable reinstaller;

    static{
        try{
            Method mod = ModsDialog.class.getDeclaredMethod("githubImportMod", String.class, boolean.class);
            mod.setAccessible(true);
            reinstaller = () -> {
                try{
                    mod.invoke(modInfo.getRepo(), modInfo.isJava());
                }catch(IllegalAccessException | InvocationTargetException e){
                    throw new RuntimeException(e);
                }
            };
        }catch(NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }

    public SunsetInfoDialog(){
        super("@sninfo.title");
        addCloseListener();
        setupButtons();
        cont.image(() -> Core.atlas.find(fullName("sunset-logo"))).fill().row();
//        cont.label(() -> "[white]Sunset").with(l->l.getStyle().font=SnFonts.outline72);//.get().setFontScale(6);
//        cont.row();
//        cont.label(() -> "[gray]by Sunset Team[white]").get().setFontScale(1.5f);
        cont.row();
        cont.label(() -> "@sninfo.description").uniform();
        resized(()->{
            if (currentContent!=null){
                currentContent.hide();
                currentContent=null;
            }
        });
    }
private BaseDialog currentContent;
    private void setupButtons(){
        buttons.defaults().size(210f, 64f);
        if(!mobile){
            buttons.button("@mods.openfolder", Icon.link, () -> Core.app.openFolder(modInfo.file.absolutePath()));
        }

        if(modInfo.getRepo() != null){
            boolean showImport = !modInfo.hasSteamID();
            buttons.button("@mods.github.open", Icon.link, () -> Core.app.openURI("https://github.com/" + modInfo.getRepo()));
            if(mobile && showImport) buttons.row();
            if(showImport) buttons.button("@mods.browser.reinstall", Icon.download, reinstaller);
        }
        Seq<UnlockableContent> all = Seq.with(content.getContentMap()).<Content>flatten().select(c -> c.minfo.mod == modInfo && c instanceof UnlockableContent).as();
        if(all.any()){
            buttons.row();
            buttons.button("@mods.viewcontent", Icon.book, () -> {
                BaseDialog d = new BaseDialog(modInfo.meta.displayName());
                d.cont.pane(cs -> {
                    int i = 0;
                    for(UnlockableContent c : all){
                        cs.button(new TextureRegionDrawable(c.uiIcon), Styles.cleari, iconMed, () -> {
                            ui.content.show(c);
                        }).size(50f).with(im -> {
                            var click = im.getClickListener();
                            im.update(() -> im.getImage().color.lerp(!click.isOver() ? Color.lightGray : Color.white, 0.4f * Time.delta));

                        }).tooltip(c.localizedName);

                        if(++i % (int)Math.min(Core.graphics.getWidth() / Scl.scl(110), 14) == 0) cs.row();
                    }
                }).grow();
                d.addCloseButton();
                d.show();
                this.resized(d::hide);
//                currentContent = d;
            }).colspan(buttons.getColumns()).center().size(300, 50).pad(4);
        }
        buttons.row();
        buttons.button("@sninfo.github", Icon.github, () -> Core.app.openURI("https://github.com/SunsetTeam/Sunset"));
        buttons.button("@sninfo.youtube", Icon.link, () -> Core.app.openURI("https://www.youtube.com/channel/UCmjFTZiVIs_G2UMeOsFtMjQ"));
        buttons.button("@sninfo.vk", Icon.link, () -> Core.app.openURI("https://vk.com/fall_of_sun"));
        buttons.row();

        buttons.button("@sninfo.discord", Icon.discord, () -> Core.app.openURI("https://discord.gg/M5pGCYDsVn"));
        buttons.row();
        buttons.button("@back", Icon.left, this::hide).colspan(buttons.getColumns()).center();
    }
}
