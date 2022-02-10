package myapp.example.MapperService.ViewFragment;

import static myapp.example.MapperService.Gamming.MyGamesLayout.TIME_CHANGED;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import myapp.example.MapperService.Gamming.MyGamesLayout;
import myapp.example.MapperService.MainActivity;
import myapp.example.MapperService.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {


    private MyGamesLayout myGamesLayout;
    private TextView mLevel;
    private TextView mTime;
    public Button button1, button2, button3, button4;

    Context context;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.startlayout,container,false);

        button1 = view.findViewById(R.id.StartBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myGamesLayout.isPause) {
                    myGamesLayout.isPause = false;
                    myGamesLayout.mHandler.sendEmptyMessage(TIME_CHANGED);
                }
            }
        });
        button2 = view. findViewById(R.id.PauseBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGamesLayout.isPause=true;
                myGamesLayout.mHandler.removeMessages(TIME_CHANGED);
            }
        });
        button3 = view. findViewById(R.id.ReStartBtn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGamesLayout.mHandler.removeMessages(TIME_CHANGED);//移除上一级Handler的信息，防止时间变快。
                myGamesLayout.restart();
            }
        });
        button4 = view. findViewById(R.id.ExitBtn);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mTime = view. findViewById(R.id.id_time);
        mLevel = view. findViewById(R.id.id_level);

        myGamesLayout = view. findViewById(R.id.pintu);
        myGamesLayout.setTimeEnabled(true);
        myGamesLayout.setmListener(new MyGamesLayout.GamePintuListener() {
            @Override
            public void nextLevel(final int nextLevel) {
                new AlertDialog.Builder(context)
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
                new AlertDialog.Builder(context)
                        .setTitle("Game Information").setMessage("Game over!")
                        .setPositiveButton("ReStart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myGamesLayout.restart();
                            }
                        }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        return view;
    }
}