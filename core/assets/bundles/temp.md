//region air-defense
smallPlastaniumBulletFrag = new BasicBulletType(3f, 15, "bullet"){{//frag
width = 6f;
height = 10f;
shrinkY = 1f;
lifetime = 20f;
frontColor = Pal.plastanium;
backColor = Pal.plastaniumBack;
despawnEffect = Fx.none;
}};
smallPlastaniumBullet = new BasicBulletType(5f, 25) {{//yes
shrinkX = 0f;
shrinkY = 0f;
lifetime = 45f;
height = 15f;
width = 10f;
frontColor = Pal.plastanium;
backColor = Pal.plastaniumBack;

            fragBullet = smallPlastaniumBulletFrag;
            fragBullets = 4;
        }};
        smallPyratiteBullet = new BasicBulletType(5f, 15) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 13f;
            width = 9f;

            splashDamage = 10f;
            splashDamageRadius = 14f;
            makeFire = true;

            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
        }};
        smallSurgeAlloyBullet = new BasicBulletType(5f, 28) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 14f;
            width = 10f;

            splashDamage = 10f;
            splashDamageRadius = 14f;
            makeFire = true;

            lightning = 7;
            lightningLength = 6;
            lightningColor = Pal.surge;
            lightningDamage = 11f;

            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
        }};
        smallBlueMissileFrag = new BasicBulletType(3f, 10, "missile") {{//frag
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 25f;
            height = 9f;
            width = 6f;
            frontColor = SnPal.blueBullet;
            backColor = SnPal.blueBulletBack;

            splashDamage = 10f;
            splashDamageRadius = 15f;

            trailColor = SnPal.blueBullet;
            trailWidth = 1.4f;
            trailLength = 2;
            trailRotation = false;
            
            weaveScale = 5f;
            weaveMag = 3f;
        }};
        smallBlueMissile = new BasicBulletType(5f, 15, "missile") {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 15f;
            width = 9f;
            frontColor = SnPal.blueBullet;
            backColor = SnPal.blueBulletBack;

            splashDamage = 40f;
            splashDamageRadius = 30f;

            trailColor = SnPal.blueBullet;
            trailWidth = 3.2f;
            trailLength = 3;
            trailRotation = false;

            fragBullet = smallBlueMissileFrag;
            fragBullets = 6;
            fragCone = 90;

            weaveScale = 5f;
            weaveMag = 3f;
        }};

        smallForsSpine = new BasicBulletType(5f, 6) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 13f;
            width = 8f;
            frontColor = SnPal.blastBullet;
            backColor = SnPal.blastBulletBack;

            spin = 8f;

            splashDamage = 30f;
            splashDamageRadius = 25f;
        }};

        smallEnojieMissile = new MissileBulletType(3f, 10) {{//yes
            width = 7f;
            height = 10f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 60f;
            homingPower = 4f;

            splashDamageRadius = 30f;
            splashDamage = 25f;

            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
            lifetime = 60f;
            backColor = SnPal.enojieBulletBack;
            frontColor = SnPal.enojieBullet;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 4f;
            weaveMag = 2f;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 3;
        }};
        //endregion air-defense