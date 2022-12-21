package com.mygdx.game.Screens;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class PlayerTankFactory {
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
      HashMap PlayerTankMap = new HashMap();
     TextureAtlas textureAtlas = new TextureAtlas("images.atlas");
     TextureRegion playerWeaponTextureRegion = textureAtlas.findRegion("weapon1");

     TextureRegion tank1TextureRegion = textureAtlas.findRegion("tank1");
     TextureRegion tank6TextureRegion = textureAtlas.findRegion("tank6");
     TextureRegion tank7TextureRegion = textureAtlas.findRegion("tank7");


    public  PlayerTank getPlayerTank(int choice) {
        PlayerTank playerTank = null;
        if (PlayerTankMap.containsKey(choice)){
             playerTank = (PlayerTank) PlayerTankMap.get(choice);

        }
        else {
            if (choice == 1) {
                playerTank = new PlayerTank(WORLD_HEIGHT / 15, WORLD_WIDTH / 2,
                        5, 8,
                        10, 10,
                        2f, 1f, 20, 10f,
                        tank1TextureRegion, tank1TextureRegion, playerWeaponTextureRegion);
                PlayerTankMap.put(choice, playerTank);
            }
            else if (choice == 2) {
                playerTank = new PlayerTank(WORLD_HEIGHT / 15, WORLD_WIDTH / 2,
                        5, 8,
                        10, 10,
                        2f, 1f, 20, 10f,
                        tank6TextureRegion, tank6TextureRegion, playerWeaponTextureRegion);
                PlayerTankMap.put(choice, playerTank);
            }
            else if (choice == 3) {
                playerTank = new PlayerTank(WORLD_HEIGHT / 15, WORLD_WIDTH / 2,
                        5, 8,
                        10, 10,
                        2f, 1f, 20, 10f,
                        tank7TextureRegion, tank7TextureRegion, playerWeaponTextureRegion);
                PlayerTankMap.put(choice, playerTank);
            }

        }
        return playerTank;
    }
}

