package dbcurd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Service.dao.Player;

public class LoginDbHelper extends SQLiteOpenHelper {
    public LoginDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PLAYER="CREATE TABLE "+ Player.TABLE_NAME+"("
                         +Player.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                         +Player.KEY_USER_NAME+" TEXT, "
                         +Player.KEY_USER_PASS+" TEXT,"
                         +Player.KEY_USER_YEAR+" INTEGER,"
                         +Player.KEY_USER_MONTH+" INTEGER,"
                         +Player.KEY_USER_DAY+" INTEGER);";
        db.execSQL(CREATE_TABLE_PLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists " +
                Player.TABLE_NAME+";");
        onCreate(db);
    }
}
