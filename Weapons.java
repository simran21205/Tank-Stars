package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapons {
    float movementSpeed;
    //POSITION DIMENSIONS
    float xPosition, yPosition;
    float width,heigth;

    //graphics
    TextureRegion textureRegion;

    public Weapons(float movementSpeed, float xPosition, float yPosition, float width, float heigth, TextureRegion textureRegion) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.heigth = heigth;
        this.textureRegion = textureRegion;
    }
    public void draw(Batch batch){
        batch.draw(textureRegion,xPosition-width/2,yPosition,width,heigth);
    }

}
