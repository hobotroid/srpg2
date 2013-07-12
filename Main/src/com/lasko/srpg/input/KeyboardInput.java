package com.lasko.srpg.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.lasko.srpg.Srpg;

public class KeyboardInput implements InputProcessor
{
    private Srpg game;

    public KeyboardInput()
    {
        super();
        this.game = (Srpg)Gdx.app.getApplicationListener();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if(keycode >= Keys.NUM_1 && keycode <= Keys.NUM_9) {
            game.getCurrentMap().toggleLayer(keycode - Keys.NUM_1);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        //System.out.println(1);
        Srpg.mouseCoordinates = new Vector3(screenX, screenY, 0);
        game.getCamera().unproject(Srpg.mouseCoordinates);
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
