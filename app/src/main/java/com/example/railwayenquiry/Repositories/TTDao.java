package com.example.railwayenquiry.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.railwayenquiry.Adapters.TimeTableItem;

import java.util.List;

@Dao
public interface TTDao {

    @Insert
    void insert(TimeTableItem tt);

    @Query("DELETE FROM time_table")
    void deleteAll();

    @Query("SELECT * from time_table")
    LiveData<List<TimeTableItem>> getAllRows();

}
