package ru.pstu.model;

import android.graphics.*;
import ru.pstu.GameView;

/**
 * @author : Ragnarok
 * @date : 11.07.12  11:53
 */
public class Text extends DrawableObject {
    private String text;

    public Text(GameView gameView, Bitmap bmp, float initialX, float initialY) {
        super(gameView, bmp, initialX, initialY);
    }

    @Override
    public void draw(Canvas canvas, int maxWidth, int maxHeight) {
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(50);
        p.setTypeface(Typeface.DEFAULT_BOLD);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, maxWidth / 2, maxHeight / 2, p);
    }

    public void setText(String text) {
        this.text = text;
    }
}
