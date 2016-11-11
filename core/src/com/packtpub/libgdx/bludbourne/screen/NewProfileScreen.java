package com.packtpub.libgdx.bludbourne.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.bludbourne.BludBourne;
import com.packtpub.libgdx.bludbourne.Utility;
import com.packtpub.libgdx.bludbourne.multiplexer.GlobalMultiplexer;
import com.packtpub.libgdx.bludbourne.profile.ProfileManager;
import com.packtpub.libgdx.bludbourne.screen.viewport.GlobalViewport;

/**
 * Created by ldalzotto on 10/11/2016.
 */
public class NewProfileScreen extends GlobalViewport implements Screen {

    private static final String TAG = NewProfileScreen.class.getSimpleName();

    private BludBourne _game;

    private OrthographicCamera _newProfileCamera;
    private Viewport _viewport;

    private Stage _stage;
    private Table _table;

    public NewProfileScreen(BludBourne game){
        _game = game;
    }

    @Override
    public void show() {
        setupViewport(10,10);
        _newProfileCamera = new OrthographicCamera();
        _newProfileCamera.setToOrtho(false, VIEWPORT.physicalWidth, VIEWPORT.physicalHeight);
        _viewport = new ScreenViewport();
        _stage = new Stage(_viewport);
        _table = new Table();

        Gdx.input.setInputProcessor(null);
        GlobalMultiplexer.getInstance().getInputMultiplexer().addProcessor(0, _stage);
        Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());

        //TextButton newGameButton = new TextButton("Create profile", Utility.MAINMENU_SKIN);
        TextField profleTextField = new TextField("enter profile", Utility.STATUSUI_SKIN);
        profleTextField.addListener(new EnteringGameEventListener(_game));
        //newGameButton.addListener(new EnteringGameEventListener(_game));

        //_table.add(newGameButton).padBottom(10f);
        _table.row();
        _table.add(profleTextField);

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

    public class EnteringGameEventListener implements EventListener {

        private final String TAG = MainMenuScreen.NewGameButtonListener.class.getSimpleName();

        private BludBourne _game;
        private String _profileName;

        public EnteringGameEventListener(BludBourne game){
            _game = game;
        }

        @Override
        public boolean handle(Event event) {
            if(event instanceof InputEvent){
                InputEvent inputEvent = (InputEvent) event;
                if(inputEvent.getType().equals(InputEvent.Type.keyDown)){
                    if(inputEvent.getKeyCode() == Input.Keys.ENTER) {
                        _profileName = ((TextField)inputEvent.getTarget()).getText();
                        Gdx.app.debug(TAG, "Creating new profile " + _profileName);

                        ProfileManager.getInstance().initiateNewProfile(_profileName);

                        GlobalMultiplexer.getInstance().getInputMultiplexer().removeProcessor(_stage);
                        Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());
                        _game.switchScreen(BludBourne.ScreenType.MAIN_GAME_SCREEN);
                    }
                }
            }
            return false;
        }

    }
}
