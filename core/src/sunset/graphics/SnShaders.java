package sunset.graphics;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.geom.*;
import mindustry.*;
import mindustry.graphics.*;
import sunset.*;

public class SnShaders{
    public static RadarCircleLineShader radarCircleLine;

    static{
        Shaders.class.getName();
    }

    public static void init(){
        radarCircleLine=new RadarCircleLineShader();
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

    public static class RadarCircleLineShader extends ModLoadShader{
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

    public static class ModLoadShader extends Shader{
        public ModLoadShader(String fragment, String vertex){
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
}
