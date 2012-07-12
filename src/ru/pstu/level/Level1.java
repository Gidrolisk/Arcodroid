package ru.pstu.level;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ru.pstu.GameView;
import ru.pstu.R;

/**
 * @author : Ragnarok
 * @date : 06.07.12  11:09
 */
public class Level1 extends AbstractLevel {

    @Override
    public void loadGameObjects(GameView view, Resources resources) {
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.block);
        bmp = Bitmap.createScaledBitmap(bmp, BLOCK_WIDTH, BLOCK_HEIGHT, false);

        // 1 Line
        addBlocks(bmp, 1, 1, 2, 3, 4, 5, 6, 7, 8);
        // 2 Line
        addBlocks(bmp, 2, 1, 8);
        // 3 Line
        addBlocks(bmp, 3, 1, 3, 4, 5, 6, 8);
        // 4 Line
        addBlocks(bmp, 4, 1, 3, 6, 8);
        // 5 Line
        addBlocks(bmp, 5, 1, 3, 4, 5, 6, 8);
        // 6 Line
        addBlocks(bmp, 6, 1, 3, 4, 5, 6, 8);
        // 7 Line
        addBlocks(bmp, 7, 1, 3, 4, 6, 8);
        // 8 Line
        addBlocks(bmp, 8, 1, 3, 4, 5, 6, 8);
        // 9 Line
        addBlocks(bmp, 9, 1, 3, 8);
        // 10 Line
        addBlocks(bmp, 10, 1, 3, 4, 5, 6, 7, 8);
    }
}
