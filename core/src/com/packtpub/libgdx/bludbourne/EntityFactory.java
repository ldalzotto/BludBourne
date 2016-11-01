package com.packtpub.libgdx.bludbourne;

import com.badlogic.gdx.utils.Json;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;
import com.packtpub.libgdx.bludbourne.components.comNPC.NPCGraphicsComponent;
import com.packtpub.libgdx.bludbourne.components.comNPC.NPCInputComponent;
import com.packtpub.libgdx.bludbourne.components.comNPC.NPCPhysicsComponent;
import com.packtpub.libgdx.bludbourne.components.comPlayer.PlayerGraphicsComponent;
import com.packtpub.libgdx.bludbourne.components.comPlayer.PlayerInputComponent;
import com.packtpub.libgdx.bludbourne.components.comPlayer.PlayerPhysicsComponent;
import com.packtpub.libgdx.bludbourne.components.comTypeable.TypeableGraphicsComponent;
import com.packtpub.libgdx.bludbourne.components.comTypeable.TypeableInputComponent;


/**
 * Created by ldalzotto on 30/10/2016.
 */
public class EntityFactory {

    private static Json _json = new Json();

    public static enum EntityType{
        PLAYER, DEMO_PLAYER, NPC, TYPEABLE
    }

    public static String PLAYER_CONFIG = "scripts/player.json";

    static public Entity getEntity(EntityType entityType){
        Entity entity = null;
        switch (entityType){
            case PLAYER:
                entity = new Entity(new PlayerInputComponent(),
                                new PlayerPhysicsComponent(),
                                new PlayerGraphicsComponent());
                entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.PLAYER_CONFIG));
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, _json.toJson(entity.getEntityConfig()));
                return entity;
            case DEMO_PLAYER:
                entity = new Entity(new NPCInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                return entity;
            case NPC:
                entity = new Entity(new NPCInputComponent(),
                        new NPCPhysicsComponent(),
                        new NPCGraphicsComponent());
                return entity;
            case TYPEABLE:
                entity = new Entity(new TypeableInputComponent(),
                        new NPCPhysicsComponent(),
                        new TypeableGraphicsComponent());
                return entity;
            default:
                return null;
        }
    }

}
