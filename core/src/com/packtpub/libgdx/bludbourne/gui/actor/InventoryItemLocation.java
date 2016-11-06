package com.packtpub.libgdx.bludbourne.gui.actor;

/**
 * Created by ldalzotto on 05/11/2016.
 */
public class InventoryItemLocation {
    private int locationIndex;
    private String itemTypeArLocation;
    private int numberItemsAtLocation;

    public InventoryItemLocation(){}

    public InventoryItemLocation(int locationIndex, String itemTypeArLocation, int numberItemsAtLocation){
        this.locationIndex = locationIndex;
        this.itemTypeArLocation = itemTypeArLocation;
        this.numberItemsAtLocation = numberItemsAtLocation;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }

    public String getItemTypeArLocation() {
        return itemTypeArLocation;
    }

    public void setItemTypeArLocation(String itemTypeArLocation) {
        this.itemTypeArLocation = itemTypeArLocation;
    }

    public int getNumberItemsAtLocation() {
        return numberItemsAtLocation;
    }

    public void setNumberItemsAtLocation(int numberItemsAtLocation) {
        this.numberItemsAtLocation = numberItemsAtLocation;
    }
}
