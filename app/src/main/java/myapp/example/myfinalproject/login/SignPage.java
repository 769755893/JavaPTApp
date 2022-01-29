package myapp.example.myfinalproject.login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import Service.dao.Player;
import myapp.example.myfinalproject.R;
import util.ChangeBitmap;

public class SignPage extends Activity implements View.OnClickListener{
    ImageButton zc_Btn;
    EditText username,userpass;
    
    ImageView user_logo,pass_logo,sign_back_btn,sign_center_logo;
    ChangeBitmap changeBitmap = new ChangeBitmap();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);//没有ContentView那所有的都是空对象
        InitWidget();
    }

    private void InitWidget() {

        user_logo = findViewById(R.id.sign_user_logo);
        pass_logo = findViewById(R.id.sign_pass_logo);
        sign_center_logo = findViewById(R.id.sign_center_logo);
        sign_back_btn = findViewById(R.id.sign_back_btn);
        zc_Btn = findViewById(R.id.zc_btn);
        username = findViewById(R.id.username_sign);
        userpass = findViewById(R.id.userpass_sign);


        sign_back_btn.setOnClickListener(this);
        zc_Btn.setOnClickListener(this);
        sign_back_btn.setOnClickListener(this);

        Bitmap user_logo_bp,pass_logo_bp,zc_btn_bp,sign_back_bp,sign_center_logo_bp;
        user_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.sign_user);
        pass_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.sign_pass);
        zc_btn_bp = BitmapFactory.decodeResource(getResources(),R.drawable.sign_img);
        sign_back_bp = BitmapFactory.decodeResource(getResources(),R.drawable.back);
        sign_center_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.panda);


        user_logo_bp = changeBitmap.changeBitmap(user_logo_bp,200,200);
        pass_logo_bp = changeBitmap.changeBitmap(pass_logo_bp,200,200);
        zc_btn_bp = changeBitmap.changeBitmap(zc_btn_bp,300,100);
        sign_back_bp = changeBitmap.changeBitmap(sign_back_bp,100,100);
        sign_center_logo_bp = changeBitmap.changeBitmap(sign_center_logo_bp,300,300);


        user_logo.setImageBitmap(user_logo_bp);
        pass_logo.setImageBitmap(pass_logo_bp);
        zc_Btn.setImageBitmap(zc_btn_bp);
        sign_back_btn.setImageBitmap(sign_back_bp);
        sign_center_logo.setImageBitmap(sign_center_logo_bp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zc_btn:
                signcheck();
                break;
            case R.id.sign_back_btn:
                finish();
                break;
        }
    }

    private void signcheck() {
        String UserName,UserPass;
        int flag=-1;
        UserName = String.valueOf(username.getText());
        UserPass = String.valueOf(userpass.getText());
        if(UserName.length()== 0){
            Toast.makeText(this,"用户名null!",Toast.LENGTH_LONG).show();
        }
        else if(UserPass .length()==0){
            Toast.makeText(this,"密码null",Toast.LENGTH_LONG).show();
        }
        else{
            SignRequest signRequest = new SignRequest();
            flag = signRequest.Request(this,UserName,UserPass);

            if(flag == 0){
                //成功，存储用户类，跳回
                Message msg = new Message();
                Bundle bundle = new Bundle();

                Player player = new Player(UserName,UserPass);
                bundle.putParcelable("person",player);

                msg.setData(bundle);
                msg.what=LoginMainPage.SIGN_SUCCESS_BACK;
                LoginMainPage.mhandler.sendMessage(msg);
                finish();
            }
            else{
                //用户存在！
                Toast.makeText(this,"当前用户名已存在！",Toast.LENGTH_LONG).show();
                clearText();
            }
        }
    }

    private void clearText() {
        username.setText("");
        userpass.setText("");
    }
}
