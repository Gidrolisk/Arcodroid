package ru.pstu;

/**
 * @author : Ragnarok
 * @date : 27.06.12  22:56
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import ru.pstu.level.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameView extends SurfaceView {
    private Lock updateLock;

    private final AbstractLevel[] LEVELS;
    private Accelerometer accelerometer;

    private AbstractLevel currentLevel;
    private int level = 0;

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

        LEVELS = new AbstractLevel[]{
                new Level1(), new Level2(), new Level3(), new Level4(),
                new Level5(), new Level6(), new Level7(), new Level8(),
                new Level9(), new Level10(), new Level1(), new Level2(),
                new Level13(), new Level4(), new Level5()
        };
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
        level = 0;
        currentLevel = LEVELS[level];
        currentLevel.load(this, getResources());
        currentLevel.start();

        updateLock = new ReentrantLock();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        updateLock.lock();
        try {
            currentLevel.drawLevel(canvas, getWidth(), getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            updateLock.unlock();
        }
    }

    public Accelerometer getAccelerometer() {
        return accelerometer;
    }

    public AbstractLevel getCurrentLevel() {
        return currentLevel;
    }

    public void nextLevel() {
        updateLock.lock();
        try {
            currentLevel.unload();
            level++;
            if (level < LEVELS.length) {
                currentLevel = LEVELS[level];
                currentLevel.load(this, getResources());
                currentLevel.start();
            } else {
                Toast.makeText(getContext(), "Вы прошли все уровни! Поздравляем!", 1500).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            updateLock.unlock();
        }
    }
}
