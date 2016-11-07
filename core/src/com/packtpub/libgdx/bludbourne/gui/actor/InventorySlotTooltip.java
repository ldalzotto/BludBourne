package com.packtpub.libgdx.bludbourne.gui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventorySlotTooltip extends Window{

    private static String TAG = InventorySlotTooltip.class.getSimpleName();

    private Skin _skin;
    private Label _description;

    public InventorySlotTooltip(final Skin skin){
        super("", skin);
        _skin = skin;

        _description = new Label("", skin, "inventory-item-count");
        this.add(_description);
        this.pack();
        this.setVisible(false);
    }

    public void setVisible(InventorySlot inventorySlot, boolean visible){
        super.setVisible(visible);

        if(inventorySlot == null){
            //Gdx.app.debug(TAG, "Tooltip is not visible");
            return;
        }

        if(!inventorySlot.hasItem()){
            super.setVisible(false);
            //Gdx.app.debug(TAG, "Tooltip is not visible");
        } else {
            //Gdx.app.debug(TAG, "Tooltip is visible");
        }

    }

    public void updateDescription(InventorySlot inventorySlot){
        if(inventorySlot.hasItem()){
            _description.setText(inventorySlot.getTopInventoryItem().getItemShortDescription());
            this.pack();
        } else {
            _description.setText("");
            this.pack();
        }
    }



}
