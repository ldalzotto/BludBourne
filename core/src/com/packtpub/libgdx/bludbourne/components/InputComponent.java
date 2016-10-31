package com.packtpub.libgdx.bludbourne.components;

import com.badlogic.gdx.utils.Json;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public abstract class InputComponent implements Component {

    protected Entity.Direction _currentDirection = Entity.Direction.LEFT;
    protected Entity.State _currentState = null;
    protected Json _json;

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
    }

    public abstract void update(Entity entity, float delta);
}
