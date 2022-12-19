package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.graphics.g2d.*;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

public class GameScreen implements Screen, InputProcessor {
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

    private TextureRegion tank1TextureRegion, tank6TextureRegion, tank7TextureRegion,playerWeaponTextureRegion,enemyWeaponTextureRegion;


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
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;
    Vector2 gravity;
    private float throwAngle=50;
    private float deltTime=2;
    private Vector2 initialVelocity;
    boolean isFired;
    private int score = 0;

    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    public GameScreen(final TankStars app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        background = new Texture("background2.png");

        Gdx.input.setInputProcessor(this);
        gravity=new Vector2(0, -Gdx.graphics.getHeight()*.05f);
        float throwVelocity=Gdx.graphics.getWidth()*.3f;
        initialVelocity=new Vector2((float)(throwVelocity*Math.sin(throwAngle * Math.PI / 180)),(float)(throwVelocity*Math.cos(throwAngle * Math.PI / 180)));

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
//        tank2TextureRegion = textureAtlas.findRegion("tank2");
//        tank3TextureRegion = textureAtlas.findRegion("tank3");
//        tank4TextureRegion = textureAtlas.findRegion("tank4");
//        tank5TextureRegion = textureAtlas.findRegion("tank5");
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
                10, 15,
                30, 10,
                3f,2f,100,5f,
                tank1TextureRegion, tank1TextureRegion,playerWeaponTextureRegion);
        enemyShip = new EnemyTank(WORLD_HEIGHT*2/4,WORLD_WIDTH/2,
                10, 15,
                30, 10,
                3f,2f,45,1f,
                tank7TextureRegion, tank7TextureRegion,enemyWeaponTextureRegion);
        playerweapons = new LinkedList<>();
        enemyweapons=new LinkedList<>();


        batch = new SpriteBatch();
    }

    private void detectInput(float deltaTime){
        float leftlimit, rightlimit, uplimit, downlimit;
        leftlimit = -playerShip.boundingBox.x;
        downlimit =-playerShip.boundingBox.y;
        rightlimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        uplimit = WORLD_HEIGHT - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightlimit>0){
            playerShip.translate(Math.min(playerShip.movementSpeed*deltaTime,rightlimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && uplimit>0){
            playerShip.translate(0f, Math.min(playerShip.movementSpeed*deltaTime,uplimit));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftlimit<0){
            playerShip.translate(Math.min(-playerShip.movementSpeed*deltaTime,leftlimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downlimit<0){
            playerShip.translate( 0f, Math.min(-playerShip.movementSpeed*deltaTime,downlimit));
        }

        if (Gdx.input.isTouched()){
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();
            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            Vector2 playerShipCenter = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width/2,
                    playerShip.boundingBox.y + playerShip.boundingBox.height/2);

            float touchDistance = touchPoint.dst(playerShipCenter);

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD){
                float xTouchDifference = touchPoint.x - playerShipCenter.x;
                float yTouchDifference = touchPoint.y - playerShipCenter.y;

                float xMove = xTouchDifference / touchDistance + playerShip.movementSpeed*deltaTime;
                float yMove = yTouchDifference / touchDistance + playerShip.movementSpeed*deltaTime;

                if (xMove>0) xMove = Math.min(xMove, rightlimit);
                else xMove = Math.max(xMove, leftlimit);

                if (yMove>0) yMove = Math.min(yMove, uplimit);
                else yMove = Math.max(yMove, downlimit);

                playerShip.translate(xMove,yMove);
            }


        }

    }
    private  void prepareHUD() {
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator fontGenerator= new FreeTypeFontGenerator(Gdx.files.internal("Blacknorthdemo-mLE25.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(0.08f);

        //calculate hud margins, etc.
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }




    private void updateAndRenderHUD() {
        //render top row labels
        font.draw(batch, "PLAYER1", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "VS", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "PLAYER2", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
//        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.health), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }
    


    private void renderExplosions(float deltaTime){}

    private void detectCollisions(){
        ListIterator<Weapons> iterator = playerweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            if (enemyShip.intersects(weapons.boundingBox)){
                //contact with enemy
                enemyShip.hit(weapons);
                iterator.remove();
            }
        }
        iterator = enemyweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            if (playerShip.intersects((weapons.boundingBox))){
                iterator.remove();
            }
        }
    }

    private void renderLasers(float deltaTime){
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
            weapons.boundingBox.x += weapons.movementSpeed*deltaTime;

//            float delta=Gdx.graphics.getDeltaTime();
//            initialVelocity.x=initialVelocity.x+gravity.x*delta*deltaTime;
//            initialVelocity.y=initialVelocity.y+gravity.y*delta*deltaTime;

//            weapons.boundingBox.setPosition(weapons.boundingBox.getX()+initialVelocity.x * delta * deltaTime,weapons.boundingBox.getY()+initialVelocity.y * delta * deltaTime);

            if (weapons.boundingBox.x>WORLD_WIDTH){
                iterator.remove();
            }

        }
        iterator = enemyweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            weapons.draw(batch);
            weapons.boundingBox.x -= weapons.movementSpeed*deltaTime;

            if (weapons.boundingBox.x>WORLD_WIDTH){
                iterator.remove();
            }
        }
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        detectInput(deltaTime);

        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        batch.draw(background,0,0,WORLD_WIDTH,WORLD_HEIGHT);

        //scrolling background
//        renderBackground(deltaTime);

        //enemy ships
        enemyShip.draw(batch);

        //player ship
        playerShip.draw(batch);
        renderLasers(deltaTime);
        detectCollisions();
        renderExplosions(deltaTime);
        //explosions
        
        updateAndRenderHUD();

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isFired = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
