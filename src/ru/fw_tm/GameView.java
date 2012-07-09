package ru.fw_tm;

/**
 * @author : Ragnarok
 * @date : 27.06.12  22:56
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import ru.fw_tm.level.AbstractLevel;
import ru.fw_tm.level.Level1;

public class GameView extends SurfaceView {
    private Accelerometer accelerometer;

    private AbstractLevel currentLevel;

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
        accelerometer = new Accelerometer();
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

        currentLevel = new Level1();
        currentLevel.load(this, getResources());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        currentLevel.drawLevel(canvas, getWidth(), getHeight());
    }

    public Accelerometer getAccelerometer() {
        return accelerometer;
    }

    public AbstractLevel getCurrentLevel() {
        return currentLevel;
    }
}
