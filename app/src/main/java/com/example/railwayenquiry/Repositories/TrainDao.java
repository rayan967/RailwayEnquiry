package com.example.railwayenquiry.Repositories;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.railwayenquiry.Adapters.TrainItem;

import java.util.List;


@Dao
public interface TrainDao {
    @Insert
    void insert(TrainItem tt);

    @Query("DELETE FROM train")
    void deleteAll();

    @Query("SELECT * from train")
    LiveData<List<TrainItem>> getAllRows();

}
