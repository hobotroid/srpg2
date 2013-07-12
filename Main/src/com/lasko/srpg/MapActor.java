package com.lasko.srpg;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.lasko.srpg.map.Map;
import com.lasko.srpg.models.RpgCharacter;

public class MapActor extends Sprite
{
    public static final float WALK_SPEED = .1f;

    RpgCharacter character;
    Map map;

    private Rectangle collideRect = new Rectangle();

    // This is the rectangle pool used in collision detection
    // Good to avoid instantiation each frame
    private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    public MapActor(RpgCharacter character, Texture texture)
    {
        super(texture, 0, 0, 48, texture.getHeight());
        setOrigin(0, 0);
        this.character = character;

        collideRect.set(10, 0, this.getWidth() - 20, 10);
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

    private Array<Rectangle> tiles = new Array<Rectangle>();
    public void moveUp()
    {
        int startY, endY;
        startY = endY = (int) (getBoundingRectangle().y + collideRect.height);
        int startX = (int) (getBoundingRectangle().x + collideRect.x);
        int endX = (int) (getBoundingRectangle().x + collideRect.x + collideRect.width);
        System.out.println(startX+"x"+startY+" - "+endX+"x"+endY);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        translateY(WALK_SPEED);
    }

    public void moveDown()
    {
        int startY, endY;
        startY = endY = (int) (getBoundingRectangle().y);
        int startX = (int) (getBoundingRectangle().x + collideRect.x);
        int endX = (int) (getBoundingRectangle().x + collideRect.x + collideRect.width);
        System.out.println(startX+"x"+startY+" - "+endX+"x"+endY);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        translateY(-WALK_SPEED);
    }

    public void moveLeft()
    {
        int startX, endX;
        int startY = (int) (getBoundingRectangle().y);
        int endY = (int) (getBoundingRectangle().y + collideRect.height);
        startX = endX = (int) (getBoundingRectangle().x + collideRect.x);
        System.out.println(startX+"x"+startY+" - "+endX+"x"+endY);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        translateX(-WALK_SPEED);
    }

    public void moveRight()
    {
        int startX, endX;
        int startY = (int) (getBoundingRectangle().y);
        int endY = (int) (getBoundingRectangle().y + collideRect.height);
        startX = endX = (int) (getBoundingRectangle().x + collideRect.x + collideRect.width);
        System.out.println(startX+"x"+startY+" - "+endX+"x"+endY);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        if(tiles.size > 0) { return; }

        translateX(WALK_SPEED);
    }

    public Rectangle getCollideRect()
    {
        return collideRect;
    }
}
