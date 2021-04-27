package azaria;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class Azaria extends Mod{

    public Azaria(){}

    @Override
    public void loadContent(){
        new Azitems().load();
        new Azblock().load();
    }
}