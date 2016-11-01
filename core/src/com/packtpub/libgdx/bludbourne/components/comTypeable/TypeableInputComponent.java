package com.packtpub.libgdx.bludbourne.components.comTypeable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.JsonValue;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.comAbstract.InputComponent;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class TypeableInputComponent extends InputComponent implements InputProcessor {

    private static final String TAG = TypeableInputComponent.class.getSimpleName();

    private String _typingWord;
    private WrapperWordAndLetterToType _wrapperWordAndLetterToType;

    private boolean _letterFound;

    public TypeableInputComponent(){
        _typingWord = "";
        _letterFound = false;
        _wrapperWordAndLetterToType = new WrapperWordAndLetterToType();
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

        if(string.length == 1){
            if(string[0].equalsIgnoreCase(MESSAGE.ENTITY_DESELECTED.toString())){
                removeInputProcessor(this);
                _wrapperWordAndLetterToType.getWordAndLetterToType().clear();
            }
        }

        if(string.length == 2){
            if(string[0].equalsIgnoreCase(MESSAGE.TYPING_WORD_INIT.toString())){
                addInputProcessor(this, 0);
                _wrapperWordAndLetterToType = _json.fromJson(WrapperWordAndLetterToType.class, string[1]);

            }
        }
    }

    @Override
    public void update(Entity entity, float delta) {

        if(_letterFound){
            entity.sendMessage(MESSAGE.TYPING_LETTER_FOUND, _json.toJson(_wrapperWordAndLetterToType));
            _letterFound = false;
        }

    }

    @Override
    public boolean keyDown(int keycode) {

        if(!_wrapperWordAndLetterToType.getWordAndLetterToType().isEmpty()) {
            String letterThatPlayerHaveToType = "";
            String keyOfLetterThatPlayerHaveToType = null;

            Integer wordLength = _wrapperWordAndLetterToType.getWordAndLetterToType().get(String.valueOf(0)).entrySet().iterator().next().getKey().length();

            for(int i = 1; i <= wordLength ; i++){
                if(!_wrapperWordAndLetterToType.getWordAndLetterToType().get(String.valueOf(i)).entrySet().iterator().next().getValue()){
                    letterThatPlayerHaveToType = _wrapperWordAndLetterToType.getWordAndLetterToType().get(String.valueOf(i)).entrySet().iterator().next().getKey();
                    keyOfLetterThatPlayerHaveToType = String.valueOf(i);
                    break;
                }
            }

            //TODO rendre ceci générique
            if (keycode == Input.Keys.T) {
                if(letterThatPlayerHaveToType.equalsIgnoreCase("t") && keyOfLetterThatPlayerHaveToType != null){
                    HashMap<String, Boolean> setValueToTrue = new HashMap<String, Boolean>();
                    setValueToTrue.put("t", Boolean.TRUE);
                    _wrapperWordAndLetterToType.getWordAndLetterToType().put(String.valueOf(keyOfLetterThatPlayerHaveToType),
                            setValueToTrue);
                    _letterFound = true;
                }
                return true;
            }
        }
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
