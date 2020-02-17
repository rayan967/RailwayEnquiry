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
import com.example.railwayenquiry.Adapters.TrainItem;
import com.example.railwayenquiry.BuildConfig;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Database(entities = {CancelItem.class}, version = 1, exportSchema = false)
public abstract class CancelRoomDatabase extends RoomDatabase {
    public abstract CancelDao trainDao();
    private static CancelRoomDatabase INSTANCE;


    public static CancelRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CancelRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CancelRoomDatabase.class, "cancel_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(cRoomDatabaseCallback)
                            .build();
                    INSTANCE.trainDao().deleteAll();
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

            try{
                Thread.sleep(5000);

                mDao.deleteAll();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(new Date());
            String key= BuildConfig.APIKEY;
            String url="https://api.railwayapi.com/v2/cancelled/date/"+date+"/apikey/"+key+"/";


            Log.d("Request: ",url);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
                if(!response.isSuccessful())
                {
                    throw new JSONException("Server Not Responding");
                }
            String jsonstring3=response.body().string();
            Log.d("CancelResponse: ",jsonstring3);


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
                CancelItem word = new CancelItem("Server Down","Not Available","");

                mDao.insert(word);

                return null;
            }
        }
    }

}
