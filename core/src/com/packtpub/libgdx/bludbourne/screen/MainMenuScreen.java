package com.packtpub.libgdx.bludbourne.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.bludbourne.BludBourne;
import com.packtpub.libgdx.bludbourne.Utility;
import com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton.InventoryButtonObservable;
import com.packtpub.libgdx.bludbourne.gui.actor.inventoryButton.InventoryReturnButtonListener;
import com.packtpub.libgdx.bludbourne.multiplexer.GlobalMultiplexer;
import com.packtpub.libgdx.bludbourne.screen.viewport.GlobalViewport;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Created by ldalzotto on 06/11/2016.
 */
public class MainMenuScreen extends GlobalViewport implements Screen{

    private static final String TAG = MainMenuScreen.class.getSimpleName();

    private BludBourne _game;


    private OrthographicCamera _mainMenuCamera;
    private Viewport _viewport;

    private Stage _stage;
    private Table _table;

    public MainMenuScreen(BludBourne game){
        _game = game;
    }

    @Override
    public void show() {
        setupViewport(10,10);
        _mainMenuCamera = new OrthographicCamera();
        _mainMenuCamera.setToOrtho(false, VIEWPORT.physicalWidth, VIEWPORT.physicalHeight);
        _viewport = new ScreenViewport();
        _stage = new Stage(_viewport);
        _table = new Table();

        Gdx.input.setInputProcessor(null);
        GlobalMultiplexer.getInstance().getInputMultiplexer().addProcessor(0, _stage);
        Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());

        TextButton newGameButton = new TextButton("NEW GAME", Utility.MAINMENU_SKIN);
        TextButton loadButton = new TextButton("LOAD PROFILE", Utility.MAINMENU_SKIN);
        TextButton exitButton = new TextButton("EXIT", Utility.MAINMENU_SKIN);

        newGameButton.addListener(new NewGameButtonListener(_game));
        _table.add(newGameButton).padBottom(10f);
        _table.row();
        _table.add(loadButton).padBottom(10f);
        _table.row();
        _table.add(exitButton);

        //_table.setPosition(0, 0);
        _table.pack();
        _table.setVisible(true);
        _stage.addActor(_table);
    }

    @Override
    public void render(float delta) {
        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public class NewGameButtonListener extends InputListener{

        private final String TAG = NewGameButtonListener.class.getSimpleName();

        private BludBourne _game;

        public NewGameButtonListener(BludBourne game){
            _game = game;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.debug(TAG, "Switching to main game scrren.");
            GlobalMultiplexer.getInstance().getInputMultiplexer().removeProcessor(_stage);
            Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());
            _game.switchScreen(BludBourne.ScreenType.MAIN_GAME_SCREEN);
            return true;
        }
    }


}
