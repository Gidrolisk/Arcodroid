package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import ru.fw_tm.GameManager;

import java.util.PriorityQueue;

/**
 * @author : Ragnarok
 * @date : 02.07.12  0:40
 */
public class Ball extends GameObject {
    private float xSpeed, ySpeed;
    private int alfa;

    private PriorityQueue<Float[]> coords;

    public Ball(Bitmap bmp, float initialX, float initialY) {
        super(bmp, initialX, initialY);
        x = initialX;
        y = initialY;
        xSpeed = GameManager.SPEED;
        ySpeed = GameManager.SPEED;
        coords = new PriorityQueue<Float[]>(4);
    }

    private void updatePos(float maxWidth, float maxHeight) {
        if (x >= maxWidth - bmp.getWidth()) {
            xSpeed = -GameManager.SPEED;
        }
        if (x <= 0) {
            xSpeed = GameManager.SPEED;
        }
        if (y >= maxHeight - bmp.getHeight()) {
            ySpeed = -GameManager.SPEED;
        }
        if (y <= 0) {
            ySpeed = GameManager.SPEED;
        }

        if (coords.size() == 4)
            coords.poll();
        coords.add(new Float[]{x, y});

        x += xSpeed;
        y += ySpeed;
    }

    public void setAlfa(int alfa) {
        this.alfa = alfa;
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        updatePos(maxWidth, maxHeight);

        Paint paint = new Paint();
        paint.setAlpha(alfa);
        canvas.drawBitmap(bmp, (int) x, (int) y, paint);

        // Рисуем "Хвост"

        Float[][] cds = coords.toArray(new Float[coords.size()][2]);
        for (int i = 0; i < cds.length; i++) {
            Float[] cd = cds[i];
            paint.setAlpha(255 / (i + 2));
            canvas.drawBitmap(bmp, cd[0], cd[1], paint);
        }
    }
}
