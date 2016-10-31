package com.packtpub.libgdx.bludbourne.map;

import com.badlogic.gdx.graphics.g2d.Batch;
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

    private static String _mapPath = "maps/town.tmx";
    private static String _townGuardWalking = "scripts/town_guard_walking.json";
    private static String _townBlacksmith = "scripts/town_blacksmith.json";
    private static String _townMage = "scripts/town_mage.json";
    private static String _townInnKeeper = "scripts/town_innkeeper.json";
    private static String _townFolk = "scripts/town_folk.json";

    TownMap(){
        super(MapFactory.MapType.TOWN, _mapPath);

        for(Vector2 position: _npcStartPositions){
            _mapEntities.add(initEntity(Entity.getEntityConfig(_townGuardWalking), position));
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
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);

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
}
