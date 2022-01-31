package myapp.example.myfinalproject.login;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import myapp.example.myfinalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateSelectFragment extends Fragment implements View.OnClickListener,DatePicker.OnDateChangedListener{
    View view;
    private int y,m,d;
    DatePicker datePicker;
    public DateSelectFragment() {
    }

    public static DateSelectFragment newInstance(String param1, String param2) {
        DateSelectFragment fragment = new DateSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button confirm_btn,back_btn;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_date_select,container,false);
        confirm_btn = view.findViewById(R.id.btn_confirm);
        back_btn = view.findViewById(R.id.btn_back);
        datePicker = view.findViewById(R.id.date_picker);

        y=datePicker.getYear();
        m=datePicker.getMonth()+1;
        d=datePicker.getDayOfMonth();


        confirm_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        datePicker.setOnDateChangedListener(this);
        return view;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        y=i;
        m=i1+1;
        d=i2;
    }

    //回调接口
    public interface FragmentInterface{
        void sendData(int i,int i1,int i2);
        void sendremovemsg();
    }
    //回调函数
    FragmentInterface fragmentInterface;
    public void FragmentCallbackInterface(FragmentInterface fragmentInterface){
        this.fragmentInterface = fragmentInterface;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm:
                //1.向Signpage传递数据
                //2.remove fragment
                fragmentInterface.sendData(y,m,d);
                fragmentInterface.sendremovemsg();
                break;
            case R.id.btn_back:
                fragmentInterface.sendremovemsg();
                break;
        }
    }
}