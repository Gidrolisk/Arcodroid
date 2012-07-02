package ru.fw_tm.model;

import android.graphics.*;
import ru.fw_tm.GameView;

/**
 * @author : Ragnarok
 * @date : 02.07.12  1:34
 */
public class Platform extends GameObject {
    private static final float HEIGHT = 30;
    private static final float WIDTH = 120;

    public Platform(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        rect = new Rect((int) (loc.getX() - WIDTH / 2), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH / 2), (int) loc.getY());
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {

        loc.update((maxWidth) / 2, maxHeight - (maxHeight / 50));

        rect.set((int) (loc.getX() - WIDTH / 2), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH / 2), (int) loc.getY());

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawRect(rect, paint);
    }
}
