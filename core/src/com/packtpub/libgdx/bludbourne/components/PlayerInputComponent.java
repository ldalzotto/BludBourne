package com.packtpub.libgdx.bludbourne.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.packtpub.libgdx.bludbourne.Entity;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public class PlayerInputComponent extends InputComponent implements InputProcessor{

    public final static String TAG = PlayerInputComponent.class.getSimpleName();
    private Vector3 _lastMouseCoordinates;

    private String _typingWord;
    private HashMap<String, Boolean> _wordAndLetterToType;

    private boolean _letterFound;

    public PlayerInputComponent(){
        this._lastMouseCoordinates = new Vector3();
        _typingWord = "";
        _letterFound = false;
        _wordAndLetterToType = new HashMap<String, Boolean>();
        _inputMultiplexer.addProcessor(this);
        //Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if(string.length == 0){
            return;
        }

        //Specifically for messages with 1 object payload
        if(string.length == 2) {
            if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())){
                //Gdx.app.debug(TAG, "Current Direction message received : " + message);
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.TYPING_WORD_INIT.toString())){
                _wordAndLetterToType = _json.fromJson(HashMap.class, string[1]);
            }
        }
    }

    @Override
    public void update(Entity entity, float delta) {
        //Keyboard input
        if(keys.get(Keys.LEFT)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, _json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, _json.toJson(Entity.Direction.LEFT));
        } else if (keys.get(Keys.RIGHT)) {
            entity.sendMessage(MESSAGE.CURRENT_STATE, _json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, _json.toJson(Entity.Direction.RIGHT));
        } else if (keys.get(Keys.UP)) {
            entity.sendMessage(MESSAGE.CURRENT_STATE, _json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, _json.toJson(Entity.Direction.UP));
        } else if (keys.get(Keys.DOWN)) {
            entity.sendMessage(MESSAGE.CURRENT_STATE, _json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, _json.toJson(Entity.Direction.DOWN));
        } else if(keys.get(Keys.QUIT)){
            Gdx.app.exit();
        } else {
            entity.sendMessage(MESSAGE.CURRENT_STATE, _json.toJson(Entity.State.IDLE));
            //entity.sendMessage(MESSAGE.CURRENT_DIRECTION, _json.toJson(Entity.Direction.DOWN));
        }

        //Mouse input
        if(mouseButtons.get(Mouse.SELECT)){
            entity.sendMessage(MESSAGE.INIT_SELECT_ENTITY, _json.toJson(_lastMouseCoordinates));
            mouseButtons.put(Mouse.SELECT, false);
        }

        if(_letterFound){
            entity.sendMessage(MESSAGE.TYPING_LETTER_FOUND, _json.toJson(_wordAndLetterToType));
            _letterFound = false;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
            this.leftPressed();
            return true;
        }
        if( keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
            this.rightPressed();
            return true;
        }
        if( keycode == Input.Keys.UP || keycode == Input.Keys.W){
            this.upPressed();
            return true;
        }
        if( keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
            this.downPressed();
            return true;
        }
        if( keycode == Input.Keys.ESCAPE){
            this.quitPressed();
            return true;
        }

        if(!_wordAndLetterToType.isEmpty()) {
            if (keycode == Input.Keys.T) {
                Iterator<String> keys = _wordAndLetterToType.keySet().iterator();
                while (keys.hasNext()) {
                    String letter = keys.next();
                    if (letter.length() == 1 && letter.equalsIgnoreCase("t") && !_wordAndLetterToType.get(letter)) {
                        _wordAndLetterToType.put(letter, Boolean.TRUE);
                        _letterFound = true;
                    }
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if( keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
            this.leftReleased();
        }
        if( keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
            this.rightReleased();
        }
        if( keycode == Input.Keys.UP || keycode == Input.Keys.W ){
            this.upReleased();
        }
        if( keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
            this.downReleased();
        }
        if( keycode == Input.Keys.Q){
            this.quitReleased();
        }
        return true;    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT){
            this.setClickedMouseCoordinates(screenX,screenY);
        }

        //left is selection, right is context menu
        if(button == Input.Buttons.LEFT){
            this.selectMouseButtonPressed(screenX, screenY);
        }
        if( button == Input.Buttons.RIGHT){
            this.doActionMouseButtonPressed(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //left is selection, right is context menu
        if( button == Input.Buttons.LEFT){
            this.selectMouseButtonReleased(screenX, screenY);
        }
        if( button == Input.Buttons.RIGHT){
            this.doActionMouseButtonReleased(screenX, screenY);
        }
        return true;    }

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

    //Key presses
    public void leftPressed(){
        keys.put(Keys.LEFT, true);
    }

    public void rightPressed(){
        keys.put(Keys.RIGHT, true);
    }

    public void upPressed(){
        keys.put(Keys.UP, true);
    }

    public void downPressed(){
        keys.put(Keys.DOWN, true);
    }
    public void quitPressed(){
        keys.put(Keys.QUIT, true);
    }

    public void setClickedMouseCoordinates(int x,int y){
        _lastMouseCoordinates.set(x, y, 0);
    }

    public void selectMouseButtonPressed(int x, int y){
        mouseButtons.put(Mouse.SELECT, true);
    }

    public void doActionMouseButtonPressed(int x, int y){
        mouseButtons.put(Mouse.DOACTION, true);
    }

    //Releases

    public void leftReleased(){
        keys.put(Keys.LEFT, false);
    }

    public void rightReleased(){
        keys.put(Keys.RIGHT, false);
    }

    public void upReleased(){
        keys.put(Keys.UP, false);
    }

    public void downReleased(){
        keys.put(Keys.DOWN, false);
    }

    public void quitReleased(){
        keys.put(Keys.QUIT, false);
    }

    public void selectMouseButtonReleased(int x, int y){
        mouseButtons.put(Mouse.SELECT, false);
    }

    public void doActionMouseButtonReleased(int x, int y){
        mouseButtons.put(Mouse.DOACTION, false);
    }

    public static void hide(){
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.QUIT, false);
    }

}
