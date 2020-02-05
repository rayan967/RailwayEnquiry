package com.example.railwayenquiry.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.railwayenquiry.Adapters.CancelItem;


import org.json.JSONArray;
import org.json.JSONObject;


@Database(entities = {CancelItem.class}, version = 1, exportSchema = false)
public abstract class CancelRoomDatabase extends RoomDatabase {
    public abstract CancelDao trainDao();
    public static String jsonstring3="{\"total\": 287, \"response_code\": 200, \"debit\": 1, \"trains\": [{\"number\": \"06521\", \"type\": null, \"dest\": {\"code\": \"DMM\", \"lng\": 77.71261890000001, \"lat\": 14.4137447, \"name\": \"DHARMAVARAM JN\"}, \"source\": {\"code\": \"BNC\", \"lng\": 77.5945627, \"lat\": 12.9715987, \"name\": \"BANGALORE CANT\"}, \"start_time\": \"07:20\", \"name\": \"BNC-DMM MEMU SPECIAL\"}, {\"number\": \"06565\", \"type\": null, \"dest\": {\"code\": \"HUP\", \"lng\": 77.4988753, \"lat\": 13.8185378, \"name\": \"HINDUPUR\"}, \"source\": {\"code\": \"SBC\", \"lng\": 77.5695295, \"lat\": 12.9781291, \"name\": \"KSR BENGALURU\"}, \"start_time\": \"09:30\", \"name\": \"SBC-HUP MEMU SPL\"}, {\"number\": \"06566\", \"type\": null, \"dest\": {\"code\": \"SBC\", \"lng\": 77.5695295, \"lat\": 12.9781291, \"name\": \"KSR BENGALURU\"}, \"source\": {\"code\": \"HUP\", \"lng\": 77.4988753, \"lat\": 13.8185378, \"name\": \"HINDUPUR\"}, \"start_time\": \"13:00\", \"name\": \"HUP-SBC MEMU SPL\"}, {\"number\": \"06571\", \"type\": null, \"dest\": {\"code\": \"HSRA\", \"lng\": 77.8252923, \"lat\": 12.7409127, \"name\": \"HOSUR\"}, \"source\": {\"code\": \"BAND\", \"lng\": 77.6481944, \"lat\": 13.0103761, \"name\": \"BANASWADI\"}, \"start_time\": \"09:50\", \"name\": \"BAND-HSRA DEMU SPL\"}, {\"number\": \"06572\", \"type\": null, \"dest\": {\"code\": \"BAND\", \"lng\": 77.6481944, \"lat\": 13.0103761, \"name\": \"BANASWADI\"}, \"source\": {\"code\": \"HSRA\", \"lng\": 77.8252923, \"lat\": 12.7409127, \"name\": \"HOSUR\"}, \"start_time\": \"11:15\", \"name\": \"HSRA-BAND-DEMU SPL\"}, {\"number\": \"06573\", \"type\": null, \"dest\": {\"code\": \"HSRA\", \"lng\": 77.8252923, \"lat\": 12.7409127, \"name\": \"HOSUR\"}, \"source\": {\"code\": \"BAND\", \"lng\": 77.6481944, \"lat\": 13.0103761, \"name\": \"BANASWADI\"}, \"start_time\": \"12:40\", \"name\": \"BAND-HSRA DEMU SPL\"}, {\"number\": \"06574\", \"type\": null, \"dest\": {\"code\": \"BAND\", \"lng\": 77.6481944, \"lat\": 13.0103761, \"name\": \"BANASWADI\"}, \"source\": {\"code\": \"HSRA\", \"lng\": 77.8252923, \"lat\": 12.7409127, \"name\": \"HOSUR\"}, \"start_time\": \"15:20\", \"name\": \"HSRA-BAND DEMU SPL\"}]}";
    private static CancelRoomDatabase INSTANCE;


    public static CancelRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CancelRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CancelRoomDatabase.class, "cancel_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(cRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback cRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new cPopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class cPopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CancelDao mDao;
        String train_name, train_no, time;

        cPopulateDbAsync(CancelRoomDatabase db) {
            mDao = db.trainDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                mDao.deleteAll();
                JSONObject js = new JSONObject(jsonstring3);
                JSONArray jsonArray = new JSONArray();


                jsonArray = js.getJSONArray("trains");
                JSONObject temp = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {

                    temp = jsonArray.getJSONObject(i);
                    train_name = temp.getString("name");
                    train_no = temp.getString("number");
                    time = temp.getString("start_time");


                    CancelItem word = new CancelItem(train_name, train_no, time);
                    Log.d("CancelRoomDatabase:", "Item Insertion Reached");
                    mDao.insert(word);

                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
