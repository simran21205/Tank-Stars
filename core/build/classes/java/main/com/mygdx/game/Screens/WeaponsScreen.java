package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.*;
import com.mygdx.game.Screens.LoadingScreen;
//import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.SplashScreen;

public class TankStars extends Game {

    public static final float VERSION = .8f;
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 480;

    public OrthographicCamera camera;
    public SpriteBatch batch;

    public BitmapFont font;
//
    public AssetManager assets;
//
    public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;
    public MainMenuScreen mainMenuScreen;
    public ChooseTank chooseTankScreen;
    public ChooseTank2 chooseTankScreen2;
    public MainToChoose mainToChoose;
    public PlayScreen playScreen;
    public SettingsScreen settingsScreen;
    public WeaponsScreen weaponsScreen;

    @Override
    public void create() {
        assets = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
//        this.setScreen(new SplashScreen(this));

//        initFonts();
//
        loadingScreen = new LoadingScreen(this);
        splashScreen = new SplashScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        chooseTankScreen = new ChooseTank(this);
        chooseTankScreen2 = new ChooseTank2(this);
        mainToChoose = new MainToChoose(this);
        playScreen = new PlayScreen(this);
        settingsScreen = new SettingsScreen(this);
        weaponsScreen = new WeaponsScreen(this);
//
        this.setScreen(loadingScreen);
    }


    @Override
    public void render() {
        super.render();

//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            Gdx.app.exit();
//        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
        loadingScreen.dispose();
        splashScreen.dispose();
        mainMenuScreen.dispose();
        chooseTankScreen.dispose();
        chooseTankScreen2.dispose();
//        playScreen.dispose();
    }

    private void initFonts() {
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//        params.size = 24;
//        params.color = Color.BLACK;
//        font24 = generator.generateFont(params);
    }
}
