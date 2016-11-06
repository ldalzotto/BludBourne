package com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by ldalzotto on 06/11/2016.
 */
public class InventoryReturnButtonListener extends InputListener{

    private InventoryButtonObservable _inventoryButtonObservable;

    public InventoryReturnButtonListener(InventoryButtonObservable inventoryButtonObservable){
        super();
        _inventoryButtonObservable = inventoryButtonObservable;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        _inventoryButtonObservable.changeButtonClickedStatus(false);
        return true;
    }
}
