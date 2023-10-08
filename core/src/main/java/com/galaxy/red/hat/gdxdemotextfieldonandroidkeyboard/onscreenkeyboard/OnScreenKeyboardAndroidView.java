package com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard;

import java.util.ArrayList;

public class OnScreenKeyboardAndroidView implements OnScreenKeyboardView{
    private final ArrayList<SizeChangeListener> listeners = new ArrayList<SizeChangeListener>();
    private float width, height;
    public OnScreenKeyboardAndroidView(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addListener(SizeChangeListener listener) {
        listeners.add(listener);
    }

    public void onSizeChange(float width, float height) {
        this.width = width;
        this.height = height;
        for(SizeChangeListener listener : listeners)
            listener.onSizeChange(width, height);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
