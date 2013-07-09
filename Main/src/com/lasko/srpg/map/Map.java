package com.lasko.srpg.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.lasko.srpg.Assets;
import com.lasko.srpg.MapActor;
import com.lasko.srpg.Srpg;
import com.lasko.srpg.models.RpgCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Map extends TiledMap
{
    private int widthInTiles;
    private int heightInTiles;
    private float widthInPixels;
    private float heightInPixels;
    private float tileWidth;
    private float tileHeight;

    private ArrayList<MapActor> actors = new ArrayList<MapActor>();
    private MapActor player;

    public int[] layersPlayer = {};
    public int[] layersAbove = {};
    public int[] layersBelow = {};

    public void init()
    {
        //store some crucial info
        TiledMapTileLayer firstLayer = (TiledMapTileLayer)getLayers().get(0);
        this.widthInTiles = firstLayer.getWidth();
        this.heightInTiles = firstLayer.getHeight();
        this.widthInPixels = firstLayer.getWidth() * firstLayer.getTileWidth();
        this.heightInPixels = firstLayer.getHeight() * firstLayer.getTileHeight();
        this.tileWidth = firstLayer.getTileWidth();
        this.tileHeight = firstLayer.getTileHeight();

        //make object layers
        MapLayer playerObjectLayer = getLayers().get("Player");
        TiledMapTileLayer playerLayer = new TiledMapTileLayer(getWidthInTiles(), getHeightInTiles(), (int)getTileWidth(), (int)getTileHeight());
        playerLayer.setName("PlayerFinal");
        MapObjects objects = playerObjectLayer.getObjects();
        Iterator<MapObject> objectIterator = objects.iterator();
        while(objectIterator.hasNext()) {
            MapObject object = objectIterator.next();

            //properties for this object stored in the tmx file
            MapProperties props = object.getProperties();
            String type = props.containsKey("type") ? props.get("type").toString().toLowerCase() : "object";
            int x = Integer.parseInt(props.get("x").toString()) / 24;
            int y = Integer.parseInt(props.get("y").toString()) / 24;
            if(type.equals("object")) {
                int gid = Integer.parseInt(props.get("gid").toString());
                TiledMapTile objectTile = getTileSets().getTile(gid);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(objectTile);
                playerLayer.setCell(x, y, cell);
            } else if(type.equals("actor")) {
                String name = "carl";
                RpgCharacter character = new RpgCharacter(name);
                MapActor actor = new MapActor(character, Assets.get().getCharacter(name));
                actor.setMapPosition(x, y);
                addActor(actor);
                player = actor;
                Gdx.app.log(Srpg.LOG, "Added actor " + name + " @ " + x + "x" + y);
            }

        }
        getLayers().add(playerLayer);

        //figure out which layers are which
        Iterator<MapLayer> layerIterator = getLayers().iterator();
        int index = 0;
        while(layerIterator.hasNext()) {
            MapLayer layer = layerIterator.next();
            String layerName = layer.getName().toLowerCase();
            Gdx.app.log(Srpg.LOG, "Layer "+index+": "+layerName);
            if(layerName.equals("playerfinal")) {
                layersPlayer = addElement(layersPlayer, index);
            } else if(layerName.equals("above")) {
                layersAbove = addElement(layersAbove, index);
            } else {
                layersBelow = addElement(layersBelow, index);
            }

            index++;
        }
    }

    public void addActor(MapActor actor)
    {
        actors.add(actor);
    }

    public ArrayList<MapActor> getActors()
    {
        return actors;
    }

    public MapActor getPlayer()
    {
        return player;
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
