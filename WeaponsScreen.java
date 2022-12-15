package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.TankStars;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class WeaponsScreen implements Screen {

    private final TankStars app;

    private Stage stage;
    private Skin skin;

    private TextButton buttonSettings, buttonWeapon ;
    private ShapeRenderer shapeRenderer;
    private Image splashImg;
    private TextButton.TextButtonStyle style;

    public WeaponsScreen(final TankStars app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(TankStars.V_WIDTH, TankStars.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        System.out.println("WEAPONS MENU");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        Texture splashTex = app.assets.get("weapons.png", Texture.class);
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
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        app.batch.begin();
        app.batch.end();


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
//
//        buttonNew = new TextButton("Tank1", skin, "default");
//        buttonNew.setPosition(55, 45);
//        buttonNew.setSize(185, 70);
////        buttonNew.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
//        buttonNew.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                app.setScreen(app.chooseTankScreen2);
//            }
//        });
//
//        buttonResume = new TextButton("Tank2", skin, "default");
//        buttonResume.setPosition(315, 45);
//        buttonResume.setSize(185, 70);
////        buttonResume.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
//        buttonResume.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                app.setScreen(app.chooseTankScreen2);
//            }
//        });
//
//        buttonExit = new TextButton("Tank3", skin, "default");
//        buttonExit.setPosition(571, 45);
//        buttonExit.setSize(185 , 70);
////        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
//        buttonExit.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                app.setScreen(app.chooseTankScreen2);
//            }
//        });
//
//        buttonBack = new TextButton("Back", skin, "default");
//        buttonBack.setPosition(35, 375);
//        buttonBack.setSize(60, 60);
////        buttonBack.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
//        buttonBack.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                app.setScreen(app.mainMenuScreen);
//            }
//        });
        buttonWeapon = new TextButton("W", skin, "default");
        buttonWeapon.setPosition(103, 38);
        buttonWeapon.setSize(50 , 50);
//        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonWeapon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
//
        buttonSettings = new TextButton("Settings", skin, "default");
        buttonSettings.setPosition(38, 372);
        buttonSettings.setSize(60, 60);
//        buttonBack.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.settingsScreen);
            }
        });
        stage.addActor(buttonSettings);
        stage.addActor(buttonWeapon);
//        stage.addActor(buttonResume);
//        stage.addActor(buttonNew);
//        stage.addActor(buttonExit);
    }
}
