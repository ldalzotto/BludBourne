package com.packtpub.libgdx.bludbourne.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.EntityConfig;
import com.packtpub.libgdx.bludbourne.EntityFactory;
import com.packtpub.libgdx.bludbourne.components.Component;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class TownMap extends Map {
    private static final String TAG = TownMap.class.getSimpleName();

    private static final String TYPEABLE_LAYER = "MAP_TYPEABLE_LAYER";

    private static String _mapPath = "maps/town.tmx";
    private static String _townGuardWalking = "scripts/town_guard_walking.json";
    private static String _townBlacksmith = "scripts/town_blacksmith.json";
    private static String _townMage = "scripts/town_mage.json";
    private static String _townInnKeeper = "scripts/town_innkeeper.json";
    private static String _townFolk = "scripts/town_folk.json";

    private static String _townTypeable = "scripts/town_typeable.json";
    private Array<Vector2> _typeablePositions;
    private MapLayer _mapTypeableLayer;

    TownMap(){
        super(MapFactory.MapType.TOWN, _mapPath);

        _mapTypeableLayer = _currentMap.getLayers().get(TYPEABLE_LAYER);

        _typeablePositions = getTypeablePositions(_mapTypeableLayer);

        for(Vector2 position: _npcStartPositions){
            _mapEntities.add(initEntity(Entity.getEntityConfig(_townGuardWalking), position));
        }
        for(Vector2 position: _typeablePositions){
            _mapEntities.add(initEntity(Entity.getEntityConfig(_townTypeable), position));
        }

        //SPecial cases
        _mapEntities.add(initSpecialEntity(Entity.getEntityConfig(_townBlacksmith)));
        _mapEntities.add(initSpecialEntity(Entity.getEntityConfig(_townMage)));
        _mapEntities.add(initSpecialEntity(Entity.getEntityConfig(_townInnKeeper)));

        //When we have multiple configs in one file
        Array<EntityConfig> configs = Entity.getEntityConfigs(_townFolk);
        for (EntityConfig entityConfig :
                configs) {
            _mapEntities.add(initSpecialEntity(entityConfig));
        }
    }

    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta) {
        for (int i=0; i < _mapEntities.size; i++){
            _mapEntities.get(i).update(mapMgr, batch, delta);
        }
    }

    private Entity initEntity(EntityConfig entityConfig, Vector2 position){
        Entity entity;

        if(entityConfig.getEntityID().contains("TYPE")){
            entity = EntityFactory.getEntity(EntityFactory.EntityType.TYPEABLE);
            entity.setEntityConfig(entityConfig);
            entity.sendMessage(Component.MESSAGE.LOAD_TYPING_BOX, _json.toJson(entity.getEntityConfig()));
        } else {
            entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);
        }

        entity.setEntityConfig(entityConfig);

        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, _json.toJson(entity.getEntityConfig()));
        entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, _json.toJson(position));
        entity.sendMessage(Component.MESSAGE.INIT_STATE, _json.toJson(entity.getEntityConfig().getState()));
        entity.sendMessage(Component.MESSAGE.INIT_DIRECTION, _json.toJson(entity.getEntityConfig().getDirection()));

        return entity;
    }

    private Entity initSpecialEntity(EntityConfig entityConfig){
        Vector2 position = new Vector2(0,0);
        if(_specialNPCStartPositions.containsKey(entityConfig.getEntityID())){
            position = _specialNPCStartPositions.get(entityConfig.getEntityID());
        }
        return initEntity(entityConfig, position);
    }

    private Array<Vector2> getTypeablePositions(MapLayer mpLayr){
        Array<Vector2> typeablePositions = new Array<Vector2>();
        for (MapObject object:
             mpLayr.getObjects()) {
            if(object instanceof RectangleMapObject){
                float x = ((RectangleMapObject)object).getRectangle().getX();
                float y = ((RectangleMapObject)object).getRectangle().getY();

                //scale by the unit to convert from map coordinates
                x *= UNIT_SCALE;
                y *= UNIT_SCALE;

                typeablePositions.add(new Vector2(x,y));
            }
        }
        return typeablePositions;
    }
}
