package ru.fw_tm.level;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ru.fw_tm.GameView;
import ru.fw_tm.R;

/**
 * @author : Ragnarok
 * @date : 06.07.12  12:13
 */
public class Level2 extends AbstractLevel {
    @Override
    public void loadGameObjects(GameView view, Resources resources) {
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.block);
        bmp = Bitmap.createScaledBitmap(bmp, BLOCK_WIDTH, BLOCK_HEIGHT, false);
        // line 1
        addBlocks(bmp, 1, 1, 2, 3, 4, 5, 6, 7, 8);
        // line 2
        addBlocks(bmp, 2, 1, 3, 5, 7, 8);
        // line 3
        addBlocks(bmp, 3, 1, 2, 4, 6, 8);
        // line 4
        addBlocks(bmp, 4, 1, 3, 5, 7, 8);
        // line 5
        addBlocks(bmp, 5, 1, 2, 4, 6, 8);
        // line 6
        addBlocks(bmp, 6, 1, 3, 5, 7, 8);
        // line 7
        addBlocks(bmp, 7, 1, 2, 4, 6, 8);
        // line 8
        addBlocks(bmp, 8, 1, 3, 5, 7, 8);
        // line 9
        addBlocks(bmp, 9, 1, 2, 4, 6, 8);
        // line 10
        addBlocks(bmp, 10, 1, 2, 3, 4, 5, 6, 7, 8);
    }
}
