package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyTank extends Tank{
    public EnemyTank(float xCentre, float yCentre,
                      float width, float height,
                      float movementSpeed, int sheild,
                      float weapon_width, float weapon_heigth, float weaponMovementspeed,
                      float timeBetweenshots, TextureRegion shipTexture,
                      TextureRegion shieldTexture, TextureRegion weaponsTexture) {
        super(xCentre, yCentre, width, height, movementSpeed, sheild, weapon_width, weapon_heigth, weaponMovementspeed, timeBetweenshots, shipTexture, shieldTexture, weaponsTexture);
    }

    @Override
    public Weapons[] fireweapons() {
        Weapons[] weapons = new Weapons[1];
        weapons[0]=new Weapons(xPosition*width*0.18f,yPosition*height*0.55f,
                weapon_width,weapon_heigth,
                weaponMovementspeed,weaponsTextureRegion);
        timesinceLastshot=0;
        return weapons;
    }

}
