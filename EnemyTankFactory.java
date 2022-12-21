package com.mygdx.game.Screens;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class EnemyTankFactory {
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    HashMap EnemyTankMap = new HashMap();
    TextureAtlas textureAtlas = new TextureAtlas("images.atlas");
    TextureRegion playerWeaponTextureRegion = textureAtlas.findRegion("weapon1");

    TextureRegion tank1TextureRegion = textureAtlas.findRegion("tank1");
    TextureRegion tank6TextureRegion = textureAtlas.findRegion("tank6");
    TextureRegion tank7TextureRegion = textureAtlas.findRegion("tank7");


    public  EnemyTank getPlayerTank(int choice) {
        EnemyTank enemyTank = null;
        if (EnemyTankMap.containsKey(choice)){
            enemyTank = (EnemyTank) EnemyTankMap.get(choice);

        }
        else {
            if (choice == 1) {
                enemyTank = new EnemyTank(WORLD_HEIGHT / 15, WORLD_WIDTH / 2,
                        5, 8,
                        10, 10,
                        2f, 1f, 20, 10f,
                        tank1TextureRegion, tank1TextureRegion, playerWeaponTextureRegion);
                EnemyTankMap.put(choice, enemyTank);
            }
            else if (choice == 2) {
                enemyTank = new EnemyTank(WORLD_HEIGHT / 15, WORLD_WIDTH / 2,
                        5, 8,
                        10, 10,
                        2f, 1f, 20, 10f,
                        tank6TextureRegion, tank6TextureRegion, playerWeaponTextureRegion);
                EnemyTankMap.put(choice, enemyTank);
            }
            else if (choice == 3) {
                enemyTank = new EnemyTank(WORLD_HEIGHT / 15, WORLD_WIDTH / 2,
                        5, 8,
                        10, 10,
                        2f, 1f, 20, 10f,
                        tank7TextureRegion, tank7TextureRegion, playerWeaponTextureRegion);
                EnemyTankMap.put(choice, enemyTank);
            }

        }
        return enemyTank;
    }
}

