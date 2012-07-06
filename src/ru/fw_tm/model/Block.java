package ru.fw_tm.model;

import android.graphics.*;
import ru.fw_tm.GameView;

/**
 * @author : Ragnarok
 * @date : 06.07.12  11:27
 */
public class Block extends GameObject {

    public Block(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
        rect = new Rect((int) loc.getX(), (int) loc.getY(), (int) (loc.getX() + bmp.getWidth()), (int) (loc.getY() + bmp.getHeight()));


    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        canvas.drawBitmap(bmp, loc.getX(), loc.getY(), null);
        //Paint p = new Paint();
        //p.setColor(Color.RED);
        //canvas.drawRect(rect, p);
    }
}
