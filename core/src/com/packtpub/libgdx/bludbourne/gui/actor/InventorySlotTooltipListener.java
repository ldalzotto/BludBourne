package com.packtpub.libgdx.bludbourne.gui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventorySlotTooltipListener extends InputListener{

    private InventorySlotTooltip _toolTip;
    private boolean _isInside = false;
    private Vector2 _currentCoords;
    private Vector2 _offset;

    private InventoryUI _inventoryUI;

    public InventorySlotTooltipListener(InventorySlotTooltip toolTip, InventoryUI inventoryUI){
        _toolTip = toolTip;
        _currentCoords = new Vector2(0,0);
        _offset = new Vector2(60, 20);
        _inventoryUI = inventoryUI;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        InventorySlot inventorySlot = (InventorySlot)event.getListenerActor();
        _toolTip.setVisible(inventorySlot, false);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        InventorySlot inventorySlot = (InventorySlot)event.getListenerActor();
        _isInside = true;

        _currentCoords.set(Gdx.input.getX(), Gdx.input.getY());
        //inventorySlot.localToParentCoordinates(_currentCoords);

        _toolTip.updateDescription(inventorySlot);
        _toolTip.setPosition(_currentCoords.x + _offset.x, _currentCoords.y + _offset.y);
        _toolTip.toFront();
        _toolTip.setVisible(inventorySlot, true);
        /**if(fromActor instanceof InventoryUI){
            ((InventoryUI)fromActor).addActor(_toolTip);
        }**/
        _inventoryUI.addActor(_toolTip);
    }


    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        InventorySlot inventorySlot = (InventorySlot) event.getListenerActor();
        if(_isInside){
            _currentCoords.set(Gdx.input.getX(), Gdx.input.getY());
            //inventorySlot.localToParentCoordinates(_currentCoords);

            _toolTip.setPosition(_currentCoords.x+_offset.x, _currentCoords.y+_offset.y);
        }
        return true;
    }


    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        InventorySlot inventorySlot = (InventorySlot)event.getListenerActor();
        _toolTip.setVisible(inventorySlot, false);
        _isInside = false;

        _currentCoords.set(Gdx.input.getX(), Gdx.input.getY());
        //inventorySlot.localToParentCoordinates(_currentCoords);
        _inventoryUI.removeActor(_toolTip);
    }
}
