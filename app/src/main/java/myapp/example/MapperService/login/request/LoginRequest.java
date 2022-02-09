package myapp.example.MapperService.login.request;

import android.content.Context;

import Service.dao.Player;
import Service.dao.playerDao;

public class LoginRequest {
    public int request(Context context,String USERNAME, String USERPASS){
        playerDao playerDao = new playerDao(context);
        Player player;
        player = playerDao.getuser(USERNAME);

        if(player == null){
            return 1;//用户不存在
        }
        if(player.user_pass.equals(USERPASS)){
           return 0; //密码正确
        }
        return -1;//密码错误
    }
    public Player getPlayer(Context context,String USERNAME){
        playerDao playerDao = new playerDao(context);
        Player player;
        player = playerDao.getuser(USERNAME);
        return player;
    }
}
