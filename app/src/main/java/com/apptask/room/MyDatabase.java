package com.apptask.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.apptask.model.Task;


@Database(entities = {Task.class}, version = 1, exportSchema = false)
public  abstract class MyDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
