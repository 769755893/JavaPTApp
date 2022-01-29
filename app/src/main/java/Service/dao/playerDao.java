package Service.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dbcurd.LoginDbHelper;

public class playerDao {
    LoginDbHelper dbhelper;
    public playerDao(Context context){
        dbhelper = new LoginDbHelper(context,"player.db",null,1);
    }
    public void add(String username, String password){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.execSQL(
                "insert into " +
                Player.TABLE_NAME+" ("+Player.KEY_USER_NAME+","+Player.KEY_USER_PASS+") " +
                "values("+username+","+password+");"
        );
        db.close();
    }
    public Player getuser(String username){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
//        String table =  Player.TABLE_NAME ;
//        String[] columns = new  String[] { Player.KEY_USER_PASS };
//        String selection = Player.KEY_USER_NAME+"=?" ;
//        String[] selectionArgs = new  String[]{username};
//        String groupBy = "" ;
//        String having = "" ;
//        String orderBy = "" ;
//        Cursor cursor = db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
        Player player = new Player("","");
        Cursor cursor = db.rawQuery("select "+Player.KEY_USER_NAME+","+Player.KEY_USER_PASS
                        +" from "+Player.TABLE_NAME +" where "+Player.KEY_USER_NAME +"=?",new String[]{username}
        );

        if(cursor.getCount()==0){
            cursor.close();
            db.close();
            return null;
        }else{
            while(cursor.moveToNext()){
                if(cursor.moveToFirst()){
                    player.setUser_name(cursor.getString(cursor.getColumnIndex(Player.KEY_USER_NAME)));
                    player.setUser_pass(cursor.getString(cursor.getColumnIndex(Player.KEY_USER_PASS)));
                    cursor.close();
                    db.close();
                    return player;
                }
            }
            cursor.close();
            db.close();
            return player;
        }
    }
}
