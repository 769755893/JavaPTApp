package myapp.example.myfinalproject.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import Service.dao.Player;
import myapp.example.myfinalproject.MainActivity;
import myapp.example.myfinalproject.R;
import util.ChangeBitmap;

public class forget_pass_page extends FragmentActivity implements View.OnClickListener{
    ChangeBitmap changeBitmap = new ChangeBitmap();
    EditText username;
    ImageView forget_logo_iv;
    ImageButton forget_sr_btn,forget_back_btn;
    Button forget_search_btn;
    TextView forgetsrtv;

    DateSelectFragment dateSelectFragment = new DateSelectFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page);

        //自动得到当前用户，提示输入密保问题
        //或手动输入当前用户，提示输入密保问题
        Bundle bundle = getIntent().getExtras();
        Player player = (Player) bundle.getParcelable("person");
        Toast.makeText(this,"来自LOGINMAINPAGE的数据："+player.user_name,Toast.LENGTH_LONG).show();
        username = findViewById(R.id.forget_username);
        username.setText(player.user_name);

        forget_logo_iv = findViewById(R.id.forget_logo);
        forget_sr_btn = findViewById(R.id.forget_imgesrbtn);
        forget_search_btn = findViewById(R.id.forget_search_btn);
        forgetsrtv = findViewById(R.id.forget_sr_tv);
        forget_back_btn = findViewById(R.id.forget_back_btn);

        forget_sr_btn.setOnClickListener(this);
        forget_search_btn.setOnClickListener(this);
        forget_back_btn.setOnClickListener(this);

        Bitmap forget_logo_bp,forget_sr_btn_bp,forget_back_bp;
        forget_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.forget_logo);
        forget_sr_btn_bp = BitmapFactory.decodeResource(getResources(),R.drawable.xlbox);
        forget_back_bp = BitmapFactory.decodeResource(getResources(),R.drawable.back);

        forget_logo_bp = changeBitmap.changeBitmap(forget_logo_bp,100,100);
        forget_sr_btn_bp = changeBitmap.changeBitmap(forget_sr_btn_bp,100,100);
        forget_back_bp = changeBitmap.changeBitmap(forget_back_bp,100,100);

        forget_logo_iv.setImageBitmap(forget_logo_bp);
        forget_sr_btn.setImageBitmap(forget_sr_btn_bp);
        forget_back_btn.setImageBitmap(forget_back_bp);
    }

    class forget_mb{
        private int i,i1,i2;

        forget_mb(int i,int i1,int i2){
            this.i=i;
            this.i1=i1;
            this.i2=i2;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getI1() {
            return i1;
        }

        public void setI1(int i1) {
            this.i1 = i1;
        }

        public int getI2() {
            return i2;
        }

        public void setI2(int i2) {
            this.i2 = i2;
        }
    }

    forget_mb fmb;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_search_btn:
                search_check();
                break;
            case R.id.forget_imgesrbtn:
                //1.弹出既定fragment，选择时间，回调传递数据
                addFragment(dateSelectFragment);

                dateSelectFragment.FragmentCallbackInterface(new DateSelectFragment.FragmentInterface() {
                    @Override
                    public void sendData(int i, int i1, int i2) {
                        forgetsrtv.setText(i+"年"+i1+"月"+i2+"日");
                        fmb = new forget_mb(i,i1,i2);
                    }

                    @Override
                    public void sendremovemsg() {
                        removeFragment(dateSelectFragment);
                    }
                });
                break;
            case R.id.forget_back_btn:
                finish();
                break;
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container_forget,fragment);//1.容器，2.fragment
        transaction.commit();
    }

    private void removeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    Player tempplayer;
    private void search_check() {
        //1.发出request，当前用户存在？
        //2.当前输入的密保同数据库中相等？
        if(username.getText().length()==0||fmb==null){
            Toast.makeText(this,"用户null or 密保null",Toast.LENGTH_LONG).show();
            return;
        }

        System.out.println("-----------------------------------------------------------111");

        Forget_request forget_request = new Forget_request();
        forget_request.forget_call_back(new Forget_request.SendPassword() {
            @Override
            public void sendplayer(Player player) {
                tempplayer = player;
            }
        });//回调函数实现需要尽早实现，不然实现在使用之后则错了。

        forget_request.request(this,String.valueOf(username.getText()),fmb.i,fmb.i1,fmb.i2);
        if(forget_request.getMsg()==-1){
            Toast.makeText(this,"用户不存在",Toast.LENGTH_LONG).show();
        }
        else if(forget_request.getMsg()==1){
            Toast.makeText(this,"密保不正确！",Toast.LENGTH_LONG).show();
        }
        else if(forget_request.getMsg()==0){
            //alterdialog 显示密码信息
//            new AlertDialog.Builder(this)
            System.out.println("-------------------------------------------------------Correct!");
             new AlertDialog.Builder(this)
                     .setIcon(R.drawable.correct)
                     .setTitle("正确！").setMessage("你的密码为："+ tempplayer.getUser_pass())
                     .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {

                         }
                     }).show();
        }
    }
}
