package com.packtpub.libgdx.bludbourne.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.packtpub.libgdx.bludbourne.Entity;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class CastleDoomMap extends Map {

    private static final String _mapPath = "maps/castle_of_doom.tmx";

    CastleDoomMap(){
        super(MapFactory.MapType.CASTLE_OF_DOOM, _mapPath);
    }

    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta) {
        for( Entity entity : _mapEntities ){
            entity.update(mapMgr, batch, delta);
        }
    }
}
