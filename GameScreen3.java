package com.mygdx.game.Screens;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TankStars;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class GameScreen3 extends InputAdapter implements Screen, Json.Serializable {
    private final TankStars app;

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private final Texture background;
    private Texture gameOver;

    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units

    private final TextureRegion tank1TextureRegion;
    private final TextureRegion tank6TextureRegion;
    private final TextureRegion tank7TextureRegion;
    private final TextureRegion tank1fTextureRegion;

    private Texture gameOver1;
    private Texture gameOver2;
    private boolean paused;
    private Texture pause;


    private int backgroundOffset;
    //timing
    private float[] backgroundOffsets = {0, 0, 0, 0};


    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //game objects
    private final Tank playerShip;
    private final Tank enemyShip;
    private final Ground groundg;
    private LinkedList<Weapons> playerweapons ;
    private LinkedList<Weapons> enemyweapons;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;
    Vector2 gravity;
    private double throwAngle = 45;
    float throwVelocity;
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
    private Vector3 mouse;
    private Vector2 path;
    Vector3 tp = new Vector3();
    boolean dragging;
    private double angle = 45;

    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX,
            hudRow1Y, hudRow2Y, hudRow3Y, hudRow4Y, hudSectionWidth;
    TextureRegion pTankTexture, eTankTexture;
    //    private Button setting;
    float xMove, yMove, touchDistance;





    public GameScreen3(final TankStars app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        background = new Texture("background2.png");

        Gdx.input.setInputProcessor(this);

        TextureAtlas textureAtlas = new TextureAtlas("images.atlas");

        gravity=new Vector2(0, -Gdx.graphics.getHeight()*4f);
        throwVelocity=Gdx.graphics.getWidth()*.3f;
        gameOver1 = new Texture("gameover1.png");
        gameOver2 = new Texture("gameover2.png");
        pause = new Texture("paused.png");



        tank1TextureRegion = textureAtlas.findRegion("tank1");
        tank6TextureRegion = textureAtlas.findRegion("tank6");
        tank7TextureRegion = textureAtlas.findRegion("tank7");
        TextureRegion playerWeaponTextureRegion = textureAtlas.findRegion("weapon1");
        TextureRegion enemyWeaponTextureRegion = textureAtlas.findRegion("weapon2");
        TextureRegion ground = textureAtlas.findRegion("ground2");
        TextureRegion settings = textureAtlas.findRegion("settings");
        enemyWeaponTextureRegion.flip(true, false);
        tank1fTextureRegion=textureAtlas.findRegion("tank1f");


//        gameOver = new Texture("gameOver.png");
        groundg = new Ground(WORLD_WIDTH,WORLD_HEIGHT/6, ground);
//        setting = new Button(WORLD_WIDTH/10, WORLD_HEIGHT/10, settings);

        playerShip = new PlayerTank(WORLD_HEIGHT/15,WORLD_WIDTH/2,
                5, 8,
                10, 10,
                2f,1f,40,2f,
                tank1TextureRegion, tank1TextureRegion,playerWeaponTextureRegion);
        enemyShip = new EnemyTank(WORLD_HEIGHT*2/4,WORLD_WIDTH/2,
                5, 8,
                10, 10,
                2f,1f,40,2f,
                tank1fTextureRegion, tank1fTextureRegion,enemyWeaponTextureRegion);
        playerweapons = new LinkedList<>();
        enemyweapons=new LinkedList<>();
        turn = playerShip;


        batch = new SpriteBatch();
        prepareHUD();
//        initButtons();
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
//            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//                pPermission = true;
//            }
            if (Gdx.input.isKeyPressed(Input.Keys.P)){
                paused=!paused;
            }
            if (Gdx.input.isTouched()) {
                float xTouchPixels = Gdx.input.getX();
                float yTouchPixels = Gdx.input.getY();
                Vector2 touchPoint = new Vector2(xTouchPixels,yTouchPixels);
                touchPoint = viewport.unproject(touchPoint);

                Vector2 playerShipCenter = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width/2,
                        playerShip.boundingBox.y + playerShip.boundingBox.height/2);

                touchDistance = touchPoint.dst(playerShipCenter);

                if (touchDistance > TOUCH_MOVEMENT_THRESHOLD){
                    float xTouchDifference = touchPoint.x - playerShipCenter.x;
                    float yTouchDifference = touchPoint.y - playerShipCenter.y;

                    this.angle = Math.atan(yTouchDifference/xTouchDifference);

                    xMove = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
                    yMove = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

                    if (xMove > 0 ) xMove = Math.min(xMove,rightlimit);
                    else  xMove = Math.max(xMove,leftlimit);

                    if (yMove > 0 ) yMove = Math.min(yMove,uplimit);
                    else  yMove = Math.max(yMove,downlimit);
                    initialVelocity=new Vector2((float)((xTouchDifference / touchDistance)*Math.sin(angle * Math.PI / 180)/10),(float)((yTouchDifference/touchDistance)*Math.cos(angle * Math.PI / 180)/10));
                }
                pPermission = true;
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
//            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//                ePermission = true;
//            }
            if (Gdx.input.isKeyPressed(Input.Keys.P)){
                paused=!paused;
            }
            if (Gdx.input.isTouched()){
                float xTouchPixels = Gdx.input.getX();
                float yTouchPixels = Gdx.input.getY();
                Vector2 touchPoint = new Vector2(xTouchPixels,yTouchPixels);
                touchPoint = viewport.unproject(touchPoint);

                Vector2 playerShipCenter = new Vector2(enemyShip.boundingBox.x + enemyShip.boundingBox.width/2,
                        enemyShip.boundingBox.y + enemyShip.boundingBox.height/2);

                touchDistance = touchPoint.dst(playerShipCenter);

                if (touchDistance > TOUCH_MOVEMENT_THRESHOLD){
                    float xTouchDifference = touchPoint.x - playerShipCenter.x;
                    float yTouchDifference = touchPoint.y - playerShipCenter.y;

                    this.angle = Math.atan(yTouchDifference/xTouchDifference);

                    xMove = xTouchDifference / touchDistance * enemyShip.movementSpeed * deltaTime;
                    yMove = yTouchDifference / touchDistance * enemyShip.movementSpeed * deltaTime;

                    if (xMove > 0 ) xMove = Math.min(xMove,rightlimit);
                    else  xMove = Math.max(xMove,leftlimit);

                    if (yMove > 0 ) yMove = Math.min(yMove,uplimit);
                    else  yMove = Math.max(yMove,downlimit);
                    initialVelocity=new Vector2((float)((xTouchDifference / touchDistance)*Math.sin(angle * Math.PI / 180)/10),(float)((yTouchDifference/touchDistance)*Math.cos(angle * Math.PI / 180)/10));
                }
                ePermission = true;
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
        hudRow3Y = hudRow2Y - hudVerticalMargin - font.getCapHeight();
        hudRow4Y = hudRow3Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }
    private void updateAndRenderHUD() {
        //render top row labels
        font.draw(batch, "PLAYER1", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "fuel", hudLeftX, hudRow3Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "VS", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "PLAYER2", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        font.draw(batch, "fuel", hudRightX, hudRow3Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.health), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.fuel), hudLeftX, hudRow4Y, hudSectionWidth, Align.left, false);
//        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", enemyShip.health), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", enemyShip.fuel), hudRightX, hudRow4Y, hudSectionWidth, Align.right, false);
    }

    private void renderExplosions(float deltaTime){}

    private void detectCollisions(){
        ListIterator<Weapons> iterator = playerweapons.listIterator();
        while (iterator.hasNext()){
            Weapons weapons = iterator.next();
            if (enemyShip.intersects(weapons.boundingBox)){
                //contact with enemy
                enemyShip.hit(weapons);
                enemyShip.health -= 15;
                enemyShip.boundingBox.x+=TOUCH_MOVEMENT_THRESHOLD;
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
                playerShip.health -= 15;
                playerShip.boundingBox.x-=TOUCH_MOVEMENT_THRESHOLD;
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
            Weapons w = iterator.next();
            w.draw(batch);
            w.translate(xMove, yMove);

            float delta = Gdx.graphics.getDeltaTime();
            initialVelocity.x = initialVelocity.x + gravity.x * delta * deltaTime;
            initialVelocity.y = initialVelocity.y + gravity.y * 4 * delta * deltaTime;

            w.boundingBox.setPosition(w.boundingBox.getX() + (initialVelocity.x * delta * deltaTime), w.boundingBox.getY() + (initialVelocity.y * delta * deltaTime));
            if (w.boundingBox.x > WORLD_WIDTH || w.boundingBox.x < 0) {
                iterator.remove();
            }
            pHasFired = true;
            eHasFired = false;
        }
        else if (turn == enemyShip && ePermission) {
            Weapons[] weapons2 = enemyShip.fireweapons();
            enemyweapons.add(weapons2[0]);
            enemyweapons.get(0).draw(batch);
            enemyweapons.get(0).translate(xMove,yMove);
            ListIterator<Weapons> iterator = enemyweapons.listIterator();

            float delta=Gdx.graphics.getDeltaTime();
            initialVelocity.x=-1*(initialVelocity.x+gravity.x*delta*deltaTime);
            initialVelocity.y=initialVelocity.y+gravity.y*4*delta*deltaTime;

            enemyweapons.get(0).boundingBox.setPosition(enemyweapons.get(0).boundingBox.getX()+  initialVelocity.x*delta * deltaTime,enemyweapons.get(0).boundingBox.getY()+initialVelocity.y * delta * deltaTime);
            if (enemyweapons.get(0).boundingBox.x > WORLD_WIDTH ||enemyweapons.get(0).boundingBox.x<0) {
                iterator.remove();
            }
            pHasFired = false;
            eHasFired = true;
        }
    }
    private void save1() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("out1.text");
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        out.writeObject(playerShip);
        out.close();
        fileOutputStream.close();
    }
    private void save2() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("out2.text");
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        out.writeObject(enemyShip);
        out.close();
        fileOutputStream.close();
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
            }
            else {
                batch.draw(gameOver2, 0, 30, WORLD_WIDTH, WORLD_HEIGHT / 2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.R)){
                app.setScreen(new ChooseTank(app));
            } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
                app.setScreen(new MainMenuScreen(app));
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
                else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    app.loadGameScreen.L=1;
                    app.setScreen(app.mainMenuScreen);

                }

            }
            else
            {
                playerShip.update(deltaTime);
                enemyShip.update(deltaTime);
                detectInput(deltaTime);
                updateWeapon(deltaTime);
                playerShip.update(deltaTime);
                enemyShip.update(deltaTime);
                detectCollisions();
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

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}

