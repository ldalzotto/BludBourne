package com.packtpub.libgdx.bludbourne.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.profile.ProfileManager;
import com.packtpub.libgdx.bludbourne.profile.ProfileObserver;

/**
 * Created by ldalzotto on 29/10/2016.
 */
public class MapManager implements ProfileObserver{

    private static final String TAG = MapManager.class.getSimpleName();

    private Camera _camera;
    private boolean _mapChanged = false;
    private Map _currentMap;
    private Entity _player;

    public MapManager(){

    }

    public void loadMap(MapFactory.MapType mapType){
        Map map = MapFactory.getMap(mapType);

        if(map == null){
            Gdx.app.debug(TAG, "Map does not exist!");
            return;
        }

        _currentMap = map;
        _mapChanged = true;
        Gdx.app.debug(TAG, "Player Start: (" + _currentMap.getPlayerStart().x + "," + _currentMap.getPlayerStart().y + ")");
    }

    public void setClosestPositionFromScaledUnits(Vector2 position){
        _currentMap.setClosestStartPositionFromScaledUnits(position);
    }

    public MapLayer getCollisionLayer(){
        return _currentMap.getCollisionLayer();
    }

    public MapLayer getPortalLayer(){
        return _currentMap.getPortalLayer();
    }
    public Vector2 getPlayerStartUnitScaled() {
        return _currentMap.getPlayerStartUnitScaled();
    }

    public TiledMap getCurrentTiledMap(){
        if(_currentMap == null){
            loadMap(MapFactory.MapType.TOWN);
        }
        return _currentMap.getCurrentTiledMap();
    }

    public void updateCurrentMapEntities(MapManager mapMgr, Batch batch, float delta){
        _currentMap.updateMapEntities(mapMgr, batch, delta);
    }

    public final Array<Entity> getCurrentMapEntities(){
        return _currentMap.getMapEntities();
    }

    public Entity getEntityToType(){
        Entity returnEntity = null;
        for (Entity entity :
                _currentMap.getMapEntities()) {
            if(entity.isGettingTyped()){
                returnEntity = entity;
            }
        }
        return returnEntity;
    }

    @Override
    public void onNotify(ProfileManager profileManager, ProfileEvent profileEvent) {
        switch (profileEvent){
            case PROFILE_LOADED:
                String currentMap = profileManager.getProperty("currentMapType", String.class);
                MapFactory.MapType mapType;
                if(currentMap == null || currentMap.isEmpty()){
                    mapType = MapFactory.MapType.TOWN;
                } else {
                    mapType = MapFactory.MapType.valueOf(currentMap);
                }
                loadMap(mapType);
                break;
            case SAVING_PROFILE:
                if(_currentMap == null){
                    break;
                }
                profileManager.setProperty(
                        "currentMapType",
                        _currentMap._currentMapType.toString()
                );
                break;
            default:
                break;
        }
    }

    public void setPlayer(Entity entity){
        this._player = entity;
    }

    public Entity getPlayer(){
        return this._player;
    }

    public void setCamera(Camera camera){
        this._camera = camera;
    }

    public Camera getCamera(){
        return _camera;
    }

    public boolean hasMapChanged(){
        return _mapChanged;
    }

    public void setMapChanged(boolean hasMapChanged){
        this._mapChanged = hasMapChanged;
    }

}
