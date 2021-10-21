package sunset.ctype;

/** Do not rearrange, ever! */ //I am sorry Anuke
public enum ContentType{
    item,
    block,
    mech_UNUSED,
    bullet,
    liquid,
    status,
    unit,
    weather,
    effect_UNUSED,
    sector,
    loadout_UNUSED,
    typeid_UNUSED,
    error,
    planet,
    ammo,
    special;

    public static final ContentType[] all = values();
}
