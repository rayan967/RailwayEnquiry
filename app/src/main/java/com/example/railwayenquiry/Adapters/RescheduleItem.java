package com.example.railwayenquiry.Adapters;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reschedule")
public class RescheduleItem {

    @PrimaryKey(autoGenerate = true)
    public int key;
    @ColumnInfo(name = "train_name")
    public String train_name;
    @ColumnInfo(name = "train_no")
    public String train_no;
    @ColumnInfo(name = "arrival_time")
    public String arrival_time;
    @ColumnInfo(name="arrival_date")
    public String arrival_date;




    public RescheduleItem(String train_name, String train_no, String arrival_time, String arrival_date) {
        this.train_name=train_name;
        this.train_no=train_no;
        this.arrival_time=arrival_time;
        this.arrival_date=arrival_date;
    }
    public RescheduleItem(){}


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
        return arrival_time;
    }
    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }


    public String getArrival_date() {
        return arrival_date;
    }
    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

}
