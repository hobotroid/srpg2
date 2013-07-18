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
    private HashMap<String, Array> frames = new HashMap<String, Array>();

    public CharacterDefinition(Element xml)
    {
        this.characterName = xml.getAttribute("id");

        for(Element element : xml.getChildByName("frames").getChildrenByName("frame")) {
            CharacterFrame frame = new CharacterFrame(
                    element.getAttribute("label"),
                    Integer.parseInt(element.getAttribute("index")),
                    element.getAttribute("default")
            );
            //frames.add(frame);
            String label = frame.getLabel();
            if(frames.get(label) == null) {
                frames.put(label, new Array<CharacterFrame>());
            }
            frames.get(label).add(frame);
        }
    }

    public HashMap<String, Array> getFrames()
    {
        return frames;
    }
}
