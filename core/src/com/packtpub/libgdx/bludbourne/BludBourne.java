package com.packtpub.libgdx.bludbourne;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.packtpub.libgdx.bludbourne.screen.LoadProfileScreen;
import com.packtpub.libgdx.bludbourne.screen.MainGameScreen;
import com.packtpub.libgdx.bludbourne.screen.MainMenuScreen;
import com.packtpub.libgdx.bludbourne.screen.NewProfileScreen;

public class BludBourne extends Game {

	public static MainMenuScreen _mainMenuGameScreen;
	public static MainGameScreen _mainGameScreen;
	public static NewProfileScreen _newProfileScreen;
	public static LoadProfileScreen _loadProfileScreen;

	public static enum ScreenType{
		MAIN_MENU_SCREEN(1),
		MAIN_GAME_SCREEN(2),
		NEW_PROFILE_SCREEN(3),
		LOAD_PROFILE_TYPE(4);

		private int _value;

		ScreenType(int value){
			_value = value;
		}
	}

	@Override
	public void create() {
		mainGameScreenInitializer();

		//testGameScreenInitializer();
	}

	@Override
	public void dispose() {
		_mainGameScreen.dispose();
		_mainMenuGameScreen.dispose();
		_newProfileScreen.dispose();
		_loadProfileScreen.dispose();
	}

	public void switchScreen(ScreenType screenType){
		switch (screenType){
			case MAIN_GAME_SCREEN:
				setScreen(_mainGameScreen);
				break;
			case MAIN_MENU_SCREEN:
				setScreen(_mainMenuGameScreen);
				break;
			case NEW_PROFILE_SCREEN:
				setScreen(_newProfileScreen);
				break;
			case LOAD_PROFILE_TYPE:
				setScreen(_loadProfileScreen);
			default:
				break;
		}
	}

	private void mainGameScreenInitializer(){
		_mainMenuGameScreen = new MainMenuScreen(this);
		_mainGameScreen = new MainGameScreen(this);
		_newProfileScreen = new NewProfileScreen(this);
		_loadProfileScreen = new LoadProfileScreen(this);
		setScreen(_mainMenuGameScreen);
	}

	private void testGameScreenInitializer(){
		_mainGameScreen = new MainGameScreen(this);
		_mainMenuGameScreen = new MainMenuScreen(this);
		setScreen(_mainGameScreen);
	}
}
