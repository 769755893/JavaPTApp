package myapp.example.myfinalproject.login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import Service.dao.Player;
import myapp.example.myfinalproject.R;
import myapp.example.myfinalproject.login.request.SignRequest;
import util.ChangeBitmap;


public class SignPage extends FragmentActivity implements View.OnClickListener{
    ImageButton zc_Btn,srmb_btn;
    EditText username,userpass;
    Button sign_eye_btn;
    
    ImageView user_logo,pass_logo,sign_back_btn,sign_center_logo,mb_logo;
    ChangeBitmap changeBitmap = new ChangeBitmap();
    DateSelectFragment dateSelectFragment = new DateSelectFragment();
    TextView srmb_tv;
    public boolean isfragmentclose = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);//没有ContentView那所有的都是空对象
        InitWidget();
        InitFragmentCallback();
    }

    protected class datedateclass{
        private int I,I1,I2;

        datedateclass(int i,int i1,int i2){
            this.I=i;
            this.I1=i1;
            this.I2=i2;
        }
        public int getI() {
            return I;
        }

        public void setI(int i) {
            I = i;
        }

        public int getI1() {
            return I1;
        }

        public void setI1(int i1) {
            I1 = i1;
        }

        public int getI2() {
            return I2;
        }

        public void setI2(int i2) {
            I2 = i2;
        }
    }
    public interface deliverdatedata{
        datedateclass deliver();
    }

    deliverdatedata datedata;
    public void delivercallfunction(deliverdatedata d){
        this.datedata = d;
    }


    private void InitFragmentCallback() {
        dateSelectFragment.FragmentCallbackInterface(new DateSelectFragment.FragmentInterface() {
            @Override
            public void sendData(int i, int i1, int i2) {
                srmb_tv.setText(i+"年"+i1+"月"+i2+"日");
                delivercallfunction(new deliverdatedata() {
                    @Override
                    public datedateclass deliver() {
                        return new datedateclass(i,i1,i2);
                    }
                });
            }

            @Override
            public void sendremovemsg() {
                removeFragment(dateSelectFragment);
                isfragmentclose = true;
            }
        });
    }

    private void InitWidget() {

        user_logo = findViewById(R.id.sign_user_logo);
        pass_logo = findViewById(R.id.sign_pass_logo);
        sign_center_logo = findViewById(R.id.sign_center_logo);
        sign_back_btn = findViewById(R.id.sign_back_btn);
        zc_Btn = findViewById(R.id.zc_btn);
        username = findViewById(R.id.username_sign);
        userpass = findViewById(R.id.userpass_sign);
        username.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        username.setInputType(InputType.TYPE_CLASS_NUMBER);
        userpass.setInputType(InputType.TYPE_CLASS_NUMBER);
        userpass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        sign_eye_btn = findViewById(R.id.sign_eye_btn);
        srmb_btn = findViewById(R.id.srmb_btn);
        srmb_tv = findViewById(R.id.srmb_tv);
        mb_logo = findViewById(R.id.mb_logo);


        srmb_btn.setOnClickListener(this);
        sign_back_btn.setOnClickListener(this);
        zc_Btn.setOnClickListener(this);
        sign_back_btn.setOnClickListener(this);
        sign_eye_btn.setOnClickListener(this);

        Bitmap user_logo_bp,pass_logo_bp,zc_btn_bp,sign_back_bp,sign_center_logo_bp,srmb_btn_bp,mb_logo_bp;
        user_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.sign_user);
        pass_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.sign_pass);
        zc_btn_bp = BitmapFactory.decodeResource(getResources(),R.drawable.sign_img);
        sign_back_bp = BitmapFactory.decodeResource(getResources(),R.drawable.back);
        sign_center_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.panda);
        srmb_btn_bp = BitmapFactory.decodeResource(getResources(),R.drawable.xlbox);
        mb_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.mb_logo);


        user_logo_bp = changeBitmap.changeBitmap(user_logo_bp,200,200);
        pass_logo_bp = changeBitmap.changeBitmap(pass_logo_bp,200,200);
        zc_btn_bp = changeBitmap.changeBitmap(zc_btn_bp,300,100);
        sign_back_bp = changeBitmap.changeBitmap(sign_back_bp,100,100);
        sign_center_logo_bp = changeBitmap.changeBitmap(sign_center_logo_bp,300,300);
        srmb_btn_bp = changeBitmap.changeBitmap(srmb_btn_bp,100,100);
        mb_logo_bp = changeBitmap.changeBitmap(mb_logo_bp,100,100);

        user_logo.setImageBitmap(user_logo_bp);
        pass_logo.setImageBitmap(pass_logo_bp);
        zc_Btn.setImageBitmap(zc_btn_bp);
        sign_back_btn.setImageBitmap(sign_back_bp);
        sign_center_logo.setImageBitmap(sign_center_logo_bp);
        srmb_btn.setImageBitmap(srmb_btn_bp);
        mb_logo.setImageBitmap(mb_logo_bp);

        sign_eye_btn.setSelected(true);
        userpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {//默认密文
            @Override
            public void onFocusChange(View view, boolean b) {
                if(sign_eye_btn.isSelected()){
                    userpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    userpass.setSelection(userpass.getText().length());
                }
            }
        });
    }

    public int sign_fragment_press=-1;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zc_btn:
                signcheck();
                break;
            case R.id.sign_back_btn:
                finish();
                break;
            case R.id.sign_eye_btn:
                if(sign_eye_btn.isSelected()){
                    sign_eye_btn.setSelected(false);
                    userpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    userpass.setSelection(userpass.getText().length());
                }
                else{
                    sign_eye_btn.setSelected(true);
                    userpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    userpass.setSelection(userpass.getText().length());
                }
                break;
            case R.id.srmb_btn:
                //add fragment
                sign_fragment_press=0;
                if(isfragmentclose){
                    ADDFragment(dateSelectFragment);
                    isfragmentclose = false;
                    InitFragmentWidget();
                }
                break;
        }
    }


    private void InitFragmentWidget() {

    }

    private void ADDFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,fragment);
        transaction.commit();
    }

    private void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
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
        else if(sign_fragment_press==-1){
            Toast.makeText(this,"密保null",Toast.LENGTH_LONG).show();
        }
        else{
            datedateclass tempdate = datedata.deliver();
            SignRequest signRequest = new SignRequest();
            flag = signRequest.Request(this,UserName,UserPass,tempdate.getI(),tempdate.getI1(),tempdate.getI2());

            if(flag == 0){
                //成功，存储用户类，跳回
                Message msg = new Message();
                Bundle bundle = new Bundle();

                Player player = new Player(UserName,UserPass,tempdate.getI(),tempdate.getI1(),tempdate.getI2());
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
