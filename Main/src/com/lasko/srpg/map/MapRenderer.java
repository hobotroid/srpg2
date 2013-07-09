package com.lasko.srpg.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: John Windows
 * Date: 7/8/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapRenderer
{
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public MapRenderer(Map map, OrthographicCamera camera)
    {
        float unitScale = 1 / map.getTileWidth();   //we want 1 unit to equal 1 tile
        renderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        this.camera = camera;
    }

    public void render()
    {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }
}
