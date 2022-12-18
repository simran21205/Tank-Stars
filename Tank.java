package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tank {

    //ship characteristics
    float movementSpeed;  //world units per second

    //position & dimension
    float xPosition, yPosition; //lower-left corner
    float width, height;

    //graphics
    TextureRegion shipTexture, shieldTexture;

    public Tank(float movementSpeed, int shield,
                float width, float height,
                float xCentre, float yCentre,
                TextureRegion shipTexture, TextureRegion shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
    }

    public void draw(Batch batch) {
        batch.draw(shipTexture, xPosition, yPosition, width, height);
    }
}

