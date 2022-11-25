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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.TankStars;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class ChooseTank implements Screen {

    private final TankStars app;

    private Stage stage;
    private Skin skin;

    private TextButton buttonNew, buttonExit, buttonResume, buttonBack ;

    private ShapeRenderer shapeRenderer;
    private Image splashImg;

    public ChooseTank(final TankStars app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(TankStars.V_WIDTH, TankStars.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        System.out.println("TANK MENU");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        Texture splashTex = app.assets.get("player1choose.png", Texture.class);
        splashImg = new Image(splashTex);
        splashImg.setOrigin(splashImg.getWidth() / 2 , splashImg.getHeight() / 2 );
        splashImg.setPosition(stage.getWidth() / 2 -400, stage.getHeight()/2 -240);
        splashImg.addAction(sequence(alpha(0), scaleTo(1f, 1f), parallel(fadeIn(0f),
                scaleTo(1f, 1f, 2f),
                moveTo(stage.getWidth() / 2 -400, stage.getHeight() / 2 -240, 2f))));
        stage.addActor(splashImg);

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
        app.font.draw(app.batch, "Player 1 choose tank : ", 210, 420);
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
//        head = new TextButton("Player 1 choose tank ", skin);
//        head.setPosition(135, 100);
//        head.setSize(150, 60);
//        head.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
//        head.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                app.setScreen(app.playScreen);
//            });

        buttonNew = new TextButton("Tank1", skin, "default");
        buttonNew.setPosition(55, 45);
        buttonNew.setSize(185, 70);
//        buttonNew.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonNew.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.chooseTankScreen2);
            }
        });

        buttonResume = new TextButton("Tank2", skin, "default");
        buttonResume.setPosition(315, 45);
        buttonResume.setSize(185, 70);
//        buttonResume.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.chooseTankScreen2);
            }
        });

        buttonExit = new TextButton("Tank3", skin, "default");
        buttonExit.setPosition(580, 45);
        buttonExit.setSize(185 , 70);
//        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.chooseTankScreen2);
            }
        });

        buttonBack = new TextButton("Back", skin, "default");
        buttonBack.setPosition(260, 50);
        buttonBack.setSize(80, 20);
        buttonBack.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mainMenuScreen);
            }
        });
        stage.addActor(buttonBack);
        stage.addActor(buttonResume);
        stage.addActor(buttonNew);
        stage.addActor(buttonExit);
    }
}
