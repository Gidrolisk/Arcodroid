package ru.fw_tm.level;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.WindowManager;
import ru.fw_tm.GameManager;
import ru.fw_tm.GameView;
import ru.fw_tm.R;
import ru.fw_tm.model.Ball;
import ru.fw_tm.model.Block;
import ru.fw_tm.model.GameObject;
import ru.fw_tm.model.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ragnarok
 * @date : 06.07.12  11:07
 */
public abstract class AbstractLevel {
    GameView gameView;
    Platform platform;
    List<GameObject> gameObjects;

    protected int MAX_WIDTH;
    protected int BLOCK_WIDTH;
    protected int BLOCK_HEIGHT;


    public AbstractLevel() {
        gameObjects = new ArrayList<GameObject>();
    }

    public void drawLevel(Canvas canvas, int maxWidth, int maxHeight) {
        for (GameObject object : gameObjects) {
            object.draw(canvas, maxWidth, maxHeight);
        }
    }

    public void load(GameView view, Resources resources) {
        gameView = view;
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        MAX_WIDTH = windowManager.getDefaultDisplay().getWidth();

        BLOCK_WIDTH = MAX_WIDTH / 8;
        BLOCK_HEIGHT = MAX_WIDTH / 12;

        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.ball1);
        Ball ball = new Ball(view, bmp, 0, 0);
        gameObjects.add(ball);

        bmp = BitmapFactory.decodeResource(resources, R.drawable.platform);
        platform = new Platform(view, bmp, 0, 0);
        gameObjects.add(platform);


        loadGameObjects(view, resources);
    }

    public void addBlocks(Bitmap bmp, int row, int... pos) {
        int initialY = GameManager.BOTTOM_SHIFT + BLOCK_HEIGHT * (row - 1);
        int i = 1;
        for (int x = 0; x < MAX_WIDTH; x += BLOCK_WIDTH) {
            if (contains(i++, pos)) {
                Block block = new Block(gameView, bmp, x, initialY);
                gameObjects.add(block);
            }
        }
    }

    private boolean contains(int x, int[] array) {
        for (int a : array) {
            if (a == x) {
                return true;
            }
        }
        return false;
    }

    public Platform getPlatform() {
        return platform;
    }

    public abstract void loadGameObjects(GameView view, Resources resources);
}
