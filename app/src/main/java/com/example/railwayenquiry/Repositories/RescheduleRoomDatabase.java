package com.example.railwayenquiry.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.railwayenquiry.Adapters.RescheduleItem;
import com.example.railwayenquiry.Adapters.TrainItem;
import com.example.railwayenquiry.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Database(entities = {RescheduleItem.class}, version = 1, exportSchema = false)
public abstract class RescheduleRoomDatabase extends RoomDatabase {


    public abstract RescheduleDao trainDao();

    private static RescheduleRoomDatabase INSTANCE;

    public static RescheduleRoomDatabase getDatabase(final Context context) {
            synchronized (RescheduleRoomDatabase.class) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RescheduleRoomDatabase.class, "reschedule_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(rRoomDatabaseCallback)
                            .build();
                    INSTANCE.trainDao().deleteAll();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback rRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new rPopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class rPopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RescheduleDao mDao;
        String train_name, train_no, rescheduled_time, rescheduled_date;

        rPopulateDbAsync(RescheduleRoomDatabase db) {
            mDao = db.trainDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {
                mDao.deleteAll();


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(new Date());
                String key= BuildConfig.APIKEY;
                String url="https://api.railwayapi.com/v2/rescheduled/date/"+date+"/apikey/"+key+"/";


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
                String jsonstring2=response.body().string();
                Log.d("RescheduleResponse: ",jsonstring2);



                JSONObject js = new JSONObject(jsonstring2);
                JSONArray jsonArray = new JSONArray();


                jsonArray = js.getJSONArray("trains");
                JSONObject temp = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {

                    temp = jsonArray.getJSONObject(i);
                    train_name = temp.getString("name");
                    train_no = temp.getString("number");
                    rescheduled_time = temp.getString("rescheduled_time");
                    rescheduled_date= temp.getString("rescheduled_date");


                    RescheduleItem word = new RescheduleItem(train_name, train_no, rescheduled_time, rescheduled_date);
                    mDao.insert(word);
                    Log.d("RescheduleRoomDatabase:", "Item Insertion Reached");

                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                RescheduleItem word = new RescheduleItem("Server Down","Not Available","","");
                mDao.insert(word);

                return null;
            }
        }
    }
}
