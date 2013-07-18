package com.lasko.srpg.models;

import com.badlogic.gdx.utils.Array;
import com.lasko.srpg.assets.Assets;
import java.util.HashMap;

public class RpgCharacter
{
    private int id;
    private String name;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int speed;

    private HashMap<String, Array> animationFrames = new HashMap<String, Array>();

    public RpgCharacter(String name)
    {
        this.name = name;

        CharacterDefinition def = Assets.get().getCharacterDefinition(name);
        this.animationFrames = def.getFrames();
    }

    public final void damage(int value)
    {
        hp -= Math.min(value, hp);
    }

    public final void heal(int value)
    {
        hp += Math.min(value, maxHp);
    }

    public final String getName()
    {
        return name;
    }

    public final void setName(String name)
    {
        this.name = name;
    }

    public final int getSpeed()
    {
        return speed;
    }

    public HashMap<String, Array> getFrames()
    {
        return animationFrames;
    }
}
