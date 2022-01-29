package myapp.example.myfinalproject.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import Service.dao.Player;
import myapp.example.myfinalproject.R;

public class forget_pass_page extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page);
        //自动得到当前用户，提示输入密保问题
        //或手动输入当前用户，提示输入密保问题
        Bundle bundle = getIntent().getExtras();

        Player player = (Player) bundle.getParcelable("person");

        Toast.makeText(this,"来自LOGINMAINPAGE的数据："+player.user_name,Toast.LENGTH_LONG).show();
    }
}
