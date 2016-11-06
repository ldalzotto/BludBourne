package com.packtpub.libgdx.bludbourne.multiplexer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

/**
 * Created by ldalzotto on 01/11/2016.
 */
public class GlobalMultiplexer {

    private InputMultiplexer _inputMultiplexer;

    public InputMultiplexer getInputMultiplexer() {
        return _inputMultiplexer;
    }

    public void setInputMultiplexer(InputMultiplexer _inputMultiplexer) {
        this._inputMultiplexer = _inputMultiplexer;
    }

    private static GlobalMultiplexer INSTANCE = null;

    private GlobalMultiplexer (){
        this._inputMultiplexer = new InputMultiplexer();
    }

    public static GlobalMultiplexer getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GlobalMultiplexer();
        }
        return INSTANCE;
    }

}
