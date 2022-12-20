package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
    TextureRegion groundTexture;
    Rectangle boundingBox;


    public Ground(float width, float height, TextureRegion groundTexture){
        this.boundingBox= new Rectangle(0,0,width,height*11/7);
        this.groundTexture = groundTexture;

    }
    public void draw(Batch batch) {
        batch.draw(groundTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
    public boolean intersects(Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);

    }
}
