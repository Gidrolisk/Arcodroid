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
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class Level11 extends AbstractLevel {
    @Override
    public void loadGameObjects(GameView view, Resources resources) {
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.block);
        bmp = Bitmap.createScaledBitmap(bmp, BLOCK_WIDTH, BLOCK_HEIGHT, false);
        // line 1
        addBlocks(bmp, 1, 4, 5);
        // line 2
        addBlocks(bmp, 2, 3, 4, 5, 6);
        // line 3
        addBlocks(bmp, 3, 2, 3, 4, 5, 6, 7);
        // line 4
        addBlocks(bmp, 4, 1, 2, 3, 4, 5, 6, 7, 8);
        // line 5
        addBlocks(bmp, 5, 1, 2, 3, 4, 5, 6, 7, 8);
        // line 6
        addBlocks(bmp, 6, 1, 4, 8);
        // line 7
        addBlocks(bmp, 7, 4);
        // line 8
        addBlocks(bmp, 8, 4);
        // line 9
        addBlocks(bmp, 9, 4, 7);
        // line 10
        addBlocks(bmp, 10, 5, 6);
    }
}
