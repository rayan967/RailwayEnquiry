package com.example.railwayenquiry.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.railwayenquiry.Adapters.PNRItem;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Database(entities = {PNRItem.class}, version = 1, exportSchema = false)
public abstract class PNRRoomDatabase extends RoomDatabase {
    public abstract PNRDao pnrDao();
    private static PNRRoomDatabase INSTANCE;
    public static String jsonstring="{ \"pnr\": \"1234567890\", \"journeyDetails\": { \"trainNumber\": \"22629\", \"trainName\": \"DADAR TEN EXP\", \"boardingDate\": \"2-5-2014\", \"from\": \"DR\", \"to\": \"KKW\", \"reservedUpto\": \"KKW\", \"boardingPoint\": \"DR\", \"class\": \"SL\" }, \"lastUpdated\": \"9-3-2014 15:34\", \"bookingStatus\": [ { \"passengerNo\": \"1\", \"bookingStatus\": \"W/L 171,PQWL\", \"currentStatus\": \"W/L 137\" }, { \"passengerNo\": \"2\", \"bookingStatus\": \"W/L 172,PQWL\", \"currentStatus\": \"W/L 138\" }, { \"passengerNo\": \"3\", \"bookingStatus\": \"W/L 173,PQWL\", \"currentStatus\": \"W/L 139\" }, { \"passengerNo\": \"4\", \"bookingStatus\": \"W/L 174,PQWL\", \"currentStatus\": \"W/L 140\" }, { \"passengerNo\": \"5\", \"bookingStatus\": \"W/L 175,PQWL\", \"currentStatus\": \"W/L 141\" }, { \"passengerNo\": \"6\", \"bookingStatus\": \"W/L 176,PQWL\", \"currentStatus\": \"W/L 142\" } ], \"chartingStatus\": \"CHART NOT PREPARED\" }";

    public static PNRRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PNRRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PNRRoomDatabase.class, "pnr_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PNRDao mDao;
        String bookingstatus, currentstatus;
        PopulateDbAsync(PNRRoomDatabase db) {
            mDao = db.pnrDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                mDao.deleteAll();
                JSONObject js = new JSONObject(jsonstring);
                JSONArray jsonArray=new JSONArray();


                jsonArray=js.getJSONArray("bookingStatus");
                JSONObject temp=new JSONObject();

                for(int i=0;i<jsonArray.length();i++) {

                    temp = jsonArray.getJSONObject(i);
                    bookingstatus=temp.getString("bookingStatus");
                    currentstatus=temp.getString("currentStatus");

                    String status= "Booking Status: "+bookingstatus+"\n"+"Current Status: "+currentstatus;

                    PNRItem word = new PNRItem(status);
                    mDao.insert(word);

                }

                return null;
            }
            catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
