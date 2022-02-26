package sunset.type;

import mindustry.type.*;

public class PublicStatusEffect extends StatusEffect{
    public PublicStatusEffect(String name){
        super(name);
    }

    @Override
    public void trans(StatusEffect effect, TransitionHandler handler){
        super.trans(effect, handler);
    }

    @Override
    public void affinity(StatusEffect effect, TransitionHandler handler){
        super.affinity(effect, handler);
    }

    @Override
    public void opposite(StatusEffect... effect){
        super.opposite(effect);
    }

    @Override
    public void handleOpposite(StatusEffect other){
        super.handleOpposite(other);
    }
}
