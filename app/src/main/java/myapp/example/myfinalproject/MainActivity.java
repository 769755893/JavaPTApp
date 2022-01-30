package myapp.example.myfinalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static myapp.example.myfinalproject.MyGamesLayout.TIME_CHANGED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyGamesLayout myGamesLayout;
    private TextView mLevel;
    private TextView mTime;
    public Button button1, button2, button3, button4;
    public Button stbutton1, stbutton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stbutton1 = (Button) findViewById(R.id.startgame);
        stbutton1.setOnClickListener(this);

        stbutton2 = (Button) findViewById(R.id.exitgame);
        stbutton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == stbutton1) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.startlayout, null);
            setContentView(view);

            button1 = (Button) findViewById(R.id.StartBtn);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myGamesLayout.isPause) {
                        myGamesLayout.isPause = false;
                        myGamesLayout.mHandler.sendEmptyMessage(TIME_CHANGED);
                    }
                }
            });
            button2 = (Button) findViewById(R.id.PauseBtn);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGamesLayout.isPause=true;
                    myGamesLayout.mHandler.removeMessages(TIME_CHANGED);
                }
            });
            button3 = (Button) findViewById(R.id.ReStartBtn);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 myGamesLayout.mHandler.removeMessages(TIME_CHANGED);//移除上一级Handler的信息，防止时间变快。
                 myGamesLayout.restart();
                }
            });
            button4 = (Button) findViewById(R.id.ExitBtn);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 finish();
                }
            });

            mTime = (TextView) findViewById(R.id.id_time);
            mLevel = (TextView) findViewById(R.id.id_level);

            myGamesLayout = (MyGamesLayout) findViewById(R.id.pintu);
            myGamesLayout.setTimeEnabled(true);
            myGamesLayout.setmListener(new MyGamesLayout.GamePintuListener() {
                @Override
                public void nextLevel(final int nextLevel) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Game Information").setMessage("Level UP!")
                            .setPositiveButton("Next Level!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myGamesLayout.nextLevel();
                                    mLevel.setText("Level:" + nextLevel);
                                }
                            }).show();
                }

                @Override
                public void timechanged(int currentTime) {
                    mTime.setText("" + currentTime);//空""作用为转字符串，防报错。
                }

                @Override
                public void gameover() {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Game Information").setMessage("Game over!")
                            .setPositiveButton("ReStart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myGamesLayout.restart();
                                }
                            }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                }
            });
        }
        if (v == stbutton2) {
            finish();
        }
    }
}
