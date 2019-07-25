package com.apptask;

import android.app.Application;
import androidx.room.Room;

import com.apptask.room.MyDatabase;

public class App extends Application {


   private MyDatabase myDatabase;

    public static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        myDatabase = Room.databaseBuilder(this,
                MyDatabase.class, "mydatabase").
                allowMainThreadQueries().
                build();
    }

    public static App getInstance() {
        return instance;
    }

    public MyDatabase getDatabase()
    {
        return  myDatabase;
    }
}
