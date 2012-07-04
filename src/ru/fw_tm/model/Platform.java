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
    private static final float[] ZONES_ANGLE = {
            60, 50, 40, 30, 20, 10, 1
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

        for (int i = 0; i < ZONES_ANGLE.length; i++) {
            Rect r = new Rect();
            left.add(r);
        }
        left.add(new Rect());
        for (int i = 0; i < ZONES_ANGLE.length; i++) {
            Rect r = new Rect();
            right.add(r);
        }
        right.add(new Rect());
    }


    private void updatePos(int maxWidth, int maxHeight) {
        loc.update((maxWidth) / 2, maxHeight - (maxHeight / 50));

        int R = (int) (WIDTH / 2);
        int x = (int) (loc.getX() - R);
        int y = (int) (loc.getY());

        int x1 = x, y1 = y;
        int x2 = 0, y2 = 0;

        for (int i = 0; i < ZONES_ANGLE.length - 1; i++) {
            x2 = x + R / (ZONES_ANGLE.length - 1) * (i + 1);
            y2 = y;

            y1 = (int) (y - Math.sqrt(R * R - Math.pow(loc.getX() - x2, 2)));

            Rect rect1 = left.get(i);
            rect1.set(x1, y1, x2, y2);

            x1 = x2;
        }

        if (loc.getX() - x1 > 0) {
            Rect rect2 = left.get(ZONES_ANGLE.length);
            x1 = x2;
            y1 = (int) (y - Math.sqrt(R * R - Math.pow(loc.getX() - x1, 2)));

            x2 = (int) loc.getX();
            y2 = (int) (loc.getY());

            rect2.set(x1, y1, x2, y2);
        }

        x = (int) loc.getX() + R;
        x1 = x;
        y1 = y;

        for (int i = 0; i < ZONES_ANGLE.length - 1; i++) {
            x2 = x - R / (ZONES_ANGLE.length - 1) * (i + 1);
            y2 = (int) (y - Math.sqrt(R * R - Math.pow(x2 - loc.getX(), 2)));

            Rect rect1 = right.get(ZONES_ANGLE.length - i - 1);
            rect1.set(x2, y2, x1, y1);

            x1 = x2;
        }

        if (x2 - loc.getX() > 0) {
            Rect rect2 = right.get(ZONES_ANGLE.length);

            x1 = x2;
            y1 = y;

            x2 = (int) loc.getX();
            y2 = (int) (loc.getY() - R);

            rect2.set(x2, y2, x1, y1);
        }

        rect.set((int) (loc.getX() - WIDTH / 2), (int) (loc.getY() - HEIGHT), (int) (loc.getX() + WIDTH / 2), (int) loc.getY());
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        updatePos(maxWidth, maxHeight);


        // For debug
        /*
        Paint p = new Paint();
        p.setColor(Color.BLUE);

        for (Rect r : left) {
            canvas.drawRect(r, p);
        }

        p.setColor(Color.GREEN);
        for (Rect r : right) {
            canvas.drawRect(r, p);
        }
        */

        canvas.drawBitmap(bmp, rect.left, rect.top, null);
    }

    public IntersectInfo intersect(Rect ballRect) {
        IntersectInfo info = null;
        for (int i = left.size() - 1; i >= 0; i--) {
            Rect rect1 = left.get(i);
            if (rect1.intersect(ballRect)) {
                info = new IntersectInfo(ZONES_ANGLE[i]);
            }
        }
        for (int i = 0; i < right.size(); i++) {
            Rect rect1 = right.get(i);
            if (rect1.intersect(ballRect)) {
                info = new IntersectInfo(ZONES_ANGLE[i]);
            }
        }
        return info;
    }

    public class IntersectInfo {
        private final float angle;

        public IntersectInfo(float angle) {
            this.angle = angle;
        }

        public float getAngle() {
            return angle;
        }
    }
}
