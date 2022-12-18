package com.mygdx.game.Screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TankStars;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {
    private final TankStars app;

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture background;

    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units

    private TextureRegion tank1TextureRegion, tank2TextureRegion,
            tank3TextureRegion, tank4TextureRegion,
            tank5TextureRegion, tank6TextureRegion, tank7TextureRegion,playerWeaponTextureRegion,enemyWeaponTextureRegion;


    private int backgroundOffset;
    //timing
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //game objects
    private Tank playerShip;
    private Tank enemyShip;
    private LinkedList<Weapons> playerweapons ;
    private LinkedList<Weapons> enemyweapons;




    public GameScreen(final TankStars app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        background = new Texture("background2.png");

        //set up the texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        //setting up the background
//        backgrounds = new TextureRegion[4];
//        backgrounds[0] = new Texture("background1.png");
//        backgrounds[1] = textureAtlas.findRegion("Starscape01");
//        backgrounds[2] = textureAtlas.findRegion("Starscape02");
//        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        //initialize texture regions
        tank1TextureRegion = textureAtlas.findRegion("tank1");
        tank2TextureRegion = textureAtlas.findRegion("tank2");
        tank3TextureRegion = textureAtlas.findRegion("tank3");
        tank4TextureRegion = textureAtlas.findRegion("tank4");
        tank5TextureRegion = textureAtlas.findRegion("tank5");
        tank6TextureRegion = textureAtlas.findRegion("tank6");
        tank7TextureRegion = textureAtlas.findRegion("tank7");
        playerWeaponTextureRegion = textureAtlas.findRegion("weapon1");
        enemyWeaponTextureRegion = textureAtlas.findRegion("weapon2");
        tank7TextureRegion.flip(true, false);
        enemyWeaponTextureRegion.flip(true, false);


//        playerLaserTextureRegion= textureAtlas.findRegion("laserBlue03");
//        enemyLaserTextureRegion= textureAtlas.findRegion("laserRed03");


        //set up game objects
        playerShip = new PlayerTank(WORLD_HEIGHT*1/15,WORLD_WIDTH/2,
                2, 3,
                10, 10,
                1,0.5f,45,1f,
                tank1TextureRegion, tank2TextureRegion,playerWeaponTextureRegion);
        enemyShip = new EnemyTank(WORLD_HEIGHT*2/4,WORLD_WIDTH/2,
                2, 3,
                10, 10,
                1f,0.5f,45,1f,
                tank7TextureRegion, tank7TextureRegion,enemyWeaponTextureRegion);
        playerweapons = new LinkedList<>();
        enemyweapons=new LinkedList<>();


        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        batch.draw(background,0,0,WORLD_WIDTH,WORLD_HEIGHT);

        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        //scrolling background
//        renderBackground(deltaTime);

        //enemy ships
        enemyShip.draw(batch);

        //player ship
        playerShip.draw(batch);

        //lasers
        if(playerShip.canFire()){
            Weapons[] weapons = playerShip.fireweapons();
            for (Weapons weapons1:weapons){
                playerweapons.add(weapons1);
            }
        }

        if(enemyShip.canFire()){
            Weapons[] weapons = enemyShip.fireweapons();
            for (Weapons weapons1:weapons){
                enemyweapons.add(weapons1);
            }
        }

        ListIterator<Weapons> iterator = playerweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            weapons.draw(batch);
            weapons.xPosition += weapons.movementSpeed*deltaTime;
            if (weapons.xPosition>WORLD_WIDTH){
                iterator.remove();
            }

        }
        iterator = enemyweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            weapons.draw(batch);
            weapons.xPosition -= weapons.movementSpeed*deltaTime;
            if (weapons.yPosition>WORLD_HEIGHT){
                iterator.remove();
            }
        }




        //explosions

        batch.end();
    }

    private void renderBackground(float deltaTime) {

        //update position of background images
//        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
//        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
//        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
//        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer
//        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
//            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
//                backgroundOffsets[layer] = 0;
//            }
//            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer],
//                    WORLD_WIDTH, backgroundHeight);
//        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
