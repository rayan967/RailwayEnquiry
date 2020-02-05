package com.example.railwayenquiry.Adapters;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cancel")
public class CancelItem {

    @PrimaryKey(autoGenerate = true)
    public int key;
    @ColumnInfo(name = "train_name")
    public String train_name;
    @ColumnInfo(name = "train_no")
    public String train_no;
    @ColumnInfo(name = "start_time")
    public String start_time;




    public CancelItem(String train_name, String train_no, String start_time) {
        this.train_name=train_name;
        this.train_no=train_no;
        this.start_time=start_time;
    }
    public CancelItem(){}


    public String getTrain_name() {
        return train_name;
    }
    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getTrain_no() {
        return train_no;
    }
    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }



    public String getArrival_time() {
        return start_time;
    }
    public void setArrival_time(String start_time) {
        this.start_time = start_time;
    }



}
