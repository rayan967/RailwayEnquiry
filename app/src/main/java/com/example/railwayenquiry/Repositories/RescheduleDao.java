package com.example.railwayenquiry.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.railwayenquiry.Adapters.RescheduleItem;


import java.util.List;

@Dao
public interface RescheduleDao {
    @Insert
    void insert(RescheduleItem tt);

    @Query("DELETE FROM reschedule")
    void deleteAll();

    @Query("SELECT * from reschedule")
    LiveData<List<RescheduleItem>> getAllRows();

}
