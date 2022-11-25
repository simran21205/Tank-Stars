package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.*;
public class LoadingScreen implements Screen {

    private final TankStars app;

    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(final TankStars app) {
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();

//        backgroungTexture = new TextureRegion(new Texture("tankstars.jpg"),0,0,2048,563);
    }

    private void queueAssets() {
        app.assets.load("please.png", Texture.class);
        app.assets.load("player1choose.png", Texture.class);
        app.assets.load("player2choose.png", Texture.class);
        app.assets.load("gamescreen.png", Texture.class);
        app.assets.load("Mainmenu.jpeg", Texture.class);
        app.assets.load("tankstars.png", Texture.class);
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }

    @Override
    public void show() {
        System.out.println("LOADING");
        this.progress = 0f;
        queueAssets();
//        shapeRenderer.setProjectionMatrix(app.camera.combined);
    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .1f);
        if (app.assets.update() && progress >= app.assets.getProgress() - .001f) {
            app.setScreen(app.mainMenuScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(150, app.camera.viewportHeight / 2 - 8, app.camera.viewportWidth , 16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(150, app.camera.viewportHeight / 2 -8, progress * (app.camera.viewportWidth ), 16);
        shapeRenderer.end();

        app.batch.begin();
        app.font.draw(app.batch, "Screen : Loading", 20, 20);
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        shapeRenderer.dispose();
    }
}