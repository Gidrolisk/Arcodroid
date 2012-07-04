package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import ru.fw_tm.GameManager;
import ru.fw_tm.GameView;
import ru.fw_tm.Location;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author : Ragnarok
 * @date : 02.07.12  0:40
 */
public class Ball extends GameObject {
    private static final int TILL_SIZE = 3; // "Длина" хвоста

    private float xSpeed, ySpeed;

    private Queue<Location> coords;

    public Ball(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        xSpeed = GameManager.SPEED;
        ySpeed = GameManager.SPEED;
        coords = new ArrayDeque<Location>(TILL_SIZE);
    }

    private void updatePos(float maxWidth, float maxHeight) {
        // расчет координат следующей точки
        if (loc.getX() + xSpeed <= 0) {
            xSpeed = GameManager.SPEED;
        }
        if (loc.getX() + xSpeed >= maxWidth - bmp.getWidth()) {
            xSpeed = -GameManager.SPEED;
        }
        if (loc.getY() + ySpeed <= 0) {
            ySpeed = GameManager.SPEED;
        }
        if (loc.getY() + ySpeed >= maxHeight - bmp.getHeight()) {
            // Проигрыш, если шар коснется этой точки
            ySpeed = -GameManager.SPEED;
        }

        // Расчет отскока от платформы
        Location nextLoc = loc.clone().update(loc.getX() + xSpeed, loc.getY() + ySpeed);
        Rect nextRect = new Rect(rect);
        nextRect.set((int) nextLoc.getX(), (int) nextLoc.getY(), (int) nextLoc.getX() + bmp.getWidth(), (int) nextLoc.getY() + bmp.getHeight());
        Platform.IntersectInfo info = gameView.platform.intersect(nextRect);
        if (info != null) {
            double a = Math.atan(Math.tan(ySpeed / xSpeed));
            double b = info.getAngle();
            xSpeed = (float) (xSpeed * Math.cos(a) / Math.cos(b));
            ySpeed = (float) (ySpeed * Math.cos(a) / Math.cos(b));
        }

        // Обновление "Хвоста"
        if (coords.size() == TILL_SIZE)
            coords.poll();
        coords.add(loc.clone());

        loc.update(loc.getX() + xSpeed, loc.getY() + ySpeed);
        rect.set((int) loc.getX(), (int) loc.getY(), (int) loc.getX() + bmp.getWidth(), (int) loc.getY() + bmp.getHeight());
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        // Обновляем позицию шарика
        updatePos(maxWidth, maxHeight);

        // Рисуем шарик
        Paint paint = new Paint();
        canvas.drawBitmap(bmp, (int) loc.getX(), (int) loc.getY(), paint);

        // Рисуем "Хвост" (c конца)
        Location[] prevLocs = coords.toArray(new Location[coords.size()]);

        for (int i = 0; i < prevLocs.length; i++) {
            Location prevLoc = prevLocs[i];
            paint.setAlpha(255 / (prevLocs.length - i + 1));
            canvas.drawBitmap(bmp, prevLoc.getX(), prevLoc.getY(), paint);
        }
    }
}
