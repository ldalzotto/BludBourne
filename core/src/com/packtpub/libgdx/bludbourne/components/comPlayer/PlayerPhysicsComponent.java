package com.packtpub.libgdx.bludbourne.components.comPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.comAbstract.PhysicsComponent;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;
import com.packtpub.libgdx.bludbourne.map.Map;
import com.packtpub.libgdx.bludbourne.map.MapFactory;
import com.packtpub.libgdx.bludbourne.map.MapManager;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public class PlayerPhysicsComponent extends PhysicsComponent {

    private static final String TAG = PlayerPhysicsComponent.class.getSimpleName();

    private Entity.State _state;
    private Vector3 _mouseSelectCoordinates;
    private boolean _isMouseSelectEnable = false;
    private Ray _selectionRay;
    private float _selectRayMaximumDistance = 32.0f;

    private Hashtable<String, Boolean> _selectedEntity;

    public PlayerPhysicsComponent(){
        _bouBoundingBoxLocation = BoundingBoxLocation.BOTTOM_CENTER;
        initBoundingBox(0.3f, 0.3f);

        _mouseSelectCoordinates = new Vector3(0,0,0);
        _selectionRay = new Ray(new Vector3(), new Vector3());
        _selectedEntity = new Hashtable<String, Boolean>();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(Component.MESSAGE_TOKEN);

        if(string.length == 0){
            return;
        }

        //Specifically for messages with 1 object payload
        if(string.length == 2){
            if(string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())){
                _currentEntityPosition = _json.fromJson(Vector2.class, string[1]);
                _nextEntityPosition.set(_currentEntityPosition.x, _currentEntityPosition.y);
                Gdx.app.debug(TAG, "INIT_START_POSITION message received : " + _currentEntityPosition + "," + _nextEntityPosition);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())){
                _state = _json.fromJson(Entity.State.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())){
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_SELECT_ENTITY.toString())){
                _mouseSelectCoordinates = _json.fromJson(Vector3.class, string[1]);
                _isMouseSelectEnable = true;
                Gdx.app.debug(TAG, "INIT_SELECT_ENTITY message received : " + _mouseSelectCoordinates);
            }
        }

    }

    private void selectMapEntityCandidate(MapManager mapMgr){
        Array<Entity> currentEntities = mapMgr.getCurrentMapEntities();

        //Convert screen coordinates to world coordinates
        //then to unit scale coordinates
        mapMgr.getCamera().unproject(_mouseSelectCoordinates);
        _mouseSelectCoordinates.x /= Map.UNIT_SCALE;
        _mouseSelectCoordinates.y /= Map.UNIT_SCALE;

        for (Entity mapEntity :
                currentEntities) {
            //Don't break, reset all entities
            mapEntity.sendMessage(MESSAGE.ENTITY_DESELECTED);
            if(_selectedEntity.get(mapEntity.getEntityConfig().getEntityID())!=null){
                _selectedEntity.remove(mapEntity.getEntityConfig().getEntityID());
            }
            Rectangle mapEntityBoundingBox = mapEntity.getCurrentBoundingBox();

            if(mapEntity.getCurrentBoundingBox().contains(_mouseSelectCoordinates.x, _mouseSelectCoordinates.y)){
                //check distance
                _selectionRay.set(_boundingBox.x, _boundingBox.y, 0.0f, mapEntityBoundingBox.x, mapEntityBoundingBox.y, 0.0f);
                float distance = _selectionRay.origin.dst(_selectionRay.direction);

                if(distance <= _selectRayMaximumDistance){
                    //we have a valid entity selecttion Picked/Selected
                    Gdx.app.debug(TAG, "Selected Entity! " + mapEntity.getEntityConfig().getEntityID());
                    mapEntity.sendMessage(MESSAGE.ENTITY_SELECTED);
                    _selectedEntity.put(mapEntity.getEntityConfig().getEntityID(), true);
                }
            }
        }

    }

    private void clearMapEntityCandidateIfTooFarAway(MapManager mapManager){
        Enumeration<String> enumKey = _selectedEntity.keys();
        while(enumKey.hasMoreElements()){
            String key = enumKey.nextElement();
            if(_selectedEntity.get(key)){
                Array<Entity> currentEntities = mapManager.getCurrentMapEntities();
                for (Entity mapEntity :
                        currentEntities) {
                        if (mapEntity.getEntityConfig().getEntityID().equals(key)) {
                            Vector2 entityPosition = new Vector2(0,0);
                            mapEntity.getCurrentBoundingBox().getCenter(entityPosition);
                            entityPosition.scl(Map.UNIT_SCALE);
                            if(_currentEntityPosition.dst(entityPosition) >= _selectRayMaximumDistance*Map.UNIT_SCALE){
                                _selectedEntity.remove(mapEntity.getEntityConfig().getEntityID());
                                mapEntity.sendMessage(MESSAGE.ENTITY_DESELECTED);
                            }
                        }
                    }
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapMgr, float delta) {
        //We want the hitbox to be at the feet for a better fell
        updateBoundingBoxPosition(_nextEntityPosition);
        updatePortalLayerActivation(mapMgr);

        if(_isMouseSelectEnable){
            selectMapEntityCandidate(mapMgr);
            _isMouseSelectEnable = false;
        }
        clearMapEntityCandidateIfTooFarAway(mapMgr);

        if(_state == Entity.State.WALKING){
            calculateNextPosition(delta);
            updateBoundingBoxPosition(_nextEntityPosition);
            //updateNextBoundingBoxPosition(_nextEntityPosition);
            if( !isCollisionWithMapLayer(entity, mapMgr) &&
                    !isCollisionWithMapEntities(entity, mapMgr)){
                setNextPositionToCurrent(entity);

                Camera camera = mapMgr.getCamera();
                camera.position.set(_currentEntityPosition.x, _currentEntityPosition.y, 0f);
                camera.update();
            } else {
                updateBoundingBoxPosition(_currentEntityPosition);
            }

        } else{
            updateBoundingBoxPosition(_currentEntityPosition);
        }


    }

    private boolean updatePortalLayerActivation(MapManager mapMgr){
        MapLayer mapPortalLayer =  mapMgr.getPortalLayer();

        if( mapPortalLayer == null ){
            Gdx.app.debug(TAG, "Portal Layer doesn't exist!");
            return false;
        }

        Rectangle rectangle = null;

        for( MapObject object: mapPortalLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();

                if (_boundingBox.overlaps(rectangle) ){
                    String mapName = object.getName();
                    if( mapName == null ) {
                        return false;
                    }

                    mapMgr.setClosestPositionFromScaledUnits(_currentEntityPosition);
                    mapMgr.loadMap(MapFactory.MapType.valueOf(mapName));

                    _currentEntityPosition.x = mapMgr.getPlayerStartUnitScaled().x;
                    _currentEntityPosition.y = mapMgr.getPlayerStartUnitScaled().y;
                    _nextEntityPosition.x = mapMgr.getPlayerStartUnitScaled().x;
                    _nextEntityPosition.y = mapMgr.getPlayerStartUnitScaled().y;

                    Gdx.app.debug(TAG, "Portal Activated");
                    return true;
                }
            }
        }
        return false;
    }
}
