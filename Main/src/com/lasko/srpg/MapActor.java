package com.lasko.srpg;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lasko.srpg.map.Map;
import com.lasko.srpg.models.RpgCharacter;

public class MapActor
{
    public static final float WALK_SPEED = .1f;

    RpgCharacter character;
    Texture texture;
    Sprite sprite;
    Map map;

    private Vector2 mapPosition = new Vector2();
    private int width, height;
    private Array<Rectangle> tiles = new Array<Rectangle>();

    public MapActor(RpgCharacter character, Texture texture)
    {
        this.character = character;
        this.texture = texture;
        this.width = 48;
        this.height = texture.getHeight();
        //this.sprite = new Sprite(texture, 0, 0, 48, 48);
    }


    public Map getMap()
    {
        return map;
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    public RpgCharacter getCharacter()
    {
        return character;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void moveUp()
    {

        int startX, startY, endX, endY;
        /*if(koala.velocity.x > 0) {
            startX = endX = (int)(koala.position.x + Koala.WIDTH + koala.velocity.x);
        } else {
            startX = endX = (int)(koala.position.x + koala.velocity.x);
        }*/
        startX = (int)mapPosition.x;
        endX = (int)(mapPosition.x + width);
        startY = (int)(mapPosition.y + height - 10);
        endY = (int)(mapPosition.y + height);
        map.getCollisionTiles(startX, startY, endX, endY, tiles);
        System.out.println(startX +"x"+startY + " - " + endX + "x" + endY +" = " + tiles);

        mapPosition.y += WALK_SPEED;
    }

    public void moveDown()
    {
        mapPosition.y -= WALK_SPEED;
    }

    public void moveLeft()
    {
        mapPosition.x -= WALK_SPEED;
    }

    public void moveRight()
    {
        mapPosition.x += WALK_SPEED;
    }

    public final void setMapPosition(Vector2 position)
    {
        this.mapPosition = position;
    }

    public final void setMapPosition(float x, float y)
    {
        this.mapPosition.x = x;
        this.mapPosition.y = y;
    }

    public final Vector2 getMapPosition()
    {
        return mapPosition;
    }
}
