package com.lasko.srpg.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderer
{
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Map map;

    public MapRenderer(Map map, OrthographicCamera camera)
    {
        float unitScale = 1 / map.getTileWidth();   //we want 1 unit to equal 1 tile
        renderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        this.camera = camera;
        this.map = map;
    }

    public void render()
    {
        camera.update();
        renderer.setView(camera);
        renderer.render(map.layersBelow);
        renderer.render(map.layersPlayer);
        renderer.render(map.layersAbove);
    }
}
