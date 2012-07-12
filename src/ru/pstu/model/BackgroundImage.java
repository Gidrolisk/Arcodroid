package ru.pstu.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import ru.pstu.GameView;

/**
 * @author : Ragnarok
 * @date : 11.07.12  12:49
 */
public class BackgroundImage extends DrawableObject {

    private boolean posInitiated;

    public BackgroundImage(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        posInitiated = false;
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        if (!posInitiated) {
            bmp = Bitmap.createScaledBitmap(bmp, maxWidth, maxHeight, false);
            posInitiated = true;
        }
        canvas.drawBitmap(bmp, 0, 0, null);
    }
}
