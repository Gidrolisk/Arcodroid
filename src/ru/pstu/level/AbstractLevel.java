package ru.pstu.level;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import ru.pstu.GameManager;
import ru.pstu.GameView;
import ru.pstu.R;
import ru.pstu.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ragnarok
 * @date : 06.07.12  11:07
 */
public abstract class AbstractLevel {
    private static final int CHANGE_TEXT = 0;
    private static final int CHANGE_GAME_STATE = 1;
    private static final int START_AFTER_BALL_LOST = 2;

    private static final int CHANGE_TEXT_TIME = 1000;
    private static final int CHANGE_GAME_STATE_TIME = 3000;
    private static final int START_AFTER_BALL_LOST_TIME = 2000;

    final List<DrawableObject> gameObjects;
    final List<Block> blocks;

    GameView gameView;
    Platform platform;
    Ball ball;
    LifeBar lifeBar;

    LevelState levelState;
    Text text;

    private Handler levelHandler = new Handler() { //создаем новый хэндлер
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_TEXT:
                    if (text != null) {
                        synchronized (gameObjects) {
                            gameObjects.remove(text);
                        }
                    }
                    if (msg.obj != null) {
                        text = new Text(gameView, null, 0, 0);
                        text.setText(String.valueOf(msg.obj));
                        synchronized (gameObjects) {
                            gameObjects.add(text);
                        }
                    }
                    break;
                case CHANGE_GAME_STATE:
                    levelState = (LevelState) msg.obj;
                    break;
                case START_AFTER_BALL_LOST:
                    if (text != null) {
                        synchronized (gameObjects) {
                            gameObjects.remove(text);
                        }
                    }
                    start();
                    break;

            }
            super.handleMessage(msg);
        }
    };

    protected int MAX_WIDTH;
    protected int BLOCK_WIDTH;
    protected int BLOCK_HEIGHT;

    public AbstractLevel() {
        gameObjects = new ArrayList<DrawableObject>();
        blocks = new ArrayList<Block>();
        levelState = LevelState.READY;
    }

    public void drawLevel(Canvas canvas, int maxWidth, int maxHeight) {
        synchronized (gameObjects) {
            for (DrawableObject object : gameObjects) {
                object.draw(canvas, maxWidth, maxHeight);
            }
        }
    }

    public void load(GameView view, Resources resources) {
        gameView = view;
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        MAX_WIDTH = windowManager.getDefaultDisplay().getWidth();

        BLOCK_WIDTH = MAX_WIDTH / 8;
        BLOCK_HEIGHT = MAX_WIDTH / 12;

        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.background);
        BackgroundImage image = new BackgroundImage(gameView, bmp, 0, 0);
        gameObjects.add(image);

        Line line = new Line(gameView, null, 0, 0);
        gameObjects.add(line);

        bmp = BitmapFactory.decodeResource(resources, R.drawable.ball);
        ball = new Ball(view, bmp, 0, 0);
        gameObjects.add(ball);

        lifeBar = new LifeBar(gameView, bmp, 0, 0);
        gameObjects.add(lifeBar);

        bmp = BitmapFactory.decodeResource(resources, R.drawable.platform);
        platform = new Platform(view, bmp, 0, 0);
        gameObjects.add(platform);

        loadGameObjects(view, resources);
    }

    public void unload() {
        synchronized (gameObjects) {
            gameObjects.clear();
        }
        synchronized (blocks) {
            blocks.clear();
        }
        System.gc();
    }

    public void addBlocks(Bitmap bmp, int row, int... pos) {
        int initialY = GameManager.TOP_SHIFT + BLOCK_HEIGHT * (row - 1);
        int i = 1;
        for (int x = 0; x < MAX_WIDTH; x += BLOCK_WIDTH) {
            if (contains(i++, pos)) {
                Block block = new Block(gameView, bmp, x, initialY);
                gameObjects.add(block);
                blocks.add(block);
            }
        }
    }

    public void deleteBlock(Block block) {
        block.setDeleted(true);
        blocks.remove(block);
        if (blocks.size() == 0) {
            onLevelCompleted();
        }
    }

    public void onLevelCompleted() {
        gameView.nextLevel();
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

    public List<Block> getBlocks() {
        return blocks;
    }

    public abstract void loadGameObjects(GameView view, Resources resources);

    public LevelState getLevelState() {
        return levelState;
    }

    public void start() {
        Message msg = new Message();
        msg.what = CHANGE_TEXT;
        msg.obj = "3";
        levelHandler.sendMessageDelayed(msg, 0);

        msg = new Message();
        msg.what = CHANGE_TEXT;
        msg.obj = "2";
        levelHandler.sendMessageDelayed(msg, CHANGE_TEXT_TIME);

        msg = new Message();
        msg.what = CHANGE_TEXT;
        msg.obj = "1";
        levelHandler.sendMessageDelayed(msg, CHANGE_TEXT_TIME * 2);

        msg = new Message();
        msg.what = CHANGE_TEXT;
        msg.obj = "GO!";
        levelHandler.sendMessageDelayed(msg, CHANGE_TEXT_TIME * 3);

        msg = new Message();
        msg.what = CHANGE_TEXT;
        levelHandler.sendMessageDelayed(msg, CHANGE_TEXT_TIME * 4);

        msg = new Message();
        msg.what = CHANGE_GAME_STATE;
        msg.obj = LevelState.GO;
        levelHandler.sendMessageDelayed(msg, CHANGE_GAME_STATE_TIME);
    }

    public void onBallLost() {
        levelState = LevelState.READY;
        ball.posInitialized = false;
        platform.posInitialized = false;
        lifeBar.decreaseLife();

        Message msg = new Message();
        msg.what = CHANGE_TEXT;
        msg.obj = "Ball lost!";
        levelHandler.sendMessageDelayed(msg, 0);

        msg = new Message();
        msg.what = START_AFTER_BALL_LOST;
        levelHandler.sendMessageDelayed(msg, START_AFTER_BALL_LOST_TIME);
    }

    public static enum LevelState {
        READY,
        GO,
    }
}
