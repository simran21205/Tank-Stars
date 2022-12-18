package com.mygdx.game.Screens;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerTank extends Tank{
    public PlayerTank(float xCentre, float yCentre,
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
        weapons[0]=new Weapons(weaponMovementspeed,xPosition*width*0.85f,yPosition*height*0.41f,
                weapon_width,weapon_heigth,
                weaponsTextureRegion);
        timesinceLastshot=0;
        return weapons;
    }
}
