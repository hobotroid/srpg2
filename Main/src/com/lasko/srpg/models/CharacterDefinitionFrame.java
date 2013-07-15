package com.lasko.srpg.models;

public class CharacterDefinitionFrame
{
    private String defaultFrame;
    private int index;
    private String label;

    public CharacterDefinitionFrame(String label, int index, String defaultFrame)
    {
        this.defaultFrame = defaultFrame;
        this.index = index;
        this.label = label;
    }
}