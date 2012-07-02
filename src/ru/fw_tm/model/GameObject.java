package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import ru.fw_tm.GameView;
import ru.fw_tm.Location;

/**
 * @author : Ragnarok
 * @date : 02.07.12  0:51
 */
public abstract class GameObject {
    protected GameView gameView;
    protected Bitmap bmp;
    protected Location loc;
    Rect rect;

    public GameObject(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        this.gameView = gameView;
        this.bmp = bmp;
        rect = new Rect();
        loc = new Location(initialX, initialY);
    }

    public abstract void draw(Canvas canvas, int maxWidth, int maxHeight);
}
