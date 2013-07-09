package com.lasko.srpg;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lasko.srpg.models.RpgCharacter;

public class MapActor
{
    public static final float WALK_SPEED = .1f;

    RpgCharacter character;
    Texture texture;
    Sprite sprite;

    private Vector2 mapPosition = new Vector2();

    public MapActor(RpgCharacter character, Texture texture)
    {
        this.character = character;
        this.texture = texture;
        //this.sprite = new Sprite(texture, 0, 0, 48, 48);
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
