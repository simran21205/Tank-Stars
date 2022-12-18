package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Tank {

    //ship characteristics
    float movementSpeed;  //world units per second

    //position & dimension
    float xPosition, yPosition; //lower-left corner
    float width, height;
    Rectangle boundedbox;



    //weapon info
    float weapon_width,weapon_heigth;
    float weaponMovementspeed;
    float timeBetweenshots;
    float timesinceLastshot=0;


    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion,weaponsTextureRegion;

    public Tank(float xCentre, float yCentre,
                float width, float height,
                float movementSpeed,int sheild,
                float weapon_width,float weapon_heigth,float weaponMovementspeed,float timeBetweenshots,
                TextureRegion shipTexture, TextureRegion shieldTexture,
                TextureRegion weaponsTexture) {
        this.movementSpeed = movementSpeed;

        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.boundedbox= new Rectangle(xPosition,yPosition,width,height);
        this.weapon_width=weapon_width;
        this.weapon_heigth=weapon_heigth;
        this.weaponMovementspeed=weaponMovementspeed;
        this.timeBetweenshots=timeBetweenshots;
        this.shipTextureRegion = shipTexture;
        this.shieldTextureRegion = shieldTexture;
        this.weaponsTextureRegion = weaponsTexture;
    }

    public void update(float deltaTime){
        boundedbox.set(xPosition,yPosition,width,height);
        timesinceLastshot += deltaTime;
    }

    public boolean canFire(){
        boolean result = timesinceLastshot - timeBetweenshots>=0;
        return result;
    }

    public abstract Weapons[] fireweapons();

    public boolean intersects(Rectangle otherRectangle){
        Rectangle thisRectangle = new Rectangle(xPosition,yPosition,width,height);
        return thisRectangle.overlaps(otherRectangle);

    }
    public void hit(Weapons weapons){}


    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
    }
}
