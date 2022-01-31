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
    public static final String KEY_USER_YEAR = "year";
    public static final String KEY_USER_MONTH = "month";
    public static final String KEY_USER_DAY = "day";
    public String user_name;
    public String user_pass;
    public int year;
    public int month;
    public int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public Player(String username,String userpass,int year,int month,int day){
        this.user_name = username;
        this.user_pass = userpass;
        this.year = year;
        this.month = month;
        this.day = day;
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
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(day);
    }

    public Player(@NonNull Parcel parcel){
        user_name = parcel.readString();
        user_pass = parcel.readString();
        year = parcel.readInt();
        month = parcel.readInt();
        day = parcel.readInt();
    }
}