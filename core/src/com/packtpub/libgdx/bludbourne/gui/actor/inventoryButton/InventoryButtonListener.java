package com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.packtpub.libgdx.bludbourne.gui.actor.StatusUI;

/**
 * Created by ldalzotto on 06/11/2016.
 */
public class InventoryButtonListener extends InputListener {

    private InventoryButtonObservable _inventoryButtonObservable;

    public InventoryButtonListener(InventoryButtonObservable observable) {
        _inventoryButtonObservable = observable;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        _inventoryButtonObservable.changeButtonClickedStatus(true);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
    }

}
