package myapp.example.myfinalproject.login;

import android.content.Context;

import Service.dao.Player;
import Service.dao.playerDao;

public class SignRequest {
    public int Request(Context context, String UserName, String UserPass){
        playerDao pd = new playerDao(context);

        //用户存在？
        Player player = pd.getuser(UserName);
        if(player !=null){
            return 1;
        }
        else{
            pd.add(UserName,UserPass);
            return 0;//用户不存在
        }
    }
}
