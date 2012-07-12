package ru.pstu.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import ru.pstu.GameManager;
import ru.pstu.GameView;
import ru.pstu.Location;
import ru.pstu.level.AbstractLevel;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author : Ragnarok
 * @date : 02.07.12  0:40
 */
public class Ball extends DrawableObject {
    private static final int TILL_SIZE = 3; // "Длина" хвоста

    private float xSpeed, ySpeed;

    private Queue<Location> coords;

    public boolean posInitialized;

    public Ball(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        xSpeed = GameManager.SPEED_X;
        ySpeed = GameManager.SPEED_Y;
        coords = new ArrayDeque<Location>(TILL_SIZE);
        posInitialized = false;
    }

    private void updatePos(float maxWidth, float maxHeight) {
        // расчет координат следующей точки
        if (loc.getX() + xSpeed <= 0) {
            xSpeed = Math.abs(xSpeed);
        }
        if (loc.getX() + xSpeed >= maxWidth - bmp.getWidth()) {
            xSpeed = -Math.abs(xSpeed);
        }
        if (loc.getY() + ySpeed <= GameManager.TOP_SHIFT) {
            ySpeed = Math.abs(ySpeed);
        }
        if (loc.getY() + ySpeed >= maxHeight - bmp.getHeight()) {
            // Проигрыш, если шар коснется этой точки
            //ySpeed = -Math.abs(ySpeed);
            gameView.getCurrentLevel().onBallLost();
        }

        // Расчет отскока от блока
        for (Block block : gameView.getCurrentLevel().getBlocks()) {
            if (block.rect.intersect(rect)) {
                gameView.getCurrentLevel().deleteBlock(block);
                ySpeed = -ySpeed;
                break;
            }
        }

        // Расчет отскока от платформы
        Location nextLoc = loc.clone().update(loc.getX() + xSpeed, loc.getY() + ySpeed);
        Rect nextRect = new Rect(rect);
        nextRect.set((int) nextLoc.getX(), (int) nextLoc.getY(), (int) nextLoc.getX() + bmp.getWidth(), (int) nextLoc.getY() + bmp.getHeight());
        Platform.IntersectInfo info = gameView.getCurrentLevel().getPlatform().intersect(nextRect);
        if (info != null) {
            float SPD = GameManager.SPEED_X * GameManager.SPEED_X + GameManager.SPEED_Y * GameManager.SPEED_Y;

            float alpha = info.getDistance();
            if (xSpeed <= 0)
                xSpeed = -(float) (Math.tan(alpha) * Math.sqrt(SPD / (1 + Math.pow(Math.tan(alpha), 2))));
            else
                xSpeed = (float) (Math.tan(alpha) * Math.sqrt(SPD / (1 + Math.pow(Math.tan(alpha), 2))));

            ySpeed = -(float) Math.sqrt(SPD / (1 + Math.pow(Math.tan(alpha), 2)));
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
        if (gameView.getCurrentLevel().getLevelState() == AbstractLevel.LevelState.READY && !posInitialized) {
            coords.clear();
            Platform platform = gameView.getCurrentLevel().getPlatform();
            int width = gameView.getWidth();
            int height = gameView.getHeight();
            int x = width / 2 - bmp.getWidth() / 2;
            int y = height - (GameManager.BOTTOM_SHIFT + bmp.getHeight() + platform.bmp.getHeight());
            loc.update(x, y);
            posInitialized = true;
        }

        // Обновляем позицию шарика
        if (gameView.getCurrentLevel().getLevelState() == AbstractLevel.LevelState.GO)
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
