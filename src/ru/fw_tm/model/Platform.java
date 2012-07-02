package ru.fw_tm.model;

import android.graphics.*;

/**
 * @author : Ragnarok
 * @date : 02.07.12  1:34
 */
public class Platform extends GameObject {
    private static final float HEIGHT = 30;
    private static final float WIDTH = 120;

    private Rect rect;

    public Platform(Bitmap bmp, float initialX, float initialY) {
        super(bmp, initialX, initialY);
        rect = new Rect((int) (x - WIDTH / 2), (int) (y - HEIGHT), (int) (x + WIDTH / 2), (int) y);
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        x = (maxWidth) / 2;
        y = (maxHeight) - (maxHeight / 50);

        rect.set((int) (x - WIDTH / 2), (int) (y - HEIGHT), (int) (x + WIDTH / 2), (int) y);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawRect(rect, paint);
    }
}
