package com.example.railwayenquiry.Adapters;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_table")
public class TimeTableItem {

    @PrimaryKey(autoGenerate = true)
    public int key;
    @ColumnInfo(name = "station_name")
    public String station_name;
    @ColumnInfo(name = "arrival_time")
    public String arrival_time;
    @ColumnInfo(name = "departure_time")
    public String departure_time;
    @ColumnInfo(name = "visited")
    public boolean visited;
    @ColumnInfo(name = "station_code")
    public String station_code;


    public TimeTableItem(String train_name, String arrival_time, String departure_time, boolean visited, String station_code) {
        this.station_name=train_name;
        this.arrival_time=arrival_time;
        this.departure_time=departure_time;
        this.visited=visited;
        this.station_code=station_code;
    }
    public TimeTableItem(){}


    public String getStation_name() {
        return station_name;
    }
    public void setTrain_name(String train_name) {
        this.station_name = train_name;
    }


    public String getArrival_time() {
        return arrival_time;
    }
    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }


    public String getDeparture_time() {
        return departure_time;
    }
    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }


    public boolean getVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getStation_code(){return station_code;}
    public void setStation_code(String station_code){this.station_code=station_code;}



}
