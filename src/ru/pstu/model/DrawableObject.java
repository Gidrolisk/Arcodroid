package ru.pstu.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import ru.pstu.GameView;
import ru.pstu.Location;

/**
 * @author : Ragnarok
 * @date : 02.07.12  0:51
 */
public abstract class DrawableObject {
    protected GameView gameView;
    protected Bitmap bmp;
    protected Location loc;

    Rect rect;

    public DrawableObject(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        this.gameView = gameView;
        this.bmp = bmp;
        rect = new Rect();
        loc = new Location(initialX, initialY);
    }

    public abstract void draw(Canvas canvas, int maxWidth, int maxHeight);
}
