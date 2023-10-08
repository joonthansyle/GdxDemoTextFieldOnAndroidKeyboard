package com.galaxy.red.hat.gdxdemotextfieldonandroidkeyboard.onscreenkeyboard;

public interface OnScreenKeyboardView {
    public void onSizeChange(float width, float height);
    public void addListener(SizeChangeListener sizeChangeListener);
    public float getWidth();
    public float getHeight();
}
