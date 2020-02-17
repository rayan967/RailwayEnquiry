package com.example.railwayenquiry.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.railwayenquiry.Adapters.TimeTableItem;
import com.example.railwayenquiry.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Database(entities = {TimeTableItem.class}, version = 1, exportSchema = false)
public abstract class TTRoomDatabase extends RoomDatabase {
    public abstract TTDao ttDao();
    private static TTRoomDatabase INSTANCE;

    public static TTRoomDatabase getDatabase(final Context context, String train_no) {
            synchronized (TTRoomDatabase.class) {

                     RoomDatabase.Callback sRoomDatabaseCallback =
                            new RoomDatabase.Callback(){

                                @Override
                                public void onOpen (@NonNull SupportSQLiteDatabase db){
                                    super.onOpen(db);
                                    new PopulateDbAsync(INSTANCE,train_no).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                }
                            };

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TTRoomDatabase.class, "tt_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    INSTANCE.ttDao().deleteAll();
            }

        return INSTANCE;
    }



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TTDao mDao;
        String arrival,departure,name;
        String schedule;
        boolean visited=true;
        int day;
        String train_no;
        String station_no;

        PopulateDbAsync(TTRoomDatabase db, String train_no) {
            this.train_no=train_no;
            mDao = db.ttDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {





                String key= BuildConfig.APIKEY;
                String url="https://api.railwayapi.com/v2/route/train/"+train_no+"/apikey/"+key+"/";

                Log.d("Request: ",url);

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                String jsonstring=response.body().string();
                Log.d("TTResponse ",jsonstring);



                if(jsonstring.charAt(0)=='<')
                    throw new Exception("");

                JSONObject js = new JSONObject(jsonstring);
                JSONArray jsonArray=new JSONArray();

                if(!js.getString("response_code").equals("200"))
                    throw new JSONException("Response Code is: "+js.getString("response_code"));


                JSONArray days=js.getJSONObject("train").getJSONArray("days"); //To check running schedule of train
                int count=0;
                if(days.length()==7)
                    for(int i=0;i<7;i++){
                        if(days.getJSONObject(i).getString("runs").equalsIgnoreCase("Y"))
                            count++;
                    }
                Log.d("Count: ", Integer.toString(count));
                if(count==7)
                    schedule="Daily";
                else if(count<7&&count>=1)
                    schedule="Weekly";
                else
                    schedule="Train not available";


                Log.d("Count: ", schedule);
                jsonArray=js.getJSONArray("route");
                JSONObject temp=new JSONObject();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                SimpleDateFormat sdfs = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                String scheduledTime;
                Calendar cal = Calendar.getInstance();
                Log.d("Time:",currentTime);
                Date date1 = sdf.parse(currentTime);
                Date date2;

                for(int i=0;i<jsonArray.length();i++) {

                    temp = jsonArray.getJSONObject(i);
                    arrival = temp.getString("scharr");
                    departure = temp.getString("schdep");
                    day=temp.getInt("day")-1;
                    temp = temp.getJSONObject("station");
                    name = temp.getString("name");
                    station_no= temp.getString("code");


                    if(!schedule.equalsIgnoreCase("Daily"))
                        visited=false;

                    else if(!arrival.equalsIgnoreCase("SOURCE")) {
                        cal.add(Calendar.DAY_OF_MONTH,day);
                        scheduledTime = sdfs.format(cal.getTime());
                        scheduledTime = scheduledTime+" "+arrival;
                        date2 = sdf.parse(scheduledTime);
                        if (date1.compareTo(date2) >= 0) {
                            visited = true;

                        } else if (date1.compareTo(date2) < 0) {
                            visited = false;
                        }
                    }
                    TimeTableItem word = new TimeTableItem(name, arrival, departure, visited, station_no);
                    mDao.insert(word);

                }

                return null;
            }
            catch (JSONException e){
                TimeTableItem word = new TimeTableItem("","","",false,"");
                mDao.insert(word);
                return null;
            }
            catch(Exception e) {
                TimeTableItem word = new TimeTableItem("","","",false,"");
                mDao.insert(word);
                e.printStackTrace();
                return null;
            }
        }
    }
}
