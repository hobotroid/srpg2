package com.lasko.srpg;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.lasko.srpg.map.Map;

import java.util.Iterator;

public class Assets extends AssetManager
{
    static Assets instance;

    public Assets()
    {
        super();
        Assets.instance = this;
    }

    public Map getMap(String filename)
    {
        TiledMap tiledMap = get(filename);
        Map map = new Map();
        Iterator iter;

        //copy everything from the libgdx TiledMap object to our own Map object
        iter = tiledMap.getTileSets().iterator();
        while(iter.hasNext()) {
            map.getTileSets().addTileSet((TiledMapTileSet)iter.next());
        }
        iter = tiledMap.getLayers().iterator();
        while(iter.hasNext()) {
            map.getLayers().add((MapLayer)iter.next());
        }
        map.getProperties().putAll(tiledMap.getProperties());

        //need to init our Map object now that all the data is filled in
        map.init();

        //return the populated Map object
        return map;
    }

    public Texture getCharacter(String characterName)
    {
        Texture texture = get("characters/" + characterName + ".png", Texture.class);
        return texture;
    }

    public static Assets get()
    {
        return Assets.instance;
    }
}
