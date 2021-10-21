package sunset.ai.weapon;

/** AI, которое ничего не делает с орудием. Может быть полезно в специфичных случаях. */
public class EmptyWeaponAI extends WeaponAI {
    @Override
    public void update() { }
}
