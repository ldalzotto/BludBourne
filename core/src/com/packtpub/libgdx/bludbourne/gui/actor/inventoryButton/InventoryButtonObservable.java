package com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton;

import java.util.Observable;

/**
 * Created by ldalzotto on 06/11/2016.
 */
public class InventoryButtonObservable extends Observable {

    public static InventoryButtonObservable INSTANCE = null;

    private boolean _isButtonClicked = false;

    private InventoryButtonObservable(){

    }

    public static InventoryButtonObservable getInstance(){
        if(INSTANCE == null){
            INSTANCE = new InventoryButtonObservable();
        }
        return INSTANCE;
    }

    public void changeButtonClickedStatus(boolean value){
        if(_isButtonClicked != value){
            _isButtonClicked = value;
            setChanged();
        }
        notifyObservers(_isButtonClicked);
    }

}
