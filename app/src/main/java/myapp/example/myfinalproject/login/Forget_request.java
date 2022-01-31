package myapp.example.myfinalproject.login;


import android.content.Context;

import Service.dao.Player;
import Service.dao.playerDao;

public class Forget_request {
    private int msg;

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public interface SendPassword{
        void sendplayer(Player player);
    }

    SendPassword sendPassword;

    public void forget_call_back(SendPassword sendPassword){
        this.sendPassword = sendPassword;
    }
    public Forget_request(Context context, String username, int y, int m, int d) {
        playerDao playerdao = new playerDao(context);

        Player player = playerdao.getuser(username);
        if(player == null){
            this.msg = -1;
        }
        else{
            if(player.getYear()==y&&player.getMonth()==m&&player.getDay()==d){
                this.msg = 0;//correct
                sendPassword.sendplayer(player);
            }else {
                this.msg = 1;//incorrect
            }
        }
    }
}
