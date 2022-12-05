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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.TankStars;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SettingsScreen implements Screen {

    private final TankStars app;

    private Stage stage;
    private Skin skin;

    private TextButton buttonSettings, buttonSave, buttonExit, buttonResume, buttonWeapon ;

    private ShapeRenderer shapeRenderer;
    private Image splashImg;
    private TextButton.TextButtonStyle style;

    public SettingsScreen(final TankStars app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(TankStars.V_WIDTH, TankStars.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        System.out.println("SETTINGS MENU");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        Texture splashTex = app.assets.get("settings.png", Texture.class);
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
        app.font.draw(app.batch, "Player 2 choose tank : ", 210, 420);
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

        buttonSave = new TextButton("Save Game", skin, "default");
        buttonSave.setPosition(315, 278);
        buttonSave.setSize(175, 45);
//        buttonNew.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mainMenuScreen);
            }
        });

        buttonResume = new TextButton("Resume Game", skin, "default");
        buttonResume.setPosition(315, 220);
        buttonResume.setSize(175, 45);
//        buttonResume.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });

        buttonExit = new TextButton("Main Menu", skin, "default");
        buttonExit.setPosition(315, 165);
        buttonExit.setSize(175 , 45);
//        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mainMenuScreen);
            }
        });


        buttonSettings = new TextButton("Settings", skin, "default");
        buttonSettings.setPosition(38, 372);
        buttonSettings.setSize(60, 60);
//        buttonBack.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });

        buttonWeapon = new TextButton("W", skin, "default");
        buttonWeapon.setPosition(103, 38);
        buttonWeapon.setSize(50 , 50);
//        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonWeapon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.weaponsScreen);
            }
        });

        stage.addActor(buttonWeapon);
        stage.addActor(buttonSettings);
        stage.addActor(buttonSave);
        stage.addActor(buttonResume);
        stage.addActor(buttonExit);
    }
}
