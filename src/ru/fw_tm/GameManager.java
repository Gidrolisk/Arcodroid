package ru.fw_tm;

/**
 * @author : Ragnarok
 * @date : 27.06.12  23:16
 */

import android.graphics.Canvas;

public class GameManager extends Thread {
    public static final float SPEED_X = 7.0F;
    public static final float SPEED_Y = 7.0F;

    public static final float PLATFORM_SPEED = 10.0F;

    private static final short FPS = 90;
    /**
     * Объект класса GameView
     */
    private GameView view;

    /**
     * Задаем состояние потока
     */
    private boolean running = false;

    /**
     * Конструктор класса
     */
    public GameManager(GameView view) {
        this.view = view;
    }

    /**
     * Задание состояния потока
     */
    public void setRunning(boolean run) {
        running = run;
    }

    /**
     * Действия, выполняемые в потоке
     */

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        }
    }
}