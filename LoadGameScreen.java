package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.TankStars;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LoadGameScreen implements Screen {

    private TankStars app;

    private Stage stage;
    private Skin skin;

    private TextButton buttonNew, buttonExit, buttonResume ;

    private ShapeRenderer shapeRenderer;
    private Image splashImg;
    private TextButton.TextButtonStyle style;
    public boolean flag=false;
    public int L;


    public LoadGameScreen(TankStars app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(TankStars.V_WIDTH, TankStars.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    private void queueAssets() {
        app.assets.load("please.png", Texture.class);
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }

    @Override
    public void show() {
        System.out.println("CHOOSE GAME");
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        Texture splashTex = app.assets.get("please.png", Texture.class);
        splashImg = new Image(splashTex);
        splashImg.setOrigin(splashImg.getWidth() / 2 , splashImg.getHeight() / 2 );
        splashImg.setPosition(stage.getWidth() / 2 -400, stage.getHeight()/2 -240);
        splashImg.addAction(sequence(alpha(0), scaleTo(1f, 1f), parallel(fadeIn(0f),
                scaleTo(1f, 1f, 2f),
                moveTo(stage.getWidth() / 2 -400, stage.getHeight() / 2 -240, 2f))));
        stage.addActor(splashImg);
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));
        initButtons();
        queueAssets();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
    }

    private void initButtons() {

        buttonNew = new TextButton("GAME 1",skin, "default");
        buttonNew.setPosition(275, 218);
        buttonNew.setSize(250, 50);
//        buttonNew.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonNew.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (L){
                    case 1:
                        app.setScreen(app.gameScreen1);
                        break;
                    case 2:
                        app.setScreen(app.gameScreen2);
                        break;
                    case 3:
                        app.setScreen(app.gameScreen3);
                        break;
                    case 4:
                        app.setScreen(app.gameScreen4);
                        break;
                    case 5:
                        app.setScreen(app.gameScreen5);
                        break;
                    case 6:
                        app.setScreen(app.gameScreen6);
                        break;
                    case 7:
                        app.setScreen(app.gameScreen7);
                        break;
                    case 8:
                        app.setScreen(app.gameScreen8);
                        break;
                    case 9:
                        app.setScreen(app.gameScreen9);
                        break;
                }
            }
        });

        buttonResume = new TextButton("GAME 2", skin, "default");
        buttonResume.setPosition(275, 150);
        buttonResume.setSize(250, 50);
//        buttonResume.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (L){
                    case 1:
                        app.setScreen(app.gameScreen1);
                        break;
                    case 2:
                        app.setScreen(app.gameScreen2);
                        break;
                    case 3:
                        app.setScreen(app.gameScreen3);
                        break;
                    case 4:
                        app.setScreen(app.gameScreen4);
                        break;
                    case 5:
                        app.setScreen(app.gameScreen5);
                        break;
                    case 6:
                        app.setScreen(app.gameScreen6);
                        break;
                    case 7:
                        app.setScreen(app.gameScreen7);
                        break;
                    case 8:
                        app.setScreen(app.gameScreen8);
                        break;
                    case 9:
                        app.setScreen(app.gameScreen9);
                        break;
                }
//
            }
        });

        buttonExit = new TextButton("GAME 3", skin, "default");
        buttonExit.setPosition(275, 80);
        buttonExit.setSize(250, 50);
//        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (L){
                    case 1:
                        app.setScreen(app.gameScreen1);
                        break;
                    case 2:
                        app.setScreen(app.gameScreen2);
                        break;
                    case 3:
                        app.setScreen(app.gameScreen3);
                        break;
                    case 4:
                        app.setScreen(app.gameScreen4);
                        break;
                    case 5:
                        app.setScreen(app.gameScreen5);
                        break;
                    case 6:
                        app.setScreen(app.gameScreen6);
                        break;
                    case 7:
                        app.setScreen(app.gameScreen7);
                        break;
                    case 8:
                        app.setScreen(app.gameScreen8);
                        break;
                    case 9:
                        app.setScreen(app.gameScreen9);
                        break;
                }
            }
        });

        stage.addActor(buttonResume);
        stage.addActor(buttonNew);
        stage.addActor(buttonExit);
    }
}