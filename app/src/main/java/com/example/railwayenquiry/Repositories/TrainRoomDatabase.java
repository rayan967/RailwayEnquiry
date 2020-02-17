package com.example.railwayenquiry.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.railwayenquiry.Adapters.TrainItem;
import com.example.railwayenquiry.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Database(entities = {TrainItem.class}, version = 1, exportSchema = false)
public abstract class TrainRoomDatabase extends RoomDatabase {
    public abstract TrainDao trainDao();

    private static TrainRoomDatabase INSTANCE;
    public static String[] contexts={"StationScheduleFragment2.class", "RPageFragment.class","CPageFragment.class"};

    public static TrainRoomDatabase getDatabase(final Context context, String station) {
            synchronized (TrainRoomDatabase.class) {

                  RoomDatabase.Callback sRoomDatabaseCallback =
                        new RoomDatabase.Callback() {

                            @Override
                            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                super.onOpen(db);
                                new PopulateDbAsync(INSTANCE,station).execute();
                            }
                        };



                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TrainRoomDatabase.class, "train_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback( sRoomDatabaseCallback )
                            .build();
                INSTANCE.trainDao().deleteAll();
        }
        return INSTANCE;
    }



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TrainDao mDao;
        String train_name, train_no, arrival_time, departure_time,station;

        PopulateDbAsync(TrainRoomDatabase db,String station) {
            this.station=station;
            mDao = db.trainDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                String key= BuildConfig.APIKEY;
                String url="https://api.railwayapi.com/v2/arrivals/station/"+station+"/hours/24/apikey/"+key+"/";


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
                String jsonstring=response.body().string();
                Log.d("Response: ",jsonstring);

                mDao.deleteAll();
                JSONObject js = new JSONObject(jsonstring);
                JSONArray jsonArray = new JSONArray();


                jsonArray = js.getJSONArray("trains");
                JSONObject temp = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {

                    temp = jsonArray.getJSONObject(i);
                    train_name = temp.getString("name");
                    train_no = temp.getString("number");
                    arrival_time = temp.getString("actarr");
                    departure_time = temp.getString("actdep");


                    TrainItem word = new TrainItem(train_name, train_no, arrival_time, departure_time);
                    mDao.insert(word);

                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                TrainItem word = new TrainItem("Server Down","Not Available","","");

                mDao.insert(word);
                return null;
            }
        }
    }

}
