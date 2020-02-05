package com.example.railwayenquiry.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.railwayenquiry.Adapters.CancelItem;

import java.util.List;

@Dao
public interface CancelDao {
    @Insert
    void insert(CancelItem tt);

    @Query("DELETE FROM cancel")
    void deleteAll();

    @Query("SELECT * from cancel")
    LiveData<List<CancelItem>> getAllRows();

}
