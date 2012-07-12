package ru.pstu.level;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ru.pstu.GameView;
import ru.pstu.R;

/**
 * Created with IntelliJ IDEA.
 * User: Gidrolisk
 * Date: 09.07.12
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class Level4 extends AbstractLevel {
    @Override
    public void loadGameObjects(GameView view, Resources resources) {
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.block);
        bmp = Bitmap.createScaledBitmap(bmp, BLOCK_WIDTH, BLOCK_HEIGHT, false);
        // line 1
        addBlocks(bmp, 1, 1, 2, 3, 6, 7);
        // line 2
        addBlocks(bmp, 2, 1, 2, 5, 6);
        // line 3
        addBlocks(bmp, 3, 1, 4, 5);
        // line 4
        addBlocks(bmp, 4, 3, 4);
        // line 5
        addBlocks(bmp, 5, 2, 3, 7, 8);
        // line 6
        addBlocks(bmp, 6, 1, 2, 6, 7);
        // line 7
        addBlocks(bmp, 7, 5, 6);
        // line 8
        addBlocks(bmp, 8, 4, 5, 8);
        // line 9
        addBlocks(bmp, 9, 3, 4, 7, 8);
        // line 10
        addBlocks(bmp, 10, 2, 3, 6, 7, 8);
    }
}
