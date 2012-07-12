package ru.pstu.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import ru.pstu.GameManager;
import ru.pstu.GameView;
import ru.pstu.level.AbstractLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ragnarok
 * @date : 02.07.12  1:34
 * <p/>
 * Координаты платформы задаются центром её окружности.
 */
public class Platform extends DrawableObject {
    private final int HEIGHT;
    private final int WIDTH;

    private List<Rect> rects;
    public boolean posInitialized;

    public int xSpeed = 0;

    public Platform(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        HEIGHT = bmp.getHeight();
        WIDTH = bmp.getWidth();
        rect = new Rect(0, 0, 0, 0);

        rects = new ArrayList<Rect>(WIDTH);
        for (int i = 0; i < WIDTH; i++) {
            rects.add(new Rect(0, 0, 0, 0));
        }

        posInitialized = false;
    }

    public void updatePos(int maxWidth, int maxHeight) {
        int direction = (int) Math.floor(gameView.getAccelerometer().getX());

        if (direction < 0) {
            xSpeed = (int) GameManager.PLATFORM_SPEED;
        } else if (direction > 0) {
            xSpeed = (int) -GameManager.PLATFORM_SPEED;
        } else {
            xSpeed = 0;
        }

        if (loc.getX() + xSpeed < 0) {
            xSpeed = 0;
        }
        if (loc.getX() + xSpeed > maxWidth - WIDTH) {
            xSpeed = 0;
        }
        loc.update(loc.getX() + xSpeed, maxHeight - GameManager.BOTTOM_SHIFT);

        rect.set((int) loc.getX(), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH), (int) loc.getY());

        for (int x0 = 0; x0 < WIDTH; x0++) {
            Rect rect1 = rects.get(x0);
            rect1.set((int) (loc.getX() + x0), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + x0) + 1, (int) loc.getY());
        }
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        if (gameView.getCurrentLevel().getLevelState() == AbstractLevel.LevelState.READY && !posInitialized) {
            loc.update(maxWidth / 2 - WIDTH / 2, maxHeight - GameManager.BOTTOM_SHIFT);
            rect.set((int) loc.getX(), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH), (int) loc.getY());
            posInitialized = true;
        }
        if (gameView.getCurrentLevel().getLevelState() == AbstractLevel.LevelState.GO)
            updatePos(maxWidth, maxHeight);

        canvas.drawBitmap(bmp, rect.left, rect.top, null);
    }

    public IntersectInfo intersect(Rect ballRect) {
        IntersectInfo info = null;
        for (int i = 0; i < rects.size(); i++) {
            Rect rect1 = rects.get(i);
            if (rect1.intersect(ballRect)) {
                int j;
                for (j = i; j < rects.size() && rects.get(j).intersect(ballRect); j++) ;
                int x = (j - i) / 2;
                int d = Math.abs(WIDTH / 2 - x);
                info = new IntersectInfo(d);
                break;
            }
        }
        return info;
    }

    public class IntersectInfo {
        private final float distance;

        public IntersectInfo(float distance) {
            this.distance = distance;
        }

        public float getDistance() {
            return distance;
        }
    }
}
