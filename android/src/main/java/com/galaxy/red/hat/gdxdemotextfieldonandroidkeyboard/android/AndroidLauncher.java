package com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.android;

import android.graphics.Rect;
import android.os.Bundle;

import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.AppAndroidMain;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard.OnScreenKeyboardAndroidView;
import com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard.OnScreenKeyboardApplicationBundle;


public class AndroidLauncher extends AndroidApplication {

    private View rootView;
    private OnScreenKeyboardAndroidView androidView;

    private int width, height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.
        configureSoftKeyboard(configuration);
    }
    private void configureSoftKeyboard(AndroidApplicationConfiguration configuration) {
        rootView = this.getWindow().getDecorView().getRootView();
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        width = rect.width();
        height = rect.height();
        androidView = new OnScreenKeyboardAndroidView(width, height);
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);

                if(!(width == rect.width() && height == rect.height())) {
                    width = rect.width();
                    height = rect.height();
                    androidView.onSizeChange(width, height);
                }
            }

        });
        initialize(new AppAndroidMain(new OnScreenKeyboardApplicationBundle(androidView)), configuration);
    }


}
