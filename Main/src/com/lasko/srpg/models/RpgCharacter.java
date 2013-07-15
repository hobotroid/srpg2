package com.lasko.srpg.models;

import com.lasko.srpg.assets.Assets;

public class RpgCharacter
{
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int speed;

    protected int id;
    protected String name;

    public RpgCharacter(String name)
    {
        this.name = name;

        CharacterDefinition def = Assets.get().getCharacterDefinition(name);
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
}
