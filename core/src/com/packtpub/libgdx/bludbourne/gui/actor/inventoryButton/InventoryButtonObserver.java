package com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton;

import com.badlogic.gdx.Gdx;
import com.packtpub.libgdx.bludbourne.gui.PlayerHUD;
import com.packtpub.libgdx.bludbourne.gui.actor.StatusUI;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by ldalzotto on 06/11/2016.
 */
public class InventoryButtonObserver implements Observer {

    public static final String TAG = InventoryButtonObserver.class.getSimpleName();

    private PlayerHUD _currentPlayerHUD;
    private Boolean _isButtonClicked = false;

    public InventoryButtonObserver(PlayerHUD playerHUD){
        super();
        _currentPlayerHUD = playerHUD;
    }

    @Override
    public void update(Observable o, Object arg) {
        Gdx.app.debug(TAG, "The inventory button has been clicked !");

        if(arg instanceof Boolean){
            _isButtonClicked = (Boolean) arg;
        }

        _currentPlayerHUD.inventoryButtonClicked(_isButtonClicked);
    }
}
