package com.packtpub.libgdx.bludbourne.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.map.Map;
import com.packtpub.libgdx.bludbourne.map.MapFactory;
import com.packtpub.libgdx.bludbourne.map.MapManager;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class TopWorldMap extends Map {
    
    private static String _mapPath = "maps/topworld.tmx";
    
    TopWorldMap(){
        super(MapFactory.MapType.TOP_WORLD, _mapPath);
    }
    
    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta) {
        for (Entity entity :
                _mapEntities) {
            entity.update(mapMgr, batch, delta);
        }
    }
}
