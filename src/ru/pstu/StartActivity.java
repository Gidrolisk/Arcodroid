package ru.pstu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

/**
 * @author : Ragnarok
 * @date : 09.07.12  15:17
 */
public class StartActivity extends Activity implements View.OnClickListener {

    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 4000; //Время показа Splash картинки 10 секунд
    private ImageView splash;

    private Handler splashHandler = new Handler() { //создаем новый хэндлер
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    //убираем Splash картинку - меняем видимость
                    splash.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.start);
        //setContentView(new GameView(this,null));

        Button startButton = (Button) findViewById(R.id.button1);
        startButton.setOnClickListener(this);

        Button exitButton = (Button) findViewById(R.id.button2);
        exitButton.setOnClickListener(this);

        splash = (ImageView) findViewById(R.id.splashscreen); //получаем индентификатор ImageView с Splash картинкой
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }

    /**
     * Обработка нажатия кнопок
     */
    public void onClick(View v) {
        switch (v.getId()) {
            //переход на сюрфейс
            case R.id.button1: {
                Intent intent = new Intent();
                intent.setClass(this, GameActivity.class);
                startActivity(intent);
            }
            break;

            //выход
            case R.id.button2: {
                finish();
            }
            break;
        }
    }
}
