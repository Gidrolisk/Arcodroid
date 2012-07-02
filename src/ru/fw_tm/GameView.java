package ru.fw_tm;

/**
 * @author : Ragnarok
 * @date : 27.06.12  22:56
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import ru.fw_tm.model.Ball;
import ru.fw_tm.model.GameObject;
import ru.fw_tm.model.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {
    private List<GameObject> gameObjects;
    public Platform platform;

    /**
     * Загружаемая картинка
     */
    private Bitmap bmp;

    /**
     * Наше поле рисования
     */
    private SurfaceHolder holder;

    /**
     * Объект класса GameManager
     */
    private GameManager gameLoopThread;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameManager(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            /** Уничтожение области рисования */
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            /** Создание области рисования */
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            /** Изменение области рисования */
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        loadGameObjects();
    }

    private void loadGameObjects() {
        gameObjects = new ArrayList<GameObject>();

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball1);
        Ball ball = new Ball(this, bmp, 0, 0);
        gameObjects.add(ball);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.platform);
        platform = new Platform(this, bmp, 0, 0);
        gameObjects.add(platform);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(canvas, getWidth(), getHeight());
        }
    }
}
