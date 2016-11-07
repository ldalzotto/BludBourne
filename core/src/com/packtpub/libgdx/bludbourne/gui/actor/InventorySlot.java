package com.packtpub.libgdx.bludbourne.gui.actor;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.packtpub.libgdx.bludbourne.Utility;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventorySlot extends Stack {

    private Stack _defaultBackground;
    private Image _customBackgroundDecal;
    private Label _numItempsLabel;
    private int _numItemsVal = 0;
    private int _filterItemType;
    private SlotType _slotType;

    public enum SlotType{
        EQUIPMENT,
        INVENTORY;
    }

    public InventorySlot(){
        _filterItemType = 0; //filter nothing
        _slotType = SlotType.INVENTORY;
        _defaultBackground = new Stack();
        _customBackgroundDecal = new Image();
        Image image = new Image(new NinePatch(Utility.STATUSUI_TEXTUREATLAS.createPatch("dialog")));
        _defaultBackground.add(image);

        _numItempsLabel = new Label(String.valueOf(_numItemsVal), Utility.STATUSUI_SKIN, "inventory-item-count");
        _numItempsLabel.setAlignment(Align.bottomRight);
        _numItempsLabel.setVisible(false);

        this.add(_defaultBackground);
        this.add(_numItempsLabel);
    }

    public InventorySlot(int filterItemType, Image customBackgroundDecal){
        this();
        _filterItemType = filterItemType;
        _customBackgroundDecal = customBackgroundDecal;
        _defaultBackground.add(_customBackgroundDecal);
        _slotType = SlotType.EQUIPMENT;
    }

    public void decrementItemCount(){
        _numItemsVal--;
        _numItempsLabel.setText(String.valueOf(_numItemsVal));
        if(_defaultBackground.getChildren().size > 1){
            _defaultBackground.getChildren().pop();
        }
        checkVisibilityOfItemCount();
    }

    private void checkVisibilityOfItemCount(){
        if(_numItemsVal < 2){
            _numItempsLabel.setVisible(false);
        }else {
            _numItempsLabel.setVisible(true);
        }
    }

    public void incrementItemCount(){
        _numItemsVal++;
        _numItempsLabel.setText(String.valueOf(_numItemsVal));
        if(_defaultBackground.getChildren().size > 1){
            _defaultBackground.getChildren().pop();
        }
        checkVisibilityOfItemCount();
    }

    @Override
    public void add(Actor actor){
        super.add(actor);
        if(_numItempsLabel == null){
            return;
        }
        if(!actor.equals(_defaultBackground) && !actor.equals(_numItempsLabel)){
           incrementItemCount();
        }
    }

    public void add(Array<Actor> actors){
        if(actors == null){
            return;
        }

        for (Actor actor :
                actors) {
            add(actor);
        }

    }

    public InventoryItem getTopInventoryItem(){
        InventoryItem actor = null;
        if(hasChildren()){
            SnapshotArray<Actor> items = this.getChildren();
            if(items.size > 2){
                actor = (InventoryItem) items.peek();
            }
        }
        return actor;
    }

    public boolean doesAcceptItemUseType(int itemUseType){
        if(_filterItemType == 0){
            return true;
        }else {
            return ((_filterItemType & itemUseType) == itemUseType);
        }
    }

    public boolean hasItem(){
        if(hasChildren()){
            SnapshotArray<Actor> items = this.getChildren();
            if(items.size > 2){
                return true;
            }
        }
        return false;
    }

    public Array<Actor> getAllInventoryItems(){
        Array<Actor> items = new Array<Actor>();
        if(hasItem()){
            SnapshotArray<Actor> arrayChildren = getChildren();
            int numInventoryItems = arrayChildren.size - 2;
            for(int i = 0; i < numInventoryItems; i++){
                items.add(arrayChildren.pop());
                decrementItemCount();
            }
        }
        return items;
    }

    public void clearAllInventoryItems(){
        if(hasItem()){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            int numInventiryItems = getNumItems();
            for(int i = 0; i < numInventiryItems; i++){
                arrayChildren.pop();
                decrementItemCount();
            }
        }
    }

    public int getNumItems(){
        if(hasChildren()){
            SnapshotArray<Actor> items = this.getChildren();
            return items.size - 2;
        }
        return 0;
    }

    public static void swapSlots(InventorySlot inventorySlotSource, InventorySlot inventorySlotTarget, InventoryItem dragActor){
        //check if items can accept each orther, otherwise, no swap
        if(!inventorySlotTarget.doesAcceptItemUseType(dragActor.getItemUseType()) ||
                !inventorySlotSource.doesAcceptItemUseType(inventorySlotTarget.getTopInventoryItem().getItemUseType())){
            inventorySlotSource.add(dragActor);
            return;
        }

        //swap
        Array<Actor> tempArray = inventorySlotSource.getAllInventoryItems();
        tempArray.add(dragActor);
        inventorySlotSource.add(inventorySlotTarget.getTopInventoryItem());
        inventorySlotTarget.add(tempArray);
    }

    public SlotType get_slotType() {
        return _slotType;
    }

    public Image get_customBackgroundDecal() {
        return _customBackgroundDecal;
    }

    public Stack get_defaultBackground() {
        return _defaultBackground;
    }
}
