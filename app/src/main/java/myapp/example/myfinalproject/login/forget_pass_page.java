package myapp.example.myfinalproject.login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import Service.dao.Player;
import myapp.example.myfinalproject.R;
import util.ChangeBitmap;

public class forget_pass_page extends Activity implements View.OnClickListener{
    ChangeBitmap changeBitmap = new ChangeBitmap();
    EditText username;
    ImageView forget_logo_iv;
    Button search_btn;
    TextView password_show_tv;

    LinearLayout ll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page);
        ll = findViewById(R.id.forget_container);

        //自动得到当前用户，提示输入密保问题
        //或手动输入当前用户，提示输入密保问题
        Bundle bundle = getIntent().getExtras();
        Player player = (Player) bundle.getParcelable("person");
        Toast.makeText(this,"来自LOGINMAINPAGE的数据："+player.user_name,Toast.LENGTH_LONG).show();
        username = findViewById(R.id.forget_username);
        username.setText(player.user_name);

        forget_logo_iv = findViewById(R.id.forget_logo);
        Bitmap forget_logo_bp;
        forget_logo_bp = BitmapFactory.decodeResource(getResources(),R.drawable.forget_logo);
        forget_logo_iv.setImageBitmap(forget_logo_bp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_btn:
                search_check();
                break;
        }
    }

    private void search_check() {

    }
}
