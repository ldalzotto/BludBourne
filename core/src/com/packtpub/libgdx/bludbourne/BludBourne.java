package com.packtpub.libgdx.bludbourne;


import com.badlogic.gdx.Game;
import com.packtpub.libgdx.bludbourne.screen.MainGameScreen;
import com.packtpub.libgdx.bludbourne.screen.MainMenuScreen;

public class BludBourne extends Game {

	public static MainMenuScreen _mainMenuGameScreen;
	public static MainGameScreen _mainGameScreen;

	public static enum ScreenType{
		MAIN_MENU_SCREEN(1),
		MAIN_GAME_SCREEN(2);

		private int _value;

		ScreenType(int value){
			_value = value;
		}
	}

	@Override
	public void create() {
		//mainGameScreenInitializer();

		testGameScreenInitializer();
	}

	@Override
	public void dispose() {
		_mainGameScreen.dispose();
		_mainMenuGameScreen.dispose();
	}

	public void switchScreen(ScreenType screenType){
		switch (screenType){
			case MAIN_GAME_SCREEN:
				setScreen(_mainGameScreen);
			case MAIN_MENU_SCREEN:
				return;
		}
	}

	private void mainGameScreenInitializer(){
		_mainMenuGameScreen = new MainMenuScreen(this);
		_mainGameScreen = new MainGameScreen(this);
		setScreen(_mainMenuGameScreen);
	}

	private void testGameScreenInitializer(){
		_mainGameScreen = new MainGameScreen(this);
		_mainMenuGameScreen = new MainMenuScreen(this);
		setScreen(_mainGameScreen);
	}
}
