package com.packtpub.libgdx.bludbourne.components.comNPC;

import com.badlogic.gdx.math.Vector2;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.comAbstract.PhysicsComponent;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;
import com.packtpub.libgdx.bludbourne.map.MapManager;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class NPCPhysicsComponent extends PhysicsComponent {

    private static final String TAG = NPCPhysicsComponent.class.getSimpleName();

    private Entity.State _state;

    public NPCPhysicsComponent(){
        _bouBoundingBoxLocation = BoundingBoxLocation.CENTER;
        initBoundingBox(0.4f, 0.15f);
    }

    @Override
    public void dispose(){
    }

    @Override
    public void receiveMessage(String message) {
        //Gdx.app.debug(TAG, "Got message " + message);
        String[] string = message.split(Component.MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        //Specifically for messages with 1 object payload
        if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                _currentEntityPosition = _json.fromJson(Vector2.class, string[1]);
                _nextEntityPosition.set(_currentEntityPosition.x, _currentEntityPosition.y);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                _state = _json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())){
                _state = _json.fromJson(Entity.State.class, string[1]);
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapMgr, float delta) {
        updateBoundingBoxPosition(_nextEntityPosition);

        if( _state == Entity.State.IMMOBILE ) {
            updateBoundingBoxPosition(_currentEntityPosition);
            return;
        }

        if (    !isCollisionWithMapLayer(entity, mapMgr) &&
                !isCollisionWithMapEntities(entity, mapMgr) &&
                _state == Entity.State.WALKING){
            setNextPositionToCurrent(entity);
        } else {
            updateBoundingBoxPosition(_currentEntityPosition);
        }
        calculateNextPosition(delta);
    }

    @Override
    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapMgr){
        //Test against player
        if( isCollision(entity, mapMgr.getPlayer()) ) {
            return true;
        }

        if( super.isCollisionWithMapEntities(entity, mapMgr) ){
            return true;
        }

        return false;
    }
}
