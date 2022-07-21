package sunset.graphics;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.graphics.Shaders.*;
import sunset.*;

import static mindustry.Vars.renderer;

public class SnShaders{
    public static RadarCircleLineShader radarCircleLine;
    public static SnSurfaceShader burheyna;

    static{
        Shaders.class.getName();
    }

    public static void init(){
        radarCircleLine=new RadarCircleLineShader();
        burheyna = new SnSurfaceShader("burheyna");
//        arkycite = new SnSurfaceShader("arkycite");
//        mud = new SnSurfaceShader("mud");
//        tar = new SnSurfaceShader("tar");
//        slag = new SnSurfaceShader("slag");
//        cryofluid = new SnSurfaceShader("cryofluid");
    }

    public static Vec2 getScreenSize(){
        Vec2 screenSize = new Vec2(Core.graphics.getWidth(), Core.graphics.getHeight());
        return screenSize;
    }

    private static Vec2 vec2(float x, float y){
        return new Vec2(x, y);
    }

    private static Vec2 vec2(float x){
        return vec2(x, x);
    }

    public static Vec2 worldToScreen(Position position){
        Vec2 cameraOffset = Core.camera.position.cpy().sub(Core.camera.width / 2f, Core.camera.height / 2f);
        float displayScale = Vars.renderer.getDisplayScale();
        return new Vec2().set(position).sub(cameraOffset).scl(vec2(displayScale));
    }

    public static class RadarCircleLineShader extends SnLoadShader{
        public final Color lightColor = new Color();
        private final Vec2 centerPosition = new Vec2();
        public float lightRadius = 1f;
        public float lightDistance = 1f;

        public void setScreen( Color lightColor, Vec2 centerPosition, float lightRadius, float lightDistance){
            this.lightColor.set(lightColor);
            this.centerPosition.set(centerPosition);
            this.lightRadius = lightRadius;
            this.lightDistance = lightDistance;
            set();
        }
        public void setWorld( Color lightColor, Vec2 centerPosition, float lightRadius, float lightDistance){
            this.lightColor.set(lightColor);
            setWorldCenterPosition(centerPosition);
            this.lightRadius = lightRadius*Vars.renderer.getDisplayScale();
            this.lightDistance = lightDistance*Vars.renderer.getDisplayScale();
            set();
        }

        public RadarCircleLineShader(){
            super("radar-circle-line", "sn-default");
        }

        public void setScreenCenterPosition(Position screenCenterPosition){
            centerPosition.set(screenCenterPosition);
        }

        public void setWorldCenterPosition(Position worldCenterPosition){
            centerPosition.set(worldToScreen(worldCenterPosition));
        }

        @Override
        public void apply(){
            super.apply();
            setUniformf("u_centerPosition", centerPosition);
            setUniformf("u_lightColor", lightColor);
            setUniformf("u_lightRadius", lightRadius);
            setUniformf("u_lightDistance", lightDistance);
        }
    }

    public static class SnLoadShader extends Shader{
        public SnLoadShader(String fragment, String vertex){
            super(load("" + vertex + ".vert"), load("" + fragment + ".frag"));
        }

        public static Fi load(String path){

            Fi tree = Vars.tree.get("shaders/"+path);
            return tree.exists()?tree : SnVars.modInfo.root.child("shaders").findAll(file-> {
                return file.name().equals(path);
            }).first();
//            return Vars.tree.get(path);
        }

        public void set(){
            Draw.shader(this);
        }

        @Override
        public void apply(){
            super.apply();
            setUniformf("u_resolution", vec2(Core.graphics.getWidth(), Core.graphics.getHeight()));
        }

    }

    public static class SnSurfaceShader extends SnLoadShader{
        Texture noiseTex;

        public SnSurfaceShader(String frag){
            super(frag,"screenspace");
            loadNoise();
        }

        public SnSurfaceShader(String vertRaw, String fragRaw){
            super(vertRaw, fragRaw);
            loadNoise();
        }

        public String textureName(){
            return "noise";
        }

        public void loadNoise(){
            Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
                ((Texture)t).setFilter(Texture.TextureFilter.linear);
                ((Texture)t).setWrap(Texture.TextureWrap.repeat);
            };
        }

        @Override
        public void apply(){
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

            if(hasUniform("u_noise")){
                if(noiseTex == null){
                    noiseTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
                }

                noiseTex.bind(1);
                renderer.effectBuffer.getTexture().bind(0);

                setUniformi("u_noise", 1);
            }
        }
    }
}
