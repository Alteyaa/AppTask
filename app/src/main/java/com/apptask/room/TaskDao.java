package com.apptask.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.apptask.model.Task;

import java.util.List;

@Dao
public interface TaskDao {


        @Query("SELECT * FROM Task ORDER BY id DESC")
        LiveData<List<Task>> getAll();

        @Update
        void update (Task task);

        @Insert
        void insert(Task task);

        @Delete
        void delete(Task task);


    }

