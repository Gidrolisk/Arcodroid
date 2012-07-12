package ru.pstu.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import ru.pstu.GameManager;
import ru.pstu.GameView;

/**
 * @author : Ragnarok
 * @date : 11.07.12  12:59
 */
public class LifeBar extends DrawableObject {
    private static final int MAX_LIFES = 3;
    private int lifeCount;

    public LifeBar(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        lifeCount = 3;
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        int y = GameManager.TOP_SHIFT / 2 - bmp.getHeight() / 2;
        int x = y;

        for (int i = 0; i < MAX_LIFES; i++) {
            Paint paint = new Paint();
            paint.setAlpha(i < lifeCount ? 255 : 120);
            canvas.drawBitmap(bmp, x, y, paint);
            x += bmp.getWidth() + 4;
        }
    }

    public void decreaseLife() {
        lifeCount--;
    }
}
