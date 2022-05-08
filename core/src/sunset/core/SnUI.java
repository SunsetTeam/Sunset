package sunset.core;

import arc.*;
import arc.KeyBinds.*;
import arc.assets.*;
import mma.core.*;
import sunset.ui.*;

public class SnUI extends ModUI implements Loadable{
    public SnUI(){
        super(new KeyBind[0]);
        Core.assets.load(this);
    }

    @Override
    public void init(){
        super.init();
        SnPlanetSystem.setup();

        Core.app.post(() ->
        Core.app.post(() -> Core.app.post(() -> {
//            SnFonts.loadContentIcons();
        }))
        );
//        Events.on(ClientLoadEvent.class,e-);
    }

    @Override
    public void loadSync(){
    }
}
