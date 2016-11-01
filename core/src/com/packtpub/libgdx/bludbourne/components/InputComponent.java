package com.packtpub.libgdx.bludbourne.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Json;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.Component;
import com.packtpub.libgdx.bludbourne.multiplexer.GlobalMultiplexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public abstract class InputComponent implements Component {

    protected Entity.Direction _currentDirection = Entity.Direction.LEFT;
    protected Entity.State _currentState = null;
    protected Json _json;
    protected GlobalMultiplexer _globalMultiplexer;

    protected enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT
    }

    enum Mouse {
        SELECT, DOACTION
    }

    protected static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    protected static Map<Mouse, Boolean> mouseButtons = new HashMap<Mouse, Boolean>();

    //initialize hashmap for inputs
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.QUIT, false);
    };

    static {
        mouseButtons.put(Mouse.DOACTION, false);
        mouseButtons.put(Mouse.SELECT, false);
    }

    InputComponent(){
        _json = new Json();
        _globalMultiplexer = GlobalMultiplexer.getInstance();
        Gdx.input.setInputProcessor(_globalMultiplexer.getInputMultiplexer());
    }

    public void addInputProcessor(InputProcessor inputProcessor){
        Gdx.input.setInputProcessor(null);
        _globalMultiplexer.getInputMultiplexer().addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(_globalMultiplexer.getInputMultiplexer());
    }

    public void addInputProcessor(InputProcessor inputProcessor, int index){
        Gdx.input.setInputProcessor(null);
        _globalMultiplexer.getInputMultiplexer().addProcessor(index, inputProcessor);
        Gdx.input.setInputProcessor(_globalMultiplexer.getInputMultiplexer());
    }

    public void removeInputProcessor(InputProcessor inputProcessor){
        Gdx.input.setInputProcessor(null);
        _globalMultiplexer.getInputMultiplexer().removeProcessor(inputProcessor);
    }

    public abstract void update(Entity entity, float delta);
}
