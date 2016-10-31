package com.packtpub.libgdx.bludbourne;


import com.badlogic.gdx.Game;
import com.packtpub.libgdx.bludbourne.screen.MainGameScreen;

public class BludBourne extends Game {

	public static final MainGameScreen _mainGameScreen = new MainGameScreen();

	@Override
	public void create() {
		setScreen(_mainGameScreen);
	}

	@Override
	public void dispose() {
		_mainGameScreen.dispose();
	}
}
