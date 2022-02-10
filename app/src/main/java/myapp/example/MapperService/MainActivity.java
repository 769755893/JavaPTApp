package myapp.example.MapperService;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static myapp.example.MapperService.Gamming.MyGamesLayout.TIME_CHANGED;

import java.util.ArrayList;

import Service.dao.Player;
import myapp.example.MapperService.Gamming.MyGamesLayout;
import myapp.example.MapperService.ViewFragment.GameFragment;
import myapp.example.MapperService.ViewFragment.ProfilesFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button stbutton1, stbutton2;
    private Player deliverPlayer;
    ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deliverPlayer = getIntent().getParcelableExtra("person");
        initPager();
    }

    private void initPager() {
        viewPager2 = findViewById(R.id.ViewPage_container);//初始化
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(GameFragment.newInstance("GamePage"));
        fragments.add(ProfilesFragment.newInstance("Profiles"));

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                getLifecycle(),fragments);
        //getLifeCycle 是JetPack控件。

        //如今的app开发中，由mv  vm 开发方式。不论是生命周期管理，还是控件管理，都大量的运用Jetpack控件，需要掌握。
        viewPager2.setAdapter(pagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
