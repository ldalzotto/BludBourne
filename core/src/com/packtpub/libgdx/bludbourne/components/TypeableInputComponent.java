package com.packtpub.libgdx.bludbourne.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.packtpub.libgdx.bludbourne.Entity;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class TypeableInputComponent extends InputComponent implements InputProcessor {

    private String _typingWord;
    private HashMap<String, Boolean> _wordAndLetterToType;

    private boolean _letterFound;

    public TypeableInputComponent(){
        _typingWord = "";
        _letterFound = false;
        _wordAndLetterToType = new HashMap<String, Boolean>();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(Component.MESSAGE_TOKEN);

        if(string.length == 0){
            return;
        }

        if(string.length == 2){
            if(string[0].equalsIgnoreCase(MESSAGE.TYPING_WORD_INIT.toString())){
                _wordAndLetterToType = _json.fromJson(HashMap.class, string[1]);
            }
        }
    }

    @Override
    public void update(Entity entity, float delta) {

        if(_letterFound){
            entity.sendMessage(MESSAGE.TYPING_LETTER_FOUND, _json.toJson(_wordAndLetterToType));
            _letterFound = false;
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
