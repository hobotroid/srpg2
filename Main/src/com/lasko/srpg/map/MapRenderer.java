package com.lasko.srpg.map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.lasko.srpg.MapActor;

import java.util.Iterator;

public class MapRenderer implements ApplicationListener
{
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Map map;

    public MapRenderer(Map map, OrthographicCamera camera)
    {
        float unitScale = 1 / map.getTileWidth();   //we want 1 unit to equal 1 tile
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        this.camera = camera;
        this.map = map;
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


        Iterator actorIterator = map.getActors().iterator();
        SpriteBatch batch = renderer.getSpriteBatch();
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("PlayerFinal");






        batch.begin();
        System.out.println("------------------------------------------------------");
        for(int y = map.getHeightInTiles() - 1; y >= 0; y--) {
            for(int x = 0; x < map.getWidthInTiles(); x++) {
                TiledMapTileLayer.Cell cell = playerLayer.getCell(x, y);
                if(cell != null) {
                    if(actorIterator.hasNext()) {
                        System.out.println("actors to draw");
                        while(actorIterator.hasNext()) {
                            MapActor actor = (MapActor)actorIterator.next();
                            if(y < actor.getMapPosition().y) {
                                System.out.println("drawing actor THEN tile");
                                batch.draw(actor.getTexture(), actor.getMapPosition().x, actor.getMapPosition().y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0, 0, 0, 48, 48, false, false);
                                batch.draw(cell.getTile().getTextureRegion(), x, y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0);
                            } else {
                                System.out.println("drawing tile THEN actor");
                                batch.draw(cell.getTile().getTextureRegion(), x, y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0);
                                batch.draw(actor.getTexture(), actor.getMapPosition().x, actor.getMapPosition().y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0, 0, 0, 48, 48, false, false);
                            }
                        }
                    } else {
                        System.out.println("no actors to draw");
                        //batch.draw(cell.getTile().getTextureRegion(), x, y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0);
                    }
                }
            }
        }
        System.out.println("------------------------------------------------------");
        batch.end();
        /*batch.begin();
        while(actorIterator.hasNext()) {
            MapActor actor = (MapActor)actorIterator.next();
            TiledMapTile tile = playerLayer.getCell(23, 19).getTile();

            if(19 < actor.getMapPosition().y) {
                batch.draw(actor.getTexture(), actor.getMapPosition().x, actor.getMapPosition().y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0, 0, 0, 48, 48, false, false);
                batch.draw(tile.getTextureRegion(), 23, 19, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0);
            } else {
                batch.draw(tile.getTextureRegion(), 23, 19, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0);
                batch.draw(actor.getTexture(), actor.getMapPosition().x, actor.getMapPosition().y, 0, 0, 48, 48, renderer.getUnitScale(), renderer.getUnitScale(), 0, 0, 0, 48, 48, false, false);
            }
        }
        batch.end();*/








        //renderer.render(map.layersPlayer);

        renderer.render(map.layersAbove);
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
