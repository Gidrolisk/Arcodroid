package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import ru.fw_tm.GameManager;
import ru.fw_tm.GameView;

/**
 * @author : Ragnarok
 * @date : 09.07.12  15:34
 */
public class Line extends GameObject {
    public Line(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawLine(0, GameManager.TOP_SHIFT-2, maxWidth, GameManager.TOP_SHIFT-2, paint);
    }
}
