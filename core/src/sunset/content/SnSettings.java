package sunset.content;

import arc.Core;
import arc.Events;
import arc.scene.Group;
import arc.scene.ui.layout.Table;
import mindustry.game.EventType;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

import static arc.Core.bundle;
import static arc.Core.settings;
import static mindustry.Vars.ui;

public class SnSettings {
    public static SettingsTable snSettings;
    public SnSettings() {
    }

    public static void init() {
        BaseDialog dialog = new BaseDialog("@setting.sn-title");
        dialog.addCloseButton();

        snSettings = new SettingsTable() {
            @Override
            public void rebuild() {
                clearChildren();

                for (Setting setting : list) {
                    setting.add(this);
                }

                button(bundle.get("settings.reset", "Reset to Defaults"), () -> {
                    for (Setting setting : list) {
                        if (setting.name == null || setting.title == null) continue;
                        settings.put(setting.name, settings.getDefault(setting.name));
                    }
                    rebuild();
                }).margin(14).width(240f).pad(6);
            }
        };
        snSettings.checkPref("sn-reloadbar", false);

        dialog.cont.center().add(snSettings);

        Events.on(EventType.ResizeEvent.class, event -> {
            if (dialog.isShown() && Core.scene.getDialog() == dialog) {
                dialog.updateScrollFocus();
            }
        });

        ui.settings.shown(() -> {
            Table settingUi = (Table)((Group)((Group)(ui.settings.getChildren().get(1))).getChildren().get(0)).getChildren().get(0); //This looks so stupid lol
            settingUi.row();
            settingUi.button("@setting.sn-title", Styles.cleart, dialog::show);
        });
    }
}
