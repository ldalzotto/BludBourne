package com.packtpub.libgdx.bludbourne.components.comNPC;

import com.badlogic.gdx.InputProcessor;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.comAbstract.InputComponent;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class NPCInputComponent extends InputComponent implements InputProcessor {

    private static final String TAG = NPCInputComponent.class.getSimpleName();

    private float _frameTime = 0.0f;

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

    @Override
    public void dispose() {

    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if(string.length == 0) return;

        if(string.length == 2){
            if(string[0].equalsIgnoreCase(MESSAGE.INIT_DIRECTION.toString())){
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())){
                _currentState = _json.fromJson(Entity.State.class, string[1]);
            }
        }


    }

    @Override
    public void update(Entity entity, float delta) {
        entity.sendMessage(MESSAGE.CURRENT_DIRECTION, _json.toJson(_currentDirection));
        entity.sendMessage(MESSAGE.CURRENT_STATE, _json.toJson(_currentState));
    }
}
