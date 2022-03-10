package sunset.ui;

import arc.Core;
import mindustry.gen.Icon;
import mindustry.mod.Mods.LoadedMod;
import mindustry.ui.dialogs.BaseDialog;

public class SnModInfoDialog extends BaseDialog {
    public SnModInfoDialog(LoadedMod mod, Runnable reinstaller) {
        super("@sninfo.title");
        addCloseListener();
        setupButtons(reinstaller);
        cont.label(() -> "[white]Sunset").get().setFontScale(6);
        cont.row();
        cont.label(() -> "[gray]by Sunset Team[white]").get().setFontScale(1.5f);
        cont.row();
        cont.labelWrap("@sninfo.description");
    }

    private void setupButtons(Runnable reinstaller) {
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
