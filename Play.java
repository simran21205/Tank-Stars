package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.TankStars;
import com.badlogic.gdx.physics.box2d.*;

public class Play  extends Game implements Screen{

    

    private final TankStars app;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private SpriteBatch Batch;

    private Stage stage;
    private Skin skin;

    private TextButton buttonSettings, buttonWeapon ;

    private ShapeRenderer shapeRenderer;
    private Image splashImg;

    private final float TIMESTEP = 1/30f;
    private final int VELOCITYITERTIONS = 8,POSITIONITERATIONS=3;
    private float speed = 250;
    private Vector2 movement = new Vector2();
    private Sprite boxsprite;


    private tank1 tank;
    private Body box;

    private Array<Body> tempBody = new Array<Body>();






    public Play(final TankStars app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(TankStars.V_WIDTH, TankStars.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }



    @Override
    public void show() {
        world = new World(new Vector2(0, -9.18f), true);
        debugRenderer = new Box2DDebugRenderer();
        Batch=new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode){
//                    case Input.Keys.UP:
//                        movement.y= speed;
//                        break;
                    case Input.Keys.LEFT:
                        movement.x= -speed;
                        break;
//                    case Input.Keys.DOWN:
//                        movement.y= -speed;
//                        break;
                    case Input.Keys.RIGHT:
                        movement.x= speed;
                        break;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode){
                    case Input.Keys.W:
                    case Input.Keys.A:
                    case Input.Keys.DOWN:
                        movement.y=0;
                        break;
                    case Input.Keys.RIGHT:
                        movement.x=0;
                        break;
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
        });

        //add ball
        BodyDef bodyDef = new BodyDef();
//        bodyDef.type= BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(0,1);
//
//        //ball shape
//        CircleShape shape = new CircleShape();
//        shape.setRadius(.5f);

        //fixture defination
        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape=shape;
//        fixtureDef.density=2.5f;
//        fixtureDef.friction = .25f;
//        fixtureDef.restitution=.75f;

//        Body ball = world.createBody(bodyDef);
//        ball.createFixture(fixtureDef);
//        shape.dispose();

        //tank
//        FixtureDef wheelfixtureDef = new FixtureDef();
//        fixtureDef.density=5;
//        fixtureDef.friction=.4f;
//        fixtureDef.restitution=.3f;
//
//        wheelfixtureDef.density=fixtureDef.density-.5f;
//        wheelfixtureDef.friction=1;
//        wheelfixtureDef.restitution=.4f;
//

        //ground
        //body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-20,-20);

//
//        //groundshape
        ChainShape groundshape = new ChainShape();
        groundshape.createChain(new Vector2[]{new Vector2(-500,0),new Vector2(500,0)});

        fixtureDef.shape=groundshape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution=0;

        world.createBody(bodyDef).createFixture(fixtureDef);


//        Gdx.input.setInputProcessor(tank = new Tank(world,0,0,3,5));

        //box
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f,10);


        //shape
        PolygonShape boxshape = new PolygonShape();
        boxshape.setAsBox(1,1);
        fixtureDef.shape = boxshape;
        fixtureDef.friction = 0.75f;
        fixtureDef.restitution=.1f;
        fixtureDef.density=5;

        box=world.createBody(bodyDef);
        box.createFixture(fixtureDef);

        boxsprite = new Sprite(new Texture("tank.gif"));
        boxsprite.setSize(8,4);
        boxsprite.setOrigin(boxsprite.getWidth()/2,boxsprite.getHeight()/2);
        box.setUserData(boxsprite);
        boxshape.dispose();
//        groundshape.dispose();

        //box.applyAngularImpulse(500,true);







    }

    private void update(float delta) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        tank.update();
        world.step(TIMESTEP,VELOCITYITERTIONS,POSITIONITERATIONS);
        box.applyForceToCenter(movement,true);
        camera.position.set(box.getPosition().x,box.getPosition().y,0);
        camera.update();
        Batch.setProjectionMatrix(camera.combined );
        Batch.begin();
        world.getBodies(tempBody);
        for(Body body:tempBody){
            if(body.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y- sprite.getHeight()/2);
                sprite.setRotation(body.getAngle()* MathUtils.radiansToDegrees);
                sprite.draw(Batch);
            }
        }
        Batch.end();
        debugRenderer.render(world,camera.combined);


    }

    @Override
    public void create() {

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
        boxsprite.getTexture().dispose();
    }

}
