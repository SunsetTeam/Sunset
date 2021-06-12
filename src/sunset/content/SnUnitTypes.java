package Sunset.content; 

import arc.func.Prov;
import arc.graphics.Color;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import Sunset.ai.types.BUFFerAI;
import Sunset.entities.abilities.YellowUnitBuff;
import Sunset.gen.ModSounds;
import Sunset.gen.SpecialMechUnit;
import Sunset.graphics.ModPal;
import Sunset.type.ModUnitType;
import Snunset.type.ModWeapon;
import mindustry.content.Bullets;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.ctype.ContentList;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.AmmoTypes;
import mindustry.type.ItemStack;
import mindustry.type.Weapon;


public class ModUnitTypes implements ContentList {
    public static ModUnitType
       //flying
       comet, satellite, planet, star, galaxy, eternity,


  public ModUnitTypes(){
    UnitTypes.class.isArray();
  }
  
  @Override
  public void load(){
    //flying
        comet = new UnitType("comet"){{
            defaultController = BUFFerAI::new;
            speed = 3.1f;
            accel = 0.08f;
            drag = 0.01f;
            flying = true;
            health = 60f;
            engineOffset = 5.6f;
            rotateShoting = false;
            commandLimit = 0f;
            circleTarget = true;
            hitSize = 6.7f;
            
            abilities.add(new YellowUnitBuff(0f, 109f, 110f, 115f, 102f, 106f, 40f)), new YellowUnitBuff(0f, 109f, 110f, 115f, 102f, 106f, 40f));
          //AbilityInfo, 1th is HP, 2th is DMG, 3th is speed, 4th is armor, 5th is regeneration, 6th is attack speed, 7th os range of ability, ALL NUMBERS IS PENECERATE!!!
              }};
       }});
  }};
        
                satellite = new UnitType("sattelite"){{
                    defaultController = BUFFerAI::new;
                    speed = 2.86f;
                    accel = 0.08f;
                    drag = 0.015f;
                    flying = true;
                    health = 265f;
                    engineOffset = 7.3f;
                    rotateShoting = true;
                    commandLimit = 14f;
                    circleTarget = true;
                    hitSize = 8.79f;
                    
                    
                    abilities.add(new YellowUnitBuff(0f, 109f, 110f, 115f, 102f, 106f, 40f)), new YellowUnitBuff(0f, 109f, 110f, 115f, 102f, 106f, 40f));
                    
                    
                    
                  weapons.add(new Weapon("staelitte-wp"){{
                    mirror = false;
                    shootY = 3.4f;
                    reload = 72f;
                    rotate = false;
                    shootSound = Sounds.lasershoot;
                    
                    
                    bulet = new MissileBulletType{{
                      lifetime = 35f;
                      damage = 9f;
                      ArmorAdd = 10f;
                      ArmorAddLimit = 105f;
                      speed = 4.25f;
                      trailColor = Pal.BuffYelow;
                      trailWidth = 2.26f;
                      trailLength = 16f;
                      trailChance = -2f
                      
                      
                      abilities.add(new YellowUnitBuff(0f, 109f, 110f, 115f, 102f, 106f, 40f)), new YellowUnitBuff(0f, 109f, 110f, 115f, 102f, 106f, 40f));
                    }};
           }});
    }},
