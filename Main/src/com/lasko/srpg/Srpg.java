package com.lasko.srpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.lasko.srpg.input.KeyboardInput;
import com.lasko.srpg.map.MapRenderer;
import com.lasko.srpg.map.Map;

public class Srpg extends Game
{
    public static final String LOG = "Debug";

    public static Vector3 mouseCoordinates = new Vector3();

    OrthographicCamera camera;
    Assets assetManager;
    MapRenderer mapRenderer;
    BitmapFont font;
    SpriteBatch batch;
    MapActor player;
    Map currentMap;

    @Override
    public void create()
    {
        //asset manager
        assetManager = new Assets();

        //camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, w / 24 / 2, h / 24 / 2); // how many units (tiles) on the screen at any given timex
        camera.update();

        //font & sprites?
        font = new BitmapFont();
        batch = new SpriteBatch();

        //load map
        //Map map = new Map((TiledMap)assetManager.get("maps/plane/plane.tmx"));
        currentMap = assetManager.getMap("maps/plane/plane.tmx");
        player = currentMap.getPlayer();
        mapRenderer = new MapRenderer(currentMap, camera);
        camera.position.set(currentMap.getWidthInTiles() / 2, currentMap.getHeightInTiles() / 2, 0);
        Gdx.app.log(Srpg.LOG, "Map size: " + currentMap.getWidthInTiles() + "x" + currentMap.getHeightInTiles());

        //input
        Gdx.input.setInputProcessor(new KeyboardInput());
    }

    @Override
    public void render()
    {
        getInput();

        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();
        camera.position.set(player.getX(), player.getY(), 0);

        batch.begin();
        font.draw(
            batch,
            "FPS: " + Gdx.graphics.getFramesPerSecond() +
            "    Camera: " + camera.position.x + "x" + camera.position.y +
            "    Player: " + player.getX() + "x" + player.getY() +
            "    Mouse:  " + mouseCoordinates.x + "x" + mouseCoordinates.y,
            10, 20
        );
        batch.end();
    }

    private void getInput()
    {
        float scrollSpeed = .1f;

        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            camera.position.x += scrollSpeed;
        }

        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            camera.position.x -= scrollSpeed;
        }

        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            camera.position.y -= scrollSpeed;
        }

        if(Gdx.input.isKeyPressed(Keys.UP)) {
            camera.position.y += scrollSpeed;
        }

        if(Gdx.input.isKeyPressed(Keys.W)) {
            player.moveUp();
        }

        if(Gdx.input.isKeyPressed(Keys.S)) {
            player.moveDown();
        }

        if(Gdx.input.isKeyPressed(Keys.A)) {
            player.moveLeft();
        }

        if(Gdx.input.isKeyPressed(Keys.D)) {
            player.moveRight();
        }

        if(Gdx.input.isKeyPressed(Keys.I)) {
            currentMap.getActor("citizen").moveUp();
        }

        if(Gdx.input.isKeyPressed(Keys.K)) {
            currentMap.getActor("citizen").moveDown();
        }

        if(Gdx.input.isKeyPressed(Keys.J)) {
            currentMap.getActor("citizen").moveLeft();
        }

        if(Gdx.input.isKeyPressed(Keys.L)) {
            currentMap.getActor("citizen").moveRight();
        }
    }

    public OrthographicCamera getCamera()
    {
        return camera;
    }

    public Map getCurrentMap()
    {
        return currentMap;
    }

    @Override
    public void dispose()
    {
        // dispose of all the native resources
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }
}