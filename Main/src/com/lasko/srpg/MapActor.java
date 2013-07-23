package com.lasko.srpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.lasko.srpg.map.Map;
import com.lasko.srpg.models.CharacterFrame;
import com.lasko.srpg.models.RpgCharacter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MapActor extends Sprite
{
    public static final float WALK_SPEED = .1f;

    RpgCharacter character;
    Map map;

    private Rectangle collideRect = new Rectangle();

    private HashMap<String, Array> animationFrames = new HashMap<String, Array>();
    private String animationLabel = "downstill";
    private int animationIndex = 0;
    private float stateTime = 0f;
    private HashMap<String, Animation> animations = new HashMap<String, Animation>();
    TextureRegion currentFrame;

    public MapActor(RpgCharacter character, Texture texture)
    {
        super(texture, 0, 0, character.getWidth(), character.getHeight());
        setOrigin(0, 0);
        this.character = character;
        this.animationFrames = character.getFrames();

        collideRect.set(12, 0, this.getWidth() - 24, 5);

        TextureRegion[][] spriteFrames = TextureRegion.split(texture, texture.getWidth() / (texture.getWidth() / character.getWidth()), texture.getHeight() / 1);
        Iterator it = animationFrames.entrySet().iterator();
        while(it.hasNext()) {
            Entry pairs = (Entry)it.next();
            Array<CharacterFrame> frames = (Array<CharacterFrame>)pairs.getValue();
            TextureRegion[] regions = new TextureRegion[frames.size];
            int index = 0;

            for(CharacterFrame frame : frames) {
                regions[index++] = spriteFrames[0][frame.getIndex()];
            }
            Animation animation = new Animation(0.15f, regions);
            animations.put(pairs.getKey().toString(), animation);
        }
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
        animationLabel = "upwalk";

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
        animationLabel = "downwalk";

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
        animationLabel = "leftwalk";

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
        animationLabel = "rightwalk";

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

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        if(animations.containsKey(animationLabel)) {
            currentFrame = animations.get(animationLabel).getKeyFrame(stateTime, true);
            this.setRegion(currentFrame);
            super.draw(spriteBatch);
        } else {
            super.draw(spriteBatch);
        }
    }

    public Rectangle getCollideRect()
    {
        return new Rectangle(getBoundingRectangle().x + collideRect.x, getBoundingRectangle().y + collideRect.y, collideRect.width, collideRect.height);
    }
}
