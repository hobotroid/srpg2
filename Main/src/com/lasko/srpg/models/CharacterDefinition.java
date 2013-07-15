package com.lasko.srpg.models;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.util.HashMap;

public class CharacterDefinition
{
    private String characterName;
    private String fullName;
    private int hp;
    private int mp;
    private int str;
    private int dex;
    private int speed;

    private int width;
    private int height;
    private Array<CharacterDefinitionFrame> frames = new Array<CharacterDefinitionFrame>();

    public CharacterDefinition(Element xml)
    {
        this.characterName = xml.getAttribute("id");

        for(Element element : xml.getChildByName("frames").getChildrenByName("frame")) {
            CharacterDefinitionFrame frame = new CharacterDefinitionFrame(
                    element.getAttribute("label"),
                    Integer.parseInt(element.getAttribute("index")),
                    element.getAttribute("default")
            );
            frames.add(frame);
        }
    }
}