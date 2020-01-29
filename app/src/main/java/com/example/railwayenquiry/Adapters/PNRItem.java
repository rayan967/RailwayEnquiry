package com.example.railwayenquiry.Adapters;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pnr")
public class PNRItem {

    @PrimaryKey(autoGenerate = true)
    public int key;
    @ColumnInfo(name = "status")
    public String status;


    public PNRItem(String status) {
        this.status=status;
    }
    public PNRItem(){}

    public String getStatus(){return status;}

    public void setStatus(String status){ this.status=status;}


}
