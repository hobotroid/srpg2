package com.lasko.srpg.map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.lasko.srpg.MapActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MapRenderer implements ApplicationListener
{
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Map map;

    private ShapeRenderer shapeRenderer;

    public MapRenderer(Map map, OrthographicCamera camera)
    {
        super();

        renderer = new OrthogonalTiledMapRenderer(map, map.getUnitScale());
        this.camera = camera;
        this.map = map;
        this.shapeRenderer = new ShapeRenderer();
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

        renderer.render(map.layersBelow);

        SpriteBatch batch = renderer.getSpriteBatch();
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("PlayerFinal");
        ArrayList<MapActor> actors = (ArrayList<MapActor>)map.getActors().clone();
        int indexToRemove = -1;
        MapActor actorToDraw = null;

        //manually draw actors and objects so that they overlap correctly
        batch.begin();
        for(int y = map.getHeightInTiles() - 1; y >= 0; y--) { //TODO: only draw tiles in view
            for(int i = 0; i < actors.size(); i++) {
                MapActor actor = actors.get(i);
                if((int)actor.getY() == y) {
                    actor.draw(batch);
                    indexToRemove = i;
                }
            }
            if(indexToRemove > -1) {
                actors.remove(indexToRemove);
                indexToRemove = -1;
            }

            for(int x = 0; x < map.getWidthInTiles(); x++) {
                TiledMapTileLayer.Cell cell = playerLayer.getCell(x, y);
                if(cell != null) {
                    batch.draw(cell.getTile().getTextureRegion(), x, y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0);
                }
            }
        }
        batch.end();

        renderer.render(map.layersAbove);

        //debugging stuff
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(255, 0, 0, 1);
        for(MapActor actor : map.getActors()) {
            Rectangle rect = actor.getCollideRect();
            shapeRenderer.rect(rect.x + actor.getX(), rect.y + actor.getY(), rect.width, rect.height);
        }
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
