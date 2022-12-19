package com.mygdx.game.Screens;
import com.badlogic.gdx.graphics.g2d.Batch;
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
        weapons[0]=new Weapons(weaponMovementspeed,boundingBox.x+boundingBox.width*0.85f,boundingBox.y+boundingBox.height*0.45f,
                weapon_width,weapon_heigth,
                weaponsTextureRegion);
        timesinceLastshot=0;
        return weapons;
    }
    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
