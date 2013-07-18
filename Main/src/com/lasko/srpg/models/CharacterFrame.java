package com.lasko.srpg.models;

public class CharacterFrame
{
    private String defaultFrame;
    private int index;
    private String label;

    public CharacterFrame(String label, int index, String defaultFrame)
    {
        this.defaultFrame = defaultFrame;
        this.index = index;
        this.label = label;
    }

    public String getDefaultFrame()
    {
        return defaultFrame;
    }

    public int getIndex()
    {
        return index;
    }

    public String getLabel()
    {
        return label;
    }
}