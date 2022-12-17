package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.physics.box2d.*;

public class Play implements Screen {

    private final TankStars app;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private Stage stage;
    private Skin skin;

    private TextButton buttonSettings, buttonWeapon ;

    private ShapeRenderer shapeRenderer;
    private Image splashImg;

    private final float TIMESTEP = 1/30f;
    private final int VELOCITYITERTIONS = 8,POSITIONITERATIONS=3;





    public Play(final TankStars app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(TankStars.V_WIDTH, TankStars.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }



    @Override
    public void show() {
        world = new World(new Vector2(0, -9.18f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        //add ball
        BodyDef bodyDef = new BodyDef();
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,1);

        //ball shape
        CircleShape shape = new CircleShape();
        shape.setRadius(.5f);

        //fixture defination
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.density=2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution=.75f;

        Body ball = world.createBody(bodyDef);
        ball.createFixture(fixtureDef);
        shape.dispose();


        //ground
        //body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-10,-10);
//
//        //groundshape
        ChainShape groundshape = new ChainShape();
        groundshape.createChain(new Vector2[]{new Vector2(-500,0),new Vector2(500,0)});


        fixtureDef.shape=groundshape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution=0;

        world.createBody(bodyDef).createFixture(fixtureDef);


        //box


    }

    private void update(float delta) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world,camera.combined);

        world.step(TIMESTEP,VELOCITYITERTIONS,POSITIONITERATIONS);

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

}
