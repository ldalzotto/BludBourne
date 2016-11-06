package com.packtpub.libgdx.bludbourne.gui.actor;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventorySlotTarget extends Target{

    InventorySlot _targetSlot;

    public InventorySlotTarget(InventorySlot actor){
        super(actor);
        _targetSlot = actor;
    }

    @Override
    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        return true;
    }

    @Override
    public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {

    }

    @Override
    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        InventoryItem sourceActor = (InventoryItem) payload.getDragActor();
        InventoryItem targetActor = _targetSlot.getTopInventoryItem();
        InventorySlot sourceSlot = ((InventorySlotSource)source).getSourceSlot();

        if(sourceActor == null){
            return;
        }

        //first, does the slot accept the source item type?
        if(!_targetSlot.doesAcceptItemUseType(sourceActor.getItemUseType())){
            //put item back where it came from
            sourceSlot.add(sourceActor);
            return;
        }

        if(!_targetSlot.hasItem()){
            _targetSlot.add(sourceActor);
        } else {
            //If the same item and stackable, add
            if(sourceActor.isSameItemType(targetActor) && sourceActor.isStackable()){
                _targetSlot.add(sourceActor);
            } else {
                //If they aren't the same items or the items aren't stackable then swap
                InventorySlot.swapSlots(sourceSlot, _targetSlot, sourceActor);
            }
        }

    }
}
