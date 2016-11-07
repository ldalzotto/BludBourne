package com.packtpub.libgdx.bludbourne.gui.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventorySlotSource extends Source {

    private DragAndDrop _dragAndDrop;
    private InventorySlot _sourceSlot;

    public InventorySlotSource(InventorySlot sourceSlot, DragAndDrop dragAndDrop){
        super(sourceSlot.getTopInventoryItem());
        _sourceSlot = sourceSlot;
        _dragAndDrop = dragAndDrop;
    }

    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
        Payload payload = new Payload();

        _sourceSlot = (InventorySlot) getActor().getParent();
        _sourceSlot.decrementItemCount();
        if(_sourceSlot.get_slotType().equals(InventorySlot.SlotType.EQUIPMENT)){
            _sourceSlot.get_defaultBackground().add(_sourceSlot.get_customBackgroundDecal());
        }

        payload.setDragActor(getActor());
        _dragAndDrop.setDragActorPosition(-x, -y + getActor().getHeight());

        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, DragAndDrop.Target target) {
        if(target == null){
            _sourceSlot.add(payload.getDragActor());
        }
    }

    public InventorySlot getSourceSlot(){
        return _sourceSlot;
    }
}
