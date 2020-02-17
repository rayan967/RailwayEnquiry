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
import com.example.railwayenquiry.BuildConfig;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Database(entities = {PNRItem.class}, version = 1, exportSchema = false)
public abstract class PNRRoomDatabase extends RoomDatabase {
    public abstract PNRDao pnrDao();
    private static PNRRoomDatabase INSTANCE;
    public static String jsonstring="{ \"pnr\": \"1234567890\", \"journeyDetails\": { \"trainNumber\": \"22629\", \"trainName\": \"DADAR TEN EXP\", \"boardingDate\": \"05-02-2020\", \"from\": \"DR\", \"to\": \"KKW\", \"reservedUpto\": \"KKW\", \"boardingPoint\": \"DR\", \"class\": \"SL\" }, \"lastUpdated\": \"01-02-2020 15:34\", \"bookingStatus\": [ { \"passengerNo\": \"1\", \"bookingStatus\": \"W/L 171,PQWL\", \"currentStatus\": \"W/L 137\" }, { \"passengerNo\": \"2\", \"bookingStatus\": \"W/L 172,PQWL\", \"currentStatus\": \"W/L 138\" }, { \"passengerNo\": \"3\", \"bookingStatus\": \"W/L 173,PQWL\", \"currentStatus\": \"W/L 139\" }, { \"passengerNo\": \"4\", \"bookingStatus\": \"W/L 174,PQWL\", \"currentStatus\": \"W/L 140\" }, { \"passengerNo\": \"5\", \"bookingStatus\": \"W/L 175,PQWL\", \"currentStatus\": \"W/L 141\" }, { \"passengerNo\": \"6\", \"bookingStatus\": \"W/L 176,PQWL\", \"currentStatus\": \"W/L 142\" } ], \"chartingStatus\": \"CHART NOT PREPARED\" }";
    //To Demonstrate UI in case 3rd party API is down

    public static PNRRoomDatabase getDatabase(final Context context, String pnr) throws IllegalStateException {
            synchronized (PNRRoomDatabase.class) {

                 RoomDatabase.Callback sRoomDatabaseCallback =
                        new RoomDatabase.Callback(){

                            @Override
                            public void onOpen (@NonNull SupportSQLiteDatabase db){
                                super.onOpen(db);
                                new PopulateDbAsync(INSTANCE,pnr).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }
                        };


                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PNRRoomDatabase.class, "pnr_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback( sRoomDatabaseCallback )
                            .build();
                INSTANCE.pnrDao().deleteAll();

        }
        return INSTANCE;
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PNRDao mDao;
        String bookingstatus, currentstatus,pnr;
        PopulateDbAsync(PNRRoomDatabase db, String pnr) {
            this.pnr=pnr;
            mDao = db.pnrDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                if(!pnr.equals("1234567890")) { //To Demonstrate UI in case 3rd party API is down
                    String key = BuildConfig.PNRAPIKEY;
                    String url = "https://indianrailways.p.rapidapi.com/index.php?pnr=" + pnr;
                    OkHttpClient client = new OkHttpClient();
                    Log.d("RequestPNR: ", url);

                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .addHeader("x-rapidapi-host", "indianrailways.p.rapidapi.com")
                            .addHeader("x-rapidapi-key", key)
                            .build();
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful())
                        throw new JSONException("Schedule unavailable");

                    jsonstring = response.body().string();
                }

                Log.d("ResponsePNR: ",jsonstring);



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
                PNRItem word = new PNRItem("Details not found");
                mDao.insert(word);
                return null;
            }
        }
    }
}
