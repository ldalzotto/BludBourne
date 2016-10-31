package com.packtpub.libgdx.bludbourne.map;

import java.util.Hashtable;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public class MapFactory {
    //All maps for the game
    private static Hashtable<MapType, Map> _maptable = new Hashtable<MapType, Map>();

    public static enum MapType{
        TOP_WORLD, TOWN, CASTLE_OF_DOOM
    }

    static public Map getMap(MapType mapType){
        Map map = null;
        switch (mapType){
            case TOP_WORLD:
                map = _maptable.get(MapType.TOP_WORLD);
                if(map == null){
                    map = new TopWorldMap();
                    _maptable.put(MapType.TOP_WORLD, map);
                }
                break;
            case TOWN:
                map = _maptable.get(MapType.TOWN);
                if(map == null){
                    map = new TownMap();
                    _maptable.put(MapType.TOWN, map);
                }
                break;
            case CASTLE_OF_DOOM:
                map = _maptable.get(MapType.CASTLE_OF_DOOM);
                if(map == null){
                    map = new CastleDoomMap();
                    _maptable.put(MapType.CASTLE_OF_DOOM, map);
                }
                break;
            default:
                break;
        }
        return map;
    }
}
