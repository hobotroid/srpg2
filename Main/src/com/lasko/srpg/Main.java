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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.lasko.utils.OrthoCamController;

import java.net.URL;

public class Main extends Game
{
    OrthographicCamera camera;
    OrthoCamController cameraController;
    AssetManager assetManager;
    OrthogonalTiledMapRenderer mapRenderer;
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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 1;
        camera.update();
        cameraController = new OrthoCamController(camera);
        Gdx.input.setInputProcessor(cameraController);

        //font & sprites?
        font = new BitmapFont();
        batch = new SpriteBatch();

        //load map
        float unitScale = 1 / 24f;
        TiledMap map = assetManager.get("maps/plane/plane.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
    }

    @Override
    public void render()
    {
        getInput();

        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }

    private void getInput()
    {
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            camera.position.x++;
        }

        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            camera.position.x--;
        }

        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            camera.position.y--;
        }

        if(Gdx.input.isKeyPressed(Keys.UP)) {
            camera.position.y++;
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