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

public class GameScreen4 implements Screen, InputProcessor {
    private final TankStars app;

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture background;
    private TextureRegion ground;
    private Texture gameOver1;
    private Texture gameOver2;
    private boolean paused;
    private Texture pause;


    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units

    private TextureRegion tank1TextureRegion,tank7fTextureRegion, tank6TextureRegion, tank7TextureRegion,playerWeaponTextureRegion,enemyWeaponTextureRegion, groundTextureRegion;

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
    private Ground groundg;
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
    private boolean pHasFired = false;
    private boolean pPermission =false;
    private boolean ePermission = false;
    private boolean eHasFired = false;
    public int choice1, choice2;

    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
    TextureRegion pTankTexture, eTankTexture;

    public GameScreen4(final TankStars app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        background = new Texture("background2.png");
//        ground = new TextureRegion("ground2.png");

        Gdx.input.setInputProcessor(this);
        gravity=new Vector2(0, -Gdx.graphics.getHeight()*.05f);
        float throwVelocity=Gdx.graphics.getWidth()*.3f;
        initialVelocity=new Vector2((float)(throwVelocity*Math.sin(throwAngle * Math.PI / 180)),(float)(throwVelocity*Math.cos(throwAngle * Math.PI / 180)));

        textureAtlas = new TextureAtlas("images.atlas");
        gameOver1 = new Texture("gameover1.png");
        gameOver2 = new Texture("gameover2.png");
        pause = new Texture("paused.png");
//
//        backgroundHeight = WORLD_HEIGHT * 2;
//        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        tank1TextureRegion = textureAtlas.findRegion("tank1");
        tank6TextureRegion = textureAtlas.findRegion("tank6");
        tank7TextureRegion = textureAtlas.findRegion("tank7");
        tank7fTextureRegion = textureAtlas.findRegion("tank7f");

        playerWeaponTextureRegion = textureAtlas.findRegion("weapon1");
        enemyWeaponTextureRegion = textureAtlas.findRegion("weapon2");
        ground = textureAtlas.findRegion("ground2");
//        tank7TextureRegion.flip(true, false);
        enemyWeaponTextureRegion.flip(true, false);

        groundg = new Ground(WORLD_WIDTH,WORLD_HEIGHT/6,ground);

        playerShip = new PlayerTank(WORLD_HEIGHT/15,WORLD_WIDTH/2,
                5, 8,
                10, 10,
                2f,1f,40,2f,
                tank7TextureRegion, tank7TextureRegion,playerWeaponTextureRegion);
        enemyShip = new EnemyTank(WORLD_HEIGHT*2/4,WORLD_WIDTH/2,
                5, 8,
                10, 10,
                2f,1f,40,2f,
                tank7fTextureRegion, tank7fTextureRegion,enemyWeaponTextureRegion);
        playerweapons = new LinkedList<>();
        enemyweapons=new LinkedList<>();
        turn = playerShip;


        batch = new SpriteBatch();
        prepareHUD();
    }


    private void detectInput(float deltaTime){
        float leftlimit, rightlimit, uplimit, downlimit;
        if(turn == playerShip && turn!=enemyShip) {
            leftlimit = -playerShip.boundingBox.x;
            downlimit = -playerShip.boundingBox.y;
            rightlimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
            uplimit = WORLD_HEIGHT - playerShip.boundingBox.y - playerShip.boundingBox.height;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightlimit > 0) {
                if (playerShip.fuel != 0) {
                    playerShip.translate(Math.min(playerShip.movementSpeed * deltaTime, rightlimit), 0f);
                    playerShip.fuel -= deltaTime;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftlimit < 0) {
                if (playerShip.fuel != 0) {
                    playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftlimit), 0f);
                    playerShip.fuel -= deltaTime;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                pPermission = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.P)){
                paused=!paused;
            }
        }
        else if(turn == enemyShip && turn!=playerShip) {
            leftlimit = -enemyShip.boundingBox.x;
            downlimit = -enemyShip.boundingBox.y;
            rightlimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
            uplimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightlimit > 0) {
                if (enemyShip.fuel != 0) {
                    enemyShip.translate(Math.min(enemyShip.movementSpeed * deltaTime, rightlimit), 0f);
                    enemyShip.fuel -= deltaTime;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftlimit < 0) {
                if (enemyShip.fuel != 0) {
                    enemyShip.translate(Math.max(-enemyShip.movementSpeed * deltaTime, leftlimit), 0f);
                    enemyShip.fuel -= deltaTime;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                ePermission=true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.P)){
                paused=true;
            }
        }
    }
    private void prepareHUD() {
        FreeTypeFontGenerator fontGenerator= new FreeTypeFontGenerator(Gdx.files.internal("Blacknorthdemo-mLE25.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        font.getData().setScale(0.08f);

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
                enemyShip.health -= 50;
                pPermission=false;
                playerShip.fuel=0;
                enemyShip.fuel = 50;
                turn = enemyShip;
                iterator.remove();
            }
            if (groundg.intersects(weapons.boundingBox)){
                pPermission=false;
                playerShip.fuel=0;
                enemyShip.fuel = 50;
                turn = enemyShip;
                iterator.remove();
            }
        }
        iterator = enemyweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            if (playerShip.intersects((weapons.boundingBox))){
                ePermission=false;
                playerShip.health -= 50;
                enemyShip.fuel=0;
                playerShip.fuel = 50;
                turn = playerShip;
                iterator.remove();
            }
            if (groundg.intersects(weapons.boundingBox)){
                ePermission=false;
                enemyShip.fuel=0;
                playerShip.fuel = 50;
                turn = playerShip;
                iterator.remove();
            }
        }
    }
    private void updateWeapon(float deltaTime) {
        if (turn == playerShip && pPermission) {
            Weapons[] weapons2 = playerShip.fireweapons();
            playerweapons.add(weapons2[0]);
            ListIterator<Weapons> iterator = playerweapons.listIterator();
            playerweapons.get(0).draw(batch);
            playerweapons.get(0).boundingBox.x += playerweapons.get(0).movementSpeed * deltaTime;
            if (playerweapons.get(0).boundingBox.x > WORLD_WIDTH) {
                iterator.remove();
            }
            pHasFired = true;
            eHasFired = false;


        }
        else if (turn == enemyShip && ePermission) {
            Weapons[] weapons2 = enemyShip.fireweapons();
            enemyweapons.add(weapons2[0]);
            ListIterator<Weapons> iterator = enemyweapons.listIterator();
            Weapons weapons = iterator.next();
            weapons.draw(batch);
            weapons.boundingBox.x -= weapons.movementSpeed * deltaTime;
            if (weapons.boundingBox.x > WORLD_WIDTH) {
                iterator.remove();
            }
            pHasFired = true;
            eHasFired = false;
        }
    }
    @Override
    public void render(float deltaTime) {
        batch.begin();

        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        groundg.draw(batch);
        enemyShip.draw(batch);
        playerShip.draw(batch);

        if (playerShip.health <= 0 || enemyShip.health <= 0) {
            if(playerShip.health>0) {
                batch.draw(gameOver1, 0, 30, WORLD_WIDTH, WORLD_HEIGHT / 2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.R)){
                    app.setScreen(new ChooseTank(app));
                } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
                    app.setScreen(new MainMenuScreen(app));
                }
            }
            else {
                batch.draw(gameOver2, 0, 30, WORLD_WIDTH, WORLD_HEIGHT / 2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.R)){
                    app.setScreen(new ChooseTank(app));
                } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
                    app.setScreen(new MainMenuScreen(app));
                }
            }
        } else {
            if (paused) {
                batch.draw(pause, 0, 30, WORLD_WIDTH , WORLD_HEIGHT / 2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.A)){
                    paused=false;
                } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
                    app.setScreen(new MainMenuScreen(app));
                }
//                else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                    app.setScreen(app.mainMenuScreen);}
            } else
            {
                playerShip.update(deltaTime);
                enemyShip.update(deltaTime);
                updateWeapon(deltaTime);
                detectCollisions();
                detectInput(deltaTime);
                renderExplosions(deltaTime);
                updateAndRenderHUD();
            }
        }
        batch.end();
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

