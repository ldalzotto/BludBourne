package com.packtpub.libgdx.bludbourne.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.bludbourne.BludBourne;
import com.packtpub.libgdx.bludbourne.Utility;
import com.packtpub.libgdx.bludbourne.multiplexer.GlobalMultiplexer;
import com.packtpub.libgdx.bludbourne.profile.ProfileManager;
import com.packtpub.libgdx.bludbourne.screen.viewport.GlobalViewport;

/**
 * Created by ldalzotto on 11/11/2016.
 */
public class LoadProfileScreen extends GlobalViewport implements Screen{

    private static final String TAG = LoadProfileScreen.class.getSimpleName();

    private BludBourne _game;

    private OrthographicCamera _newProfileCamera;
    private Viewport _viewport;

    private Stage _stage;
    private Table _table;

    public LoadProfileScreen(BludBourne game){
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

        Array<String> listOfProfile = ProfileManager.getInstance().getProfileList();

        for (String profileName :
                listOfProfile) {
            TextButton textButton = new TextButton(profileName, Utility.STATUSUI_SKIN);
            textButton.addListener(new LoadingGameProfileEvent(_game));
            _table.add(textButton);
            _table.row();
        }

        _table.pack();
        _table.setVisible(true);
        ScrollPane scrollPane = new ScrollPane(_table);


        _stage.addActor(scrollPane);

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
        GlobalMultiplexer.getInstance().getInputMultiplexer().removeProcessor(_stage);
        Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());
    }

    public class LoadingGameProfileEvent extends InputListener {

        private final String TAG = MainMenuScreen.NewGameButtonListener.class.getSimpleName();

        private BludBourne _game;
        private String _profileName;

        public LoadingGameProfileEvent(BludBourne game){
            _game = game;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            _profileName = ((Label)event.getTarget()).getText().toString();
            Gdx.app.debug(TAG, "Loading profile " + _profileName);

            ProfileManager.getInstance().setCurrentProfile(_profileName);
            ProfileManager.getInstance().loadProfile();

            GlobalMultiplexer.getInstance().getInputMultiplexer().removeProcessor(_stage);
            Gdx.input.setInputProcessor(GlobalMultiplexer.getInstance().getInputMultiplexer());
            _game.switchScreen(BludBourne.ScreenType.MAIN_GAME_SCREEN);

            return true;
        }


    }
}
