package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Weapons {
    float movementSpeed;
    //POSITION DIMENSIONS
//    float xPosition, yPosition;
//    float width,heigth;
    Rectangle boundingBox;

    //graphics
    TextureRegion textureRegion;

    public Weapons(float movementSpeed, float xCenter, float yBottom, float width, float height, TextureRegion textureRegion) {
        this.movementSpeed = movementSpeed;
        this.boundingBox= new Rectangle(xCenter - width / 2,yBottom,width,height);
        this.textureRegion = textureRegion;
    }
    public void draw(Batch batch){
        batch.draw(textureRegion,boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
//    public Rectangle getBoundingbox(){
//        return new com.badlogic.gdx.math.Rectangle(xPosition,yPosition,width,heigth);
//    }

}
