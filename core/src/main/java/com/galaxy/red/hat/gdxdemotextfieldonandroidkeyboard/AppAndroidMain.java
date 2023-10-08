package com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard;

import com.badlogic.gdx.Game;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard.OnScreenKeyboardApplicationBundle;

public class AppAndroidMain extends Game {
	private static final String TAG = AppAndroidMain.class.getCanonicalName();
	public static final String NAME = TAG;
	private AppAndroidScreen appScreen;
	public AppAndroidMain(OnScreenKeyboardApplicationBundle applicationBundle) {
		appScreen = new AppAndroidScreen(applicationBundle);
	}
	@Override
	public void create() {
		setScreen(appScreen);
	}

    @Override
    public void dispose() {
        super.dispose();
        appScreen.dispose();
    }
}
