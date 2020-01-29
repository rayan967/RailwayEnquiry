package com.example.railwayenquiry.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Dao;

import com.example.railwayenquiry.Adapters.PNRItem;


import java.util.List;
@Dao
public interface PNRDao {

    @Insert
    void insert(PNRItem tt);

    @Query("DELETE FROM pnr")
    void deleteAll();

    @Query("SELECT * from pnr")
    LiveData<List<PNRItem>> getAllRows();
}
