package myapp.example.myfinalproject.login;

import static java.lang.System.exit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import Service.dao.Player;
import myapp.example.myfinalproject.MainActivity;
import myapp.example.myfinalproject.R;
import myapp.example.myfinalproject.login.request.LoginRequest;
import util.ChangeBitmap;

public class LoginMainPage extends Activity implements View.OnClickListener {
    TextClock textClock;
    ImageView logo,user_logo,pass_logo;
    ImageButton login_button,exit_button,about;
    Button forget_btn,sign_btn,login_eye_btn;
    ChangeBitmap changeBitmap = new ChangeBitmap();
    AutoCompleteTextView username, userpass;
    static final int SIGN_SUCCESS_BACK =0x000001;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate!");
        setContentView(R.layout.login);
        inittv();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop!");
    }

    private void inittv() {
        textClock=findViewById(R.id.tv_time);
        CharSequence s = "hh:mm:ss";
        //tvShowTime 是TextClock的实例对象
        textClock.setFormat24Hour(s);

        about = findViewById(R.id.gy_button);
        about.setOnClickListener(this);
        logo = findViewById(R.id.logo_iv);
        user_logo = findViewById(R.id.user_logo);
        pass_logo = findViewById(R.id.pass_logo);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        exit_button = findViewById(R.id.exit_button);
        exit_button.setOnClickListener(this);
        forget_btn = findViewById(R.id.forget_pass_btn);
        forget_btn.setOnClickListener(this);
        sign_btn = findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(this);
        username = findViewById(R.id.username);
        userpass = findViewById(R.id.userpass);
        userpass.setMaxLines(1);
        username.setMaxLines(1);
        username.setInputType(InputType.TYPE_CLASS_NUMBER);
        userpass.setInputType(InputType.TYPE_CLASS_NUMBER);
        username.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        userpass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        login_eye_btn = findViewById(R.id.eye_login_btn);
        login_eye_btn.setOnClickListener(this);
        login_eye_btn.setSelected(true);

        userpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(login_eye_btn.isSelected()) {
                    userpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    userpass.setSelection(userpass.getText().length());
                }
            }
        });





        Bitmap LOGO_Bitmap,USER_LOGO_BITMAP,PASS_LOGO_BITMAP,LOGIN_BUTTON_BITMAP,EXIT_BUTTON_BITMAP,
                GY_BITMAP;
         LOGO_Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gamer);
         USER_LOGO_BITMAP = BitmapFactory.decodeResource(getResources(),R.drawable.user_logo);
         PASS_LOGO_BITMAP = BitmapFactory.decodeResource(getResources(),R.drawable.pass_logo);
         LOGIN_BUTTON_BITMAP = BitmapFactory.decodeResource(getResources(),R.drawable.login);
         EXIT_BUTTON_BITMAP = BitmapFactory.decodeResource(getResources(),R.drawable.exit);
         GY_BITMAP = BitmapFactory.decodeResource(getResources(),R.drawable.about);


        LOGO_Bitmap = changeBitmap.changeBitmap(LOGO_Bitmap,400,400);
        USER_LOGO_BITMAP = changeBitmap.changeBitmap(USER_LOGO_BITMAP,120,120);
        PASS_LOGO_BITMAP = changeBitmap.changeBitmap(PASS_LOGO_BITMAP,120,120);
        LOGIN_BUTTON_BITMAP = changeBitmap.changeBitmap(LOGIN_BUTTON_BITMAP,500,100);
        EXIT_BUTTON_BITMAP = changeBitmap.changeBitmap(EXIT_BUTTON_BITMAP,500,100);
        GY_BITMAP = changeBitmap.changeBitmap(GY_BITMAP,250,250);
        logo.setImageBitmap(LOGO_Bitmap);
        user_logo.setImageBitmap(USER_LOGO_BITMAP);
        pass_logo.setImageBitmap(PASS_LOGO_BITMAP);
        login_button.setImageBitmap(LOGIN_BUTTON_BITMAP);
        exit_button.setImageBitmap(EXIT_BUTTON_BITMAP);
        about.setImageBitmap(GY_BITMAP);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gy_button:
                Toast.makeText(this, "你点击了关于", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginMainPage.this,aboutpage.class);
                startActivity(intent);
                break;
            case R.id.login_button:
                checklogin();
                break;
            case R.id.exit_button:
                exit(0);
                break;
            case R.id.sign_btn:
                Intent intent1 = new Intent(this,SignPage.class);
                startActivity(intent1);
                break;
            case R.id.forget_pass_btn:
                Intent intent2 = new Intent(this,forget_pass_page.class);
                Bundle bundle = new Bundle();
                Player player = new Player(String.valueOf(username.getText()),String.valueOf(userpass.getText()),0,0,0);
                bundle.putParcelable("person",player);
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
            case R.id.eye_login_btn:
                if(login_eye_btn.isSelected()){
                    userpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    login_eye_btn.setSelected(false);
                    userpass.setSelection(userpass.getText().length());
                }
                else{
                    login_eye_btn.setSelected(true);
                    userpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    userpass.setSelection(userpass.getText().length());
                }
                break;
        }
    }

    private void checklogin() {
        //向服务层发送请求信息,将username,password 传入服务层。

        String USERNAME, USERPASS;
        USERNAME = String.valueOf(username.getText());
        USERPASS = String.valueOf(userpass.getText());
        if (USERNAME.length()==0) {
            Toast.makeText(this, "用户名null!", Toast.LENGTH_LONG).show();
        } else if (USERPASS.length()==0) {
            Toast.makeText(this, "密码null", Toast.LENGTH_LONG).show();
        } else {
            if (USERNAME.length() > 10 || USERPASS.length() > 10) {
                Toast.makeText(this, "长度非法！", Toast.LENGTH_LONG).show();
            } else {
                int flag = -1;
                LoginRequest loginRequest = new LoginRequest();
                flag = loginRequest.request(this, USERNAME, USERPASS);
                if (flag == 0) {
                    //Success
                    Player player = loginRequest.getPlayer(this,USERNAME);
                    Intent intent = new Intent(this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("person",player);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if (flag == 1) {
                    Toast.makeText(this, "用户不存在，请先注册!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "用户or密码错误！", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("信息").setMessage("你确认退出？")
                .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("不是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }
    public static Player callback_player;
    public static Handler mhandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (SIGN_SUCCESS_BACK == msg.what) {
                callback_player = msg.getData().getParcelable("person");
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume!");
        if(callback_player !=null){
        Toast.makeText(this,"Handler传递数据："+callback_player.user_name,Toast.LENGTH_LONG).show();
    }
    }
}
