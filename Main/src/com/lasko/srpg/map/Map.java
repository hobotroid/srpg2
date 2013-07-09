package com.lasko.srpg.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Arrays;
import java.util.Iterator;

public class Map
{
    private TiledMap map;

    private int widthInTiles;
    private int heightInTiles;
    private float widthInPixels;
    private float heightInPixels;
    private float tileWidth;
    private float tileHeight;

    public int[] layersPlayer = {};
    public int[] layersAbove = {};
    public int[] layersBelow = {};

    public Map(TiledMap map)
    {
        this.map = map;

        //store some crucial info
        TiledMapTileLayer firstLayer = (TiledMapTileLayer)map.getLayers().get(0);
        this.widthInTiles = firstLayer.getWidth();
        this.heightInTiles = firstLayer.getHeight();
        this.widthInPixels = firstLayer.getWidth() * firstLayer.getTileWidth();
        this.heightInPixels = firstLayer.getHeight() * firstLayer.getTileHeight();
        this.tileWidth = firstLayer.getTileWidth();
        this.tileHeight = firstLayer.getTileHeight();

        //figure out which layers are which
        Iterator<MapLayer> layerIterator = map.getLayers().iterator();
        int index = 0;
        while(layerIterator.hasNext()) {
            MapLayer layer = layerIterator.next();
            String layerName = layer.getName().toLowerCase();
            System.out.println(layerName);
            if(layerName.equals("player")) {
                layersPlayer = addElement(layersPlayer, index);
            } else if(layerName.equals("above")) {
                layersAbove = addElement(layersAbove, index);
            } else {
                layersBelow = addElement(layersBelow, index);
            }

            index++;
        }
    }

    public TiledMap getMap()
    {
        return this.map;
    }

    public int getWidthInTiles()
    {
        return widthInTiles;
    }

    public int getHeightInTiles()
    {
        return heightInTiles;
    }

    public float getWidthInPixels()
    {
        return widthInPixels;
    }

    public float getHeightInPixels()
    {
        return heightInPixels;
    }

    public float getTileWidth()
    {
        return tileWidth;
    }

    public float getTileHeight()
    {
        return tileHeight;
    }

    public float getTileWidthLarge()
    {
        return 48f;
    }

    int[] addElement(int[] org, int added) {
        int[] result = Arrays.copyOf(org, org.length +1);
        result[org.length] = added;
        return result;
    }
}
