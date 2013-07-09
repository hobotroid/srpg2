package com.lasko.srpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.lasko.srpg.map.MapRenderer;
import com.lasko.srpg.map.Map;

import java.net.URL;

public class Srpg extends Game
{
    public static final String LOG = "Debug";

    OrthographicCamera camera;
    AssetManager assetManager;
    MapRenderer mapRenderer;
    BitmapFont font;
    SpriteBatch batch;

    @Override
    public void create()
    {
        //asset manager
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("maps/plane/plane.tmx", TiledMap.class);
        assetManager.finishLoading();

        //camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, w / 24 / 2, h / 24 / 2);
        camera.update();

        //font & sprites?
        font = new BitmapFont();
        batch = new SpriteBatch();

        //load map
        Map map = new Map((TiledMap)assetManager.get("maps/plane/plane.tmx"));
        mapRenderer = new MapRenderer(map, camera);
        //camera.translate(0, map.getHeightInTiles());
        camera.position.set(map.getWidthInTiles() / 2, map.getHeightInTiles() / 2, 0);
        Gdx.app.log(Srpg.LOG, "Map size: " + map.getWidthInTiles() + "x" + map.getHeightInTiles());
    }

    @Override
    public void render()
    {
        getInput();

        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        batch.begin();
        font.draw(
            batch,
            "FPS: " + Gdx.graphics.getFramesPerSecond() +
            "    Camera: " + camera.position.x + "x" + camera.position.y,
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