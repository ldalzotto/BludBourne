package com.packtpub.libgdx.bludbourne.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.packtpub.libgdx.bludbourne.*;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;
import com.packtpub.libgdx.bludbourne.gui.PlayerHUD;
import com.packtpub.libgdx.bludbourne.map.Map;
import com.packtpub.libgdx.bludbourne.map.MapManager;
import com.packtpub.libgdx.bludbourne.multiplexer.GlobalMultiplexer;

/**
 * Created by ldalzotto on 29/10/2016.
 */
public class MainGameScreen implements Screen {

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private OrthographicCamera _hudCamera = null;
    private static PlayerHUD _playerHud;

    private static class VIEWPORT {
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRation;
    }

    private OrthogonalTiledMapRenderer _mapRenderer = null;
    private OrthographicCamera _camera = null;
    private static MapManager _mapMgr;
    private Json _json;

    private Entity _player;

    public MainGameScreen() {
        _mapMgr = new MapManager();
        _json = new Json();
    }

    @Override
    public void show() {
        //_camera setup
        setupViewport(10,10);

        _hudCamera = new OrthographicCamera();
        _hudCamera.setToOrtho(false, VIEWPORT.physicalWidth, VIEWPORT.physicalHeight);

        _player = EntityFactory.getEntity(EntityFactory.EntityType.PLAYER);
        _playerHud = new PlayerHUD(_hudCamera, _player);

        Gdx.input.setInputProcessor(null);
        GlobalMultiplexer.getInstance().getInputMultiplexer().addProcessor(0, _playerHud.get_stage());
        Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());

        //get the current size
        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);

        _mapRenderer = new OrthogonalTiledMapRenderer(_mapMgr.getCurrentTiledMap(), Map.UNIT_SCALE);
        _mapRenderer.setView(_camera);

        _mapMgr.setCamera(_camera);

        Gdx.app.debug(TAG, "UnitScale value is: " + _mapRenderer.getUnitScale());

        _mapMgr.setPlayer(_player);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _mapRenderer.setView(_camera);

        if(_mapMgr.hasMapChanged()){
            _mapRenderer.setMap(_mapMgr.getCurrentTiledMap());
            _player.sendMessage(Component.MESSAGE.INIT_START_POSITION, _json.toJson(_mapMgr.getPlayerStartUnitScaled()));

            _camera.position.set(_mapMgr.getPlayerStartUnitScaled().x, _mapMgr.getPlayerStartUnitScaled().y, 0f);
            _camera.update();

            _mapMgr.setMapChanged(false);
        }
        _mapRenderer.render();
        _mapMgr.updateCurrentMapEntities(_mapMgr, _mapRenderer.getBatch(), delta);
        _player.update(_mapMgr, _mapRenderer.getBatch(), delta);

        _playerHud.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        _player.dispose();
        _mapRenderer.dispose();
    }

    private void setupViewport(int width, int height){
        //Makethe viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        //Current viewport dimensions
        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        //pixel dimensions of diplay
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        //aspect ratio for current viewport
        VIEWPORT.aspectRation = (VIEWPORT.virtualWidth/VIEWPORT.virtualHeight);

        //update viewport if there could be skewing
        if(VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRation) {
            //letterbox left and right
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        } else {
            //letterbox above and below
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight / VIEWPORT.physicalWidth);
        }

        Gdx.app.debug(TAG, "WorldRenderer: virtual: (" + VIEWPORT.virtualWidth + "," + VIEWPORT.virtualHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: viewport: (" + VIEWPORT.viewportWidth + "," + VIEWPORT.viewportHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: physical: (" + VIEWPORT.physicalWidth + "," + VIEWPORT.physicalHeight + ")");

    }
}
