package com.lasko.srpg;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.lasko.srpg.map.Map;
import com.lasko.srpg.models.RpgCharacter;

import java.util.Iterator;

public class MapActor extends Sprite
{
    public static final float WALK_SPEED = .1f;

    RpgCharacter character;
    Map map;

    private Rectangle collideRect = new Rectangle();

    public MapActor(RpgCharacter character, Texture texture)
    {
        super(texture, 0, 0, 48, texture.getHeight());
        setOrigin(0, 0);
        this.character = character;

        collideRect.set(12, 0, this.getWidth() - 24, 5);
    }

    public MapActor(TextureRegion textureRegion)
    {
        super(textureRegion);

        setOrigin(0, 0);
        collideRect.set(0, 0, this.getWidth(), 5);
    }


    public Map getMap()
    {
        return map;
    }

    public void setMap(Map map)
    {
        this.map = map;
        collideRect.set(collideRect.x*map.getUnitScale(), collideRect.y*map.getUnitScale(), collideRect.width*map.getUnitScale(), collideRect.height*map.getUnitScale());
        setScale(map.getUnitScale());
    }

    public RpgCharacter getCharacter()
    {
        return character;
    }

    public String getCharacterName()
    {
        if(character != null) {
            return character.getName();
        }

        return "";
    }

    private Array<Rectangle> tiles = new Array<Rectangle>();
    public void moveUp()
    {
        int startY, endY;
        startY = endY = (int) (getBoundingRectangle().y + collideRect.height + WALK_SPEED);
        int startX = (int) (getBoundingRectangle().x + collideRect.x);
        int endX = (int) (getBoundingRectangle().x + collideRect.x + collideRect.width);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        Rectangle r = new Rectangle(getCollideRect());
        r.setY(r.getY()+WALK_SPEED);

        for(MapActor actor : map.getActors()) {
            if(actor != this && actor.getCollideRect().overlaps(r)) {
                return;
            }
        }

        translateY(WALK_SPEED);
    }

    public void moveDown()
    {
        int startY, endY;
        startY = endY = (int) (getBoundingRectangle().y - WALK_SPEED);
        int startX = (int) (getBoundingRectangle().x + collideRect.x);
        int endX = (int) (getBoundingRectangle().x + collideRect.x + collideRect.width);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        Rectangle r = new Rectangle(getCollideRect());
        r.setY(r.getY()-WALK_SPEED);

        for(MapActor actor : map.getActors()) {
            if(actor != this && actor.getCollideRect().overlaps(r)) {
                return;
            }
        }

        translateY(-WALK_SPEED);
    }

    public void moveLeft()
    {
        int startX, endX;
        int startY = (int) (getBoundingRectangle().y);
        int endY = (int) (getBoundingRectangle().y + collideRect.height);
        startX = endX = (int) (getBoundingRectangle().x + collideRect.x - WALK_SPEED);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        Rectangle r = new Rectangle(getCollideRect());
        r.setX(r.getX()-WALK_SPEED);

        for(MapActor actor : map.getActors()) {
            if(actor != this && actor.getCollideRect().overlaps(r)) {
                return;
            }
        }

        translateX(-WALK_SPEED);
    }

    public void moveRight()
    {
        int startX, endX;
        int startY = (int) (getBoundingRectangle().y);
        int endY = (int) (getBoundingRectangle().y + collideRect.height);
        startX = endX = (int) (getBoundingRectangle().x + collideRect.x + collideRect.width + WALK_SPEED);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        Rectangle r = new Rectangle(getCollideRect());
        r.setX(r.getX()+WALK_SPEED);

        for(MapActor actor : map.getActors()) {
            if(actor != this && actor.getCollideRect().overlaps(r)) {
                return;
            }
        }

        translateX(WALK_SPEED);
    }

    public Rectangle getCollideRect()
    {
        return new Rectangle(getBoundingRectangle().x + collideRect.x, getBoundingRectangle().y + collideRect.y, collideRect.width, collideRect.height);
    }
}
