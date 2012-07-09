package ru.fw_tm.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import ru.fw_tm.GameView;

/**
 * @author : Ragnarok
 * @date : 06.07.12  11:27
 */
public class Block extends GameObject {

    private boolean deleted;

    public Block(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        rect = new Rect((int) loc.getX(), (int) loc.getY(), (int) (loc.getX() + bmp.getWidth()), (int) (loc.getY() + bmp.getHeight()));
        deleted = false;

    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        if (!deleted) {
            canvas.drawBitmap(bmp, loc.getX(), loc.getY(), null);
        }
        //Paint p = new Paint();
        //p.setColor(Color.RED);
        //canvas.drawRect(rect, p);
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
