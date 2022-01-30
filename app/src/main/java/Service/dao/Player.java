package Service.dao;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

public class Player implements Parcelable {
    public static final String TABLE_NAME = "Player";
    public static final String KEY_ID = "Player_id";
    public static final String KEY_USER_NAME = "User_name";
    public static final String KEY_USER_PASS = "User_pass";

    public String user_name;
    public String user_pass;
    public int y;
    public int m;
    public int d;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }


    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Player() {
    }

    public Player(String username,String userpass){
        this.user_name = username;
        this.user_pass = userpass;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(user_name);
        parcel.writeString(user_pass);
    }

    public Player(@NonNull Parcel parcel){
        user_name = parcel.readString();
        user_pass = parcel.readString();
    }
}