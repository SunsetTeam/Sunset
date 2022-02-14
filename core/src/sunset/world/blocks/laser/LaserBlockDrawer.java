package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import sunset.world.blocks.laser.LaserBlock.LaserBlockBuild;

/** Drawer class for LaserBlockBuild
 * My hall of shame
 * todo rewrite somewhen due to optimization and code quality */
public class LaserBlockDrawer {
    //indicators for drawing regions
    //true - draw lens
    //false - draw edge
    public boolean top = false,
                    left = false,
                    right = false,
                    down = false;

    public TextureRegion baseRegion, topRegion, edgeDarkRegion, edgeLightRegion, lensRegion;

    LaserBlockBuild self;

    public LaserBlockDrawer(LaserBlockBuild self){
        this.self = self;
    }

    public void initRegions(TextureRegion nodeBase, TextureRegion nodeTop, TextureRegion nodeEdgeDark, TextureRegion nodeEdgeLight, TextureRegion nodeLens){
        baseRegion = nodeBase;
        topRegion = nodeTop;
        edgeDarkRegion = nodeEdgeDark;
        edgeLightRegion = nodeEdgeLight;
        lensRegion = nodeLens;
    }

    public void draw() {
        top = false;
        left = false;
        right = false;
        down = false;
        drawBase();
        float z = Draw.z();
        Draw.z(Layer.blockOver);
        //todo rewrite 'if else if else if else if'
        float offset = self.block.offset;
        for (LaserLink l : self.laserModule.input){
            LaserBlockBuild other = l.build;
            //from right
            if (self.x < other.x - offset) {
                right = true;
            }
            //from top
            else if (self.y < other.y - offset) {
                top = true;
            }
            //from left
            else if (self.x > other.x + offset) {
                left = true;
            }
            //from down
            else if (self.y > other.y + offset) {
                down = true;
            }
        }

        float sx = 0f, sy = 0f, ex = 0f, ey = 0f;
        float startOffset, endOffset;
        LaserBlockBuild start, end;

        //indicators for drawing edges and lenses
        for (LaserLink l : self.laserModule.output) {
            LaserBlockBuild other = l.build;
            start = self;
            end = other;

            //choose smaller block, from where beam will begin drawing
            if (self.block().size > other.block().size) {
                start = other;
                end = self;
            }
            startOffset = Vars.tilesize * start.block().size / 2f + 2f;
            endOffset = Vars.tilesize * end.block().size / 2f + 2f;
            //use x, y for start
            //left
            if (start.x < end.x - offset) {
                if (start == self) {
                    right = true;
                    other.drawer.left = true;
                } else {
                    left = true;
                }
                sx = start.x + startOffset;
                sy = start.y;

                ex = end.x - endOffset;
                ey = sy;
            }
            //down
            else if (start.y < end.y - offset) {
                if (start == self)
                    top = true;
                else
                    down = true;

                sx = start.x;
                sy = start.y + startOffset;

                ex = start.x;
                ey = end.y - endOffset;
            }
            //right
            else if (start.x > end.x + offset) {
                if (start == self)
                    left = true;
                else
                    right = true;

                sx = start.x - startOffset;
                sy = start.y;

                ex = end.x + endOffset;
                ey = sy;
            }
            //top
            else if (start.y > end.y + offset) {
                if (start == self)
                    down = true;
                else
                    top = true;
                sx = start.x;
                sy = start.y - startOffset;

                ex = sx;
                ey = end.y + endOffset;
            }
            float scale = Mathf.clamp(self.laserModule.getIntensity() * 0.75f, 0f, 0.75f)  + (start.block.size - 1) / 4f + Mathf.sin(Time.time / 100f) * 0.1f;
            if (self.laserModule.out <= 0)
                scale = 0;
            Drawf.laser(self.team, Core.atlas.find("minelaser"), Core.atlas.find("minelaser-end"), sx, sy, ex, ey, scale);
            Drawf.light(null, sx, sy, ex, ey, scale * 8f, Pal.accent, 0.25f);
        }
        Draw.color(Color.white, Color.red, self.heat);
        Draw.z(Layer.blockOver - 1f);
        drawLenses(top, right, left, down);
        drawEdges(!top, !right, !left, !down);
        Draw.z(z);
        Draw.reset();
    }

    public void drawBase(){
        Draw.color(Color.white, Color.red, self.heat);
        Draw.rect(baseRegion, self.x, self.y);
        if(topRegion != null)
            Draw.rect(topRegion, self.x, self.y);
        Draw.reset();
    }

    public void drawEdges(boolean top, boolean right, boolean left, boolean down){
        if (top)
            Draw.rect(edgeLightRegion, self.x, self.y, 90);
        if(right)
            Draw.rect(edgeLightRegion, self.x, self.y);
        if(left)
            Draw.rect(edgeDarkRegion, self.x, self.y, 180);
        if(down)
            Draw.rect(edgeDarkRegion, self.x, self.y, -90);
    }

    public void drawLenses(boolean top, boolean right, boolean left, boolean down){
        if (top)
            Draw.rect(lensRegion, self.x, self.y, 90);
        if(right)
            Draw.rect(lensRegion, self.x, self.y);
        if(left)
            Draw.rect(lensRegion, self.x, self.y, 180);
        if(down)
            Draw.rect(lensRegion, self.x, self.y, -90);
    }
}
