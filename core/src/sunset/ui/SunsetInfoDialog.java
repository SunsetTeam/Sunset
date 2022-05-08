package sunset.ui;

import arc.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import mma.*;

import java.lang.reflect.*;

public class SunsetInfoDialog extends BaseDialog{
    private static final Runnable reinstaller;

    static{
        try{
            Method mod = ModsDialog.class.getDeclaredMethod("githubImportMod", String.class, boolean.class);
            mod.setAccessible(true);
            reinstaller = () -> {
                try{
                    mod.invoke(ModVars.modInfo.getRepo(), ModVars.modInfo.isJava());
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
        cont.image(() -> Core.atlas.find(ModVars.fullName("sunset-logo"))).fill().row();
//        cont.label(() -> "[white]Sunset").with(l->l.getStyle().font=SnFonts.outline72);//.get().setFontScale(6);
//        cont.row();
//        cont.label(() -> "[gray]by Sunset Team[white]").get().setFontScale(1.5f);
        cont.row();
        cont.label(() -> "@sninfo.description").uniform();
    }

    private void setupButtons(){
        buttons.defaults().size(210f, 64f);

        buttons.button("@sninfo.github", Icon.github, () -> Core.app.openURI("https://github.com/SunsetTeam/Sunset"));
        buttons.button("@sninfo.youtube", Icon.link, () -> Core.app.openURI("https://www.youtube.com/channel/UCmjFTZiVIs_G2UMeOsFtMjQ"));
        buttons.button("@sninfo.vk", Icon.link, () -> Core.app.openURI("https://vk.com/fall_of_sun"));
        buttons.row();

        buttons.button("@back", Icon.left, this::hide);
        buttons.button("@sninfo.discord", Icon.discord, () -> Core.app.openURI("https://discord.gg/M5pGCYDsVn"));
        buttons.button("@mods.browser.reinstall", Icon.download, reinstaller);
    }
}
