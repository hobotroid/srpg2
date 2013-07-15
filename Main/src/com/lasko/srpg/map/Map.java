package com.lasko.srpg.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.math.Rectangle;
import com.lasko.srpg.assets.Assets;
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
    public int[] layersCollision = {};

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

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
        MapObjects objects = playerObjectLayer.getObjects();
        Iterator<MapObject> objectIterator = objects.iterator();
        while(objectIterator.hasNext()) {
            MapObject object = objectIterator.next();

            //properties for this object stored in the tmx file
            MapProperties props = object.getProperties();
            String type = props.containsKey("type") ? props.get("type").toString().toLowerCase() : "object";
            int x = Integer.parseInt(props.get("x").toString()) / 24;
            int y = Integer.parseInt(props.get("y").toString()) / 24;

            //generic object
            if(type.equals("object")) {
                int gid = Integer.parseInt(props.get("gid").toString());
                TiledMapTile objectTile = getTileSets().getTile(gid);
                MapActor objectActor = new MapActor(objectTile.getTextureRegion());
                objectActor.setPosition(x, y);
                addActor(objectActor);

            //actor (character)
            } else if(type.equals("actor")) {
                String name = object.getName().toLowerCase();
                if(name.equals("player")) {
                    name = "carl";
                }
                RpgCharacter character = new RpgCharacter(name);
                MapActor actor = new MapActor(character, Assets.get().getCharacterSheet(name));
                actor.setPosition(x, y);
                addActor(actor);
                if(name.equals("carl")) {
                    player = actor;
                }
                Gdx.app.log(Srpg.LOG, "Added actor " + name + " @ " + x + "x" + y);
            }

        }

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
                if(layerName.equals("collision")) {
                    layersCollision = addElement(layersCollision, index);
                }
            }

            index++;
        }
    }

    public void getCollisionTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles)
    {
        TiledMapTileLayer layer = (TiledMapTileLayer)getLayers().get(layersCollision[0]);

        rectPool.freeAll(tiles);
        tiles.clear();
        for(int y = startY; y <= endY; y++) {
            for(int x = startX; x <= endX; x++) {
                Cell cell = layer.getCell(x, y);
                if(cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }

    public void addActor(MapActor actor)
    {
        actor.setMap(this);
        actors.add(actor);
    }

    public ArrayList<MapActor> getActors()
    {
        return actors;
    }

    public MapActor getActor(String name)
    {
        for(MapActor actor : actors) {
            if(actor.getCharacterName().equals(name)) {
                return actor;
            }
        }

        return null;
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

    public float getUnitScale()
    {
        return 1 / getTileWidth();   //we want 1 unit to equal 1 tile
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

    public void toggleLayer(int layerNum)
    {
        MapLayer layer = this.getLayers().get(layerNum);
        if(layer != null) {
            layer.setVisible(!layer.isVisible());
        }
    }

    int[] addElement(int[] org, int added) {
        int[] result = Arrays.copyOf(org, org.length +1);
        result[org.length] = added;
        return result;
    }
}
