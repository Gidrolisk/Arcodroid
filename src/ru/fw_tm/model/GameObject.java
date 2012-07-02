package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * @author : Ragnarok
 * @date : 02.07.12  0:51
 */
public abstract class GameObject {
    protected Bitmap bmp;
    protected float x, y;

    public GameObject(Bitmap bmp, float initialX, float initialY) {
        this.bmp = bmp;
        x = initialX;
        y = initialY;
    }

    public abstract void draw(Canvas canvas, int maxWidth, int maxHeight);
}
