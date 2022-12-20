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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.graphics.g2d.*;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

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
    private Tank turn;

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
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(5, 10);

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
        playerShip = new PlayerTank(WORLD_HEIGHT/15,WORLD_WIDTH/2,
                5, 8,
                10, 10,
                2f,1f,40,2f,
                tank1TextureRegion, tank1TextureRegion,playerWeaponTextureRegion);
        enemyShip = new EnemyTank(WORLD_HEIGHT*2/4,WORLD_WIDTH/2,
                5, 8,
                10, 10,
                2f,1f,40,2f,
                tank7TextureRegion, tank7TextureRegion,enemyWeaponTextureRegion);
        playerweapons = new LinkedList<>();
        enemyweapons=new LinkedList<>();
        turn = playerShip;


        batch = new SpriteBatch();
        prepareHUD();
    }

    private void detectInput(float deltaTime){
        float leftlimit, rightlimit, uplimit, downlimit;

        if(playerShip.isturn) {
            leftlimit = -playerShip.boundingBox.x;
            downlimit =-playerShip.boundingBox.y;
            rightlimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
            uplimit = WORLD_HEIGHT - playerShip.boundingBox.y - playerShip.boundingBox.height;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightlimit > 0) {
                playerShip.translate(Math.min(playerShip.movementSpeed * deltaTime, rightlimit), 0f);
            }
//
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftlimit < 0) {
                playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftlimit), 0f);
            }
        }
        if(enemyShip.isturn) {
            leftlimit = -enemyShip.boundingBox.x;
            downlimit =-enemyShip.boundingBox.y;
            rightlimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
            uplimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightlimit > 0) {
                enemyShip.translate(Math.min(enemyShip.movementSpeed * deltaTime, rightlimit), 0f);
            }
//
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftlimit < 0) {
                enemyShip.translate(Math.max(-enemyShip.movementSpeed * deltaTime, leftlimit), 0f);
            }
        }
//

//        if (Gdx.input.isTouched()){
//            float xTouchPixels = Gdx.input.getX();
//            float yTouchPixels = Gdx.input.getY();
//            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
//            touchPoint = viewport.unproject(touchPoint);
//
//            Vector2 playerShipCenter = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width/2,
//                    playerShip.boundingBox.y + playerShip.boundingBox.height/2);
//
//            float touchDistance = touchPoint.dst(playerShipCenter);
//
//            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD){
//                float xTouchDifference = touchPoint.x - playerShipCenter.x;
//                float yTouchDifference = touchPoint.y - playerShipCenter.y;
//
//                float xMove = xTouchDifference / touchDistance + playerShip.movementSpeed*deltaTime;
//                float yMove = yTouchDifference / touchDistance + playerShip.movementSpeed*deltaTime;
//
//                if (xMove>0) xMove = Math.min(xMove, rightlimit);
//                else xMove = Math.max(xMove, leftlimit);
//
//                if (yMove>0) yMove = Math.min(yMove, uplimit);
//                else yMove = Math.max(yMove, downlimit);
//
//                playerShip.translate(xMove,yMove);
//            }
//            Vector2 playerShipCenter = new Vector2(playerweapons.get(0).boundingBox.x + playerweapons.get(0).boundingBox.width/2,
//                    playerweapons.get(0).boundingBox.y + playerweapons.get(0).boundingBox.height/2);
//
//            float touchDistance = touchPoint.dst(playerShipCenter);
//
//            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD){
//                float xTouchDifference = touchPoint.x - playerShipCenter.x;
//                float yTouchDifference = touchPoint.y - playerShipCenter.y;
//
//                float xMove = xTouchDifference / touchDistance + playerweapons.get(0).movementSpeed*deltaTime;
//                float yMove = yTouchDifference / touchDistance + playerweapons.get(0).movementSpeed*deltaTime;
//
//                if (xMove>0) xMove = Math.min(xMove, rightlimit);
//                else xMove = Math.max(xMove, leftlimit);
//
//                if (yMove>0) yMove = Math.min(yMove, uplimit);
//                else yMove = Math.max(yMove, downlimit);
//
//                playerweapons.get(0).translate(xMove,yMove);
//            }
//        }
    }
    private void prepareHUD() {
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

    private void check(Tank playerShip,Tank enemyShip){
        if(playerShip.hasFired){
            playerShip.isturn=false;
            enemyShip.isturn=true;
        }
        else if (enemyShip.hasFired){
            enemyShip.isturn=false;
            playerShip.isturn=true;
        }

    }

    private void updateWeapon(float deltaTime) {
        if (isFired) {
            if (playerShip.isturn) {
                Weapons[] weapons = playerShip.fireweapons();
                playerweapons.add(weapons[0]);
                playerShip.hasFired = true;
                playerweapons.get(0).draw(batch);
//                playerweapons.get(0).boundingBox.x += playerweapons.get(0).movementSpeed * deltaTime;
                float delta = Gdx.graphics.getDeltaTime();
                initialVelocity.x = (initialVelocity.x * 0.7f) + gravity.x * 4 * delta * deltTime;
                initialVelocity.y = (initialVelocity.y * 0.5f) + gravity.y * 4 * delta * deltTime;
                playerweapons.get(0).boundingBox.setPosition(playerweapons.get(0).boundingBox.getX() + initialVelocity.x * delta * deltTime, playerweapons.get(0).boundingBox.getY() + initialVelocity.y * delta * deltTime);
//                playerShip.isturn = false;
//                enemyShip.isturn = true;
//                    isFired = false;
                return;
            }
        }
        if (isFired) {
            if (enemyShip.isturn) {
                Weapons[] weapons = enemyShip.fireweapons();
                enemyweapons.add(weapons[0]);
                enemyShip.hasFired = true;
                enemyweapons.get(0).draw(batch);
//                enemyweapons.get(0).boundingBox.x -= enemyweapons.get(0).movementSpeed * deltaTime;
                    float delta = Gdx.graphics.getDeltaTime();
                    initialVelocity.x = -(initialVelocity.x * 0.7f) - gravity.x * 4 * delta * deltTime;
                    initialVelocity.y = (initialVelocity.y * 0.5f) + gravity.y * 4 * delta * deltTime;
                    enemyweapons.get(0).boundingBox.setPosition(enemyweapons.get(0).boundingBox.getX() + initialVelocity.x * delta * deltTime, enemyweapons.get(0).boundingBox.getY() + initialVelocity.y * delta * deltTime);
//                    enemyShip.isturn = false;
//                    playerShip.isturn = true;
//                    isFired = false;
          }
        }
    }

//    }



    private void renderLasers(float deltaTime){
//        if(playerShip.canFire()){
//            Weapons[] weapons = playerShip.fireweapons();
//            for (Weapons weapons1:weapons){
//                playerweapons.add(weapons1);
//            }
//        }
//        if(enemyShip.canFire()){
//            Weapons[] weapons = enemyShip.fireweapons();
//            for (Weapons weapons1:weapons){
//                enemyweapons.add(weapons1);
//            }
//        }
//
//        ListIterator<Weapons> iterator = playerweapons.listIterator();
//        while (iterator.hasNext()){
//            Weapons weapons = iterator.next();
//            weapons.draw(batch);
//            weapons.boundingBox.x += weapons.movementSpeed*deltaTime;
//
////            float delta=Gdx.graphics.getDeltaTime();
////            initialVelocity.x=initialVelocity.x+gravity.x*delta*deltaTime;
////            initialVelocity.y=initialVelocity.y+gravity.y*delta*deltaTime;
//
////            weapons.boundingBox.setPosition(weapons.boundingBox.getX()+initialVelocity.x * delta * deltaTime,weapons.boundingBox.getY()+initialVelocity.y * delta * deltaTime);
//
//            if (weapons.boundingBox.x>WORLD_WIDTH){
//                iterator.remove();
//            }
//
//        }
//        iterator = enemyweapons.listIterator();
//        while (iterator.hasNext()){
//            Weapons weapons = iterator.next();
//            weapons.draw(batch);
//            weapons.boundingBox.x -= weapons.movementSpeed*deltaTime;
//
//            if (weapons.boundingBox.x>WORLD_WIDTH){
//                iterator.remove();
//            }
//        }
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
//        renderLasers(deltaTime);
        check(playerShip,enemyShip);
        updateWeapon(deltaTime);
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


