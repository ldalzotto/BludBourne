package com.packtpub.libgdx.bludbourne.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.packtpub.libgdx.bludbourne.Utility;
import com.packtpub.libgdx.bludbourne.gui.actor.InventoryItem;
import com.packtpub.libgdx.bludbourne.gui.actor.InventoryItemLocation;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventoryItemFactory {

    private Json _json = new Json();
    private final String INVENTORY_ITEM = "scripts/inventory_items.json";
    private static InventoryItemFactory _instance = null;
    private Hashtable<InventoryItem.ItemTypeID, InventoryItem> _inventoryItemList;

    public static InventoryItemFactory getInstance(){
        if(_instance == null){
            _instance = new InventoryItemFactory();
        }

        return _instance;
    }

    private InventoryItemFactory(){
        ArrayList<JsonValue> list = _json.fromJson(ArrayList.class, Gdx.files.internal(INVENTORY_ITEM));
        _inventoryItemList = new Hashtable<InventoryItem.ItemTypeID, InventoryItem>();

        for (JsonValue jsonVal :
                list) {
            InventoryItem inventoryItem = _json.readValue(InventoryItem.class, jsonVal);
            _inventoryItemList.put(inventoryItem.getItemTypeID(), inventoryItem);
        }
    }

    public InventoryItem getInventoryItem(InventoryItem.ItemTypeID inventoryItemType){
        InventoryItem item = new InventoryItem(_inventoryItemList.get(inventoryItemType));
        item.setDrawable(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(item.getItemTypeID().toString())));
        item.setScaling(Scaling.none);
        return item;
    }

    public Array<InventoryItemLocation> getInitInventory(){
        ArrayList<JsonValue> list = _json.fromJson(ArrayList.class, Gdx.files.internal(INVENTORY_ITEM));
        Array<InventoryItemLocation> locationList = new Array<InventoryItemLocation>();
        int counter = 0;
        for (JsonValue jsonValue:
             list) {
            InventoryItem inventoryItem = _json.readValue(InventoryItem.class, jsonValue);
            locationList.add(new InventoryItemLocation(counter, inventoryItem.getItemTypeID().toString(), 1));
            counter ++;
        }
        return locationList;
    }

}
