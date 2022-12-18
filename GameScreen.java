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
            tank5TextureRegion, tank6TextureRegion, tank7TextureRegion;


    //timing
    private int backgroundOffset;
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //game objects
    private Tank playerShip;
    private Tank enemyShip;


    public GameScreen(final TankStars app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        background = new Texture("background1.png");
        backgroundOffset = 0;


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
//        enemyShieldTextureRegion.flip(false, true);

//        playerLaserTextureRegion= textureAtlas.findRegion("laserBlue03");
//        enemyLaserTextureRegion= textureAtlas.findRegion("laserRed03");


        //set up game objects
        playerShip = new Tank(2, 3, 10, 10,
                WORLD_HEIGHT/4,WORLD_WIDTH/2,
                tank1TextureRegion, tank2TextureRegion);
        enemyShip = new Tank(2, 1, 10, 10,
                WORLD_HEIGHT*2/4, WORLD_WIDTH/2 ,
                tank7TextureRegion, tank4TextureRegion);


        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        //scrolling background
//        renderBackground(deltaTime);
//        backgroundOffset++;
//        if (backgroundOffset % WORLD_HEIGHT == 0){
//            backgroundOffset = 0;
//        }
        batch.draw(background,0,0,WORLD_WIDTH,WORLD_HEIGHT);

        //enemy ships
        enemyShip.draw(batch);

        //player ship
        playerShip.draw(batch);

        //lasers

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
