package com.packtpub.libgdx.bludbourne.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton.InventoryButtonObservable;
import com.packtpub.libgdx.bludbourne.gui.actor.InventoryUI;
import com.packtpub.libgdx.bludbourne.gui.actor.StatusUI;
import com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton.InventoryButtonObserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by ldalzotto on 03/11/2016.
 */
public class PlayerHUD implements Screen {

    private static String TAG = PlayerHUD.class.getSimpleName();

    private Stage _stage;
    private Viewport _viewport;
    private Camera _camera;
    private Entity _player;

    private StatusUI _statusUI;
    private InventoryUI _inventoryUI;

    private InventoryButtonObservable _inventoryButtonObservable;

    public PlayerHUD(Camera camera, Entity player) {
        _camera = camera;
        _player = player;

        _viewport = new ScreenViewport();
        _stage = new Stage(_viewport);

        _statusUI = new StatusUI();
        _statusUI.setVisible(true);
        _statusUI.setPosition(0,0);

        _stage.addActor(_statusUI);

        _inventoryUI = new InventoryUI();
        _inventoryUI.setVisible(false);
        _inventoryUI.setPosition(_viewport.getScreenHeight()/2,_viewport.getScreenWidth()/2);

        populateInventory();

        _inventoryButtonObservable = InventoryButtonObservable.getInstance();
        _inventoryButtonObservable.addObserver(new InventoryButtonObserver(this));

        _stage.addActor(_inventoryUI);
    }

    public void inventoryButtonClicked(boolean isInventoryButtonClicked){
        if(isInventoryButtonClicked){
            _inventoryUI.setVisible(true);
        } else {
            _statusUI.closeInventoryButtonImage();
            _statusUI.setVisible(true);
            _inventoryUI.setVisible(false);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        _stage.dispose();
    }

    public Stage get_stage() {
        return _stage;
    }

    public void populateInventory(){
        _inventoryUI.populateInventory(null, InventoryItemFactory.getInstance().getInitInventory());
    }
}
