package com.lasko.srpg.map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.lasko.srpg.MapActor;
import com.lasko.srpg.Srpg;

import java.util.*;

public class MapRenderer implements ApplicationListener
{
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Map map;
    private Srpg game;

    private ShapeRenderer shapeRenderer;

    public MapRenderer(Map map, OrthographicCamera camera)
    {
        super();

        renderer = new OrthogonalTiledMapRenderer(map, map.getUnitScale());
        this.camera = camera;
        this.map = map;
        this.shapeRenderer = new ShapeRenderer();
        this.game = (Srpg)Gdx.app.getApplicationListener();
    }

    @Override
    public void create()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render()
    {
        camera.update();
        renderer.setView(camera);

        //1. render tiles below actors
        renderer.render(map.layersBelow);

        //2. render actors, making sure to sort based on y position so they overlap correctly
        SpriteBatch batch = renderer.getSpriteBatch();
        Comparator<MapActor> comparator = new Comparator<MapActor>() {
            public int compare(MapActor c1, MapActor c2) {
                return c2.getY() > c1.getY() ? 1 : -1;
            }
        };
        Collections.sort(map.getActors(), comparator);
        batch.begin();
        for(MapActor actor : map.getActors()) {
            actor.draw(batch);
        }
        batch.end();

        //3. render tile layers above actors
        renderer.render(map.layersAbove);

        //debugging stuff
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(255, 0, 0, 1);
        for(MapActor actor : map.getActors()) {
            Rectangle rect = actor.getCollideRect();
            //shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        shapeRenderer.setColor(0, 255, 0, .5f);
        shapeRenderer.rect((int)Srpg.mouseCoordinates.x, (int)Srpg.mouseCoordinates.y, 1, 1);
        shapeRenderer.end();
    }

    @Override
    public void pause()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resize(int width, int height)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
