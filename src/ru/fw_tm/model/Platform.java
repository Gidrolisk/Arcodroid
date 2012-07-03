package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import ru.fw_tm.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ragnarok
 * @date : 02.07.12  1:34
 * <p/>
 * Координаты платформы задаются центром её окружности.
 */
public class Platform extends GameObject {
    private static final int[] ZONES_ANGLE_LEFT = {
            60, 50, 40, 30, 20, 10, 1,
    };

    private static final int[] ZONES_ANGLE_RIGHT = {
            1, 10, 20, 30, 40, 50, 60
    };

    private final float HEIGHT;
    private final float WIDTH;

    private List<Rect> left, right;

    public Platform(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        HEIGHT = bmp.getHeight();
        WIDTH = bmp.getWidth();
        rect = new Rect((int) (loc.getX() - WIDTH / 2), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH / 2), (int) loc.getY());

        left = new ArrayList<Rect>();
        right = new ArrayList<Rect>();

        for (int i = 0; i < ZONES_ANGLE_LEFT.length; i++) {
            Rect r = new Rect();
            left.add(r);
        }
        for (int i = 0; i < ZONES_ANGLE_RIGHT.length; i++) {
            Rect r = new Rect();
            right.add(r);
        }
    }


    private void updatePos(int maxWidth, int maxHeight) {
        loc.update((maxWidth) / 2, maxHeight - (maxHeight / 50));

        int R = (int) (WIDTH / 2);
        int x = (int) (loc.getX() - R);
        int y = (int) (loc.getY());

        for (int i = 0; i < ZONES_ANGLE_LEFT.length; i++) {
            int x1 = x + R / ZONES_ANGLE_LEFT.length * (i + 1);
            int y1 = (int) (y - Math.sqrt(R * R - Math.pow(loc.getX() - x1, 2)));

            int x2 = x1 + R / ZONES_ANGLE_LEFT.length;
            int y2 = y;

            Rect rect1 = left.get(i);
            rect1.set(x1, y1, x2, y2);
        }

        x = (int) loc.getX();
        for (int i = 0; i < ZONES_ANGLE_RIGHT.length; i++) {
            int x1 = x + R / ZONES_ANGLE_RIGHT.length * i;
            int y1 = (int) (y - Math.sqrt(R * R - Math.pow(loc.getX() - x1, 2)));

            int x2 = x1 + R / ZONES_ANGLE_RIGHT.length;
            int y2 = y;

            Rect rect1 = right.get(i);
            rect1.set(x1, y1, x2, y2);
        }

        rect.set((int) (loc.getX() - WIDTH / 2), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH / 2), (int) loc.getY());
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        updatePos(maxWidth, maxHeight);

        canvas.drawBitmap(bmp, rect.left, rect.top, null);
    }
}
