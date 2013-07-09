package com.lasko.srpg.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Map
{
    private TiledMap map;

    private int widthInTiles;
    private int heightInTiles;
    private float widthInPixels;
    private float heightInPixels;
    private float tileWidth;
    private float tileHeight;

    public Map(TiledMap map)
    {
        this.map = map;

        TiledMapTileLayer firstLayer = (TiledMapTileLayer)map.getLayers().get(0);
        this.widthInTiles = firstLayer.getWidth();
        this.heightInTiles = firstLayer.getHeight();
        this.widthInPixels = firstLayer.getWidth() * firstLayer.getTileWidth();
        this.heightInPixels = firstLayer.getHeight() * firstLayer.getTileHeight();
        this.tileWidth = firstLayer.getTileWidth();
        this.tileHeight = firstLayer.getTileHeight();
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
}
