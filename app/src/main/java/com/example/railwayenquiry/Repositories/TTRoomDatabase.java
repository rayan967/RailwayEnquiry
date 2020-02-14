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
    public static String jsonstring="{\"route\": [{\"distance\": 0.0, \"scharr\": \"SOURCE\", \"halt\": -1, \"no\": 1, \"schdep\": \"12:45\", \"day\": 1, \"station\": {\"lng\": 72.8354475, \"name\": \"C SHIVAJI MAHARAJ T\", \"code\": \"CSMT\", \"lat\": 18.9398446}}, {\"distance\": 9.0, \"scharr\": \"12:57\", \"halt\": 3, \"no\": 2, \"schdep\": \"13:00\", \"day\": 1, \"station\": {\"lng\": 72.84781199999999, \"name\": \"DADAR\", \"code\": \"DR\", \"lat\": 19.0177989}}, {\"distance\": 53.0, \"scharr\": \"13:37\", \"halt\": 3, \"no\": 3, \"schdep\": \"13:40\", \"day\": 1, \"station\": {\"lng\": 73.1305395, \"name\": \"KALYAN JN\", \"code\": \"KYN\", \"lat\": 19.2403305}}, {\"distance\": 100.0, \"scharr\": \"14:18\", \"halt\": 2, \"no\": 4, \"schdep\": \"14:20\", \"day\": 1, \"station\": {\"lng\": 73.3304703, \"name\": \"KARJAT\", \"code\": \"KJT\", \"lat\": 18.9107981}}, {\"distance\": 128.0, \"scharr\": \"15:07\", \"halt\": 3, \"no\": 5, \"schdep\": \"15:10\", \"day\": 1, \"station\": {\"lng\": 73.4090757, \"name\": \"LONAVALA\", \"code\": \"LNL\", \"lat\": 18.7557237}}, {\"distance\": 192.0, \"scharr\": \"16:30\", \"halt\": 5, \"no\": 6, \"schdep\": \"16:35\", \"day\": 1, \"station\": {\"lng\": 73.8567437, \"name\": \"PUNE JN\", \"code\": \"PUNE\", \"lat\": 18.5204303}}, {\"distance\": 220.0, \"scharr\": \"17:04\", \"halt\": 1, \"no\": 7, \"schdep\": \"17:05\", \"day\": 1, \"station\": {\"lng\": 74.1333715, \"name\": \"URULI\", \"code\": \"URI\", \"lat\": 18.4874257}}, {\"distance\": 245.0, \"scharr\": \"17:24\", \"halt\": 1, \"no\": 8, \"schdep\": \"17:25\", \"day\": 1, \"station\": {\"lng\": 74.70894489999999, \"name\": \"KEDGAON\", \"code\": \"KDG\", \"lat\": 19.0645141}}, {\"distance\": 267.0, \"scharr\": \"18:10\", \"halt\": 5, \"no\": 9, \"schdep\": \"18:15\", \"day\": 1, \"station\": {\"lng\": 74.5873348, \"name\": \"DAUND JN\", \"code\": \"DD\", \"lat\": 18.4592892}}, {\"distance\": 294.0, \"scharr\": \"18:38\", \"halt\": 2, \"no\": 10, \"schdep\": \"18:40\", \"day\": 1, \"station\": {\"lng\": 74.75425709999999, \"name\": \"BHIGWAN\", \"code\": \"BGVN\", \"lat\": 18.2989478}}, {\"distance\": 341.0, \"scharr\": \"19:15\", \"halt\": 2, \"no\": 11, \"schdep\": \"19:17\", \"day\": 1, \"station\": {\"lng\": 75.1632937, \"name\": \"JEUR\", \"code\": \"JEUR\", \"lat\": 18.260227}}, {\"distance\": 357.0, \"scharr\": \"19:40\", \"halt\": 2, \"no\": 12, \"schdep\": \"19:42\", \"day\": 1, \"station\": {\"lng\": -94.6045539, \"name\": \"KEM\", \"code\": \"KEM\", \"lat\": 39.09518}}, {\"distance\": 376.0, \"scharr\": \"20:08\", \"halt\": 2, \"no\": 13, \"schdep\": \"20:10\", \"day\": 1, \"station\": {\"lng\": 75.4221007, \"name\": \"KURDUVADI\", \"code\": \"KWV\", \"lat\": 18.0963477}}, {\"distance\": 454.0, \"scharr\": \"22:15\", \"halt\": 5, \"no\": 14, \"schdep\": \"22:20\", \"day\": 1, \"station\": {\"lng\": 75.9063906, \"name\": \"SOLAPUR JN\", \"code\": \"SUR\", \"lat\": 17.6599188}}, {\"distance\": 518.0, \"scharr\": \"23:28\", \"halt\": 2, \"no\": 15, \"schdep\": \"23:30\", \"day\": 1, \"station\": {\"lng\": 73.1686859, \"name\": \"DUDHANI\", \"code\": \"DUD\", \"lat\": 20.176971}}, {\"distance\": 567.0, \"scharr\": \"00:27\", \"halt\": 3, \"no\": 16, \"schdep\": \"00:30\", \"day\": 2, \"station\": {\"lng\": 76.8342957, \"name\": \"KALABURAGI\", \"code\": \"KLBG\", \"lat\": 17.329731}}, {\"distance\": 593.0, \"scharr\": \"00:58\", \"halt\": 2, \"no\": 17, \"schdep\": \"01:00\", \"day\": 2, \"station\": {\"lng\": 79.9447096, \"name\": \"SHAHABAD\", \"code\": \"SDB\", \"lat\": 27.6441382}}, {\"distance\": 603.0, \"scharr\": \"01:30\", \"halt\": 5, \"no\": 18, \"schdep\": \"01:35\", \"day\": 2, \"station\": {\"lng\": 58.4977931, \"name\": \"WADI\", \"code\": \"WADI\", \"lat\": 23.2006924}}, {\"distance\": 618.0, \"scharr\": \"01:54\", \"halt\": 1, \"no\": 19, \"schdep\": \"01:55\", \"day\": 2, \"station\": {\"lng\": 77.0830256, \"name\": \"CHITTAPUR\", \"code\": \"CT\", \"lat\": 17.1181765}}, {\"distance\": 628.0, \"scharr\": \"02:04\", \"halt\": 1, \"no\": 20, \"schdep\": \"02:05\", \"day\": 2, \"station\": {\"lng\": 77.1726159, \"name\": \"MALKHAID ROAD\", \"code\": \"MQR\", \"lat\": 17.1468018}}, {\"distance\": 640.0, \"scharr\": \"02:14\", \"halt\": 1, \"no\": 21, \"schdep\": \"02:15\", \"day\": 2, \"station\": {\"lng\": 129.4864411, \"name\": \"SERAM\", \"code\": \"SEM\", \"lat\": -3.0166501}}, {\"distance\": 673.0, \"scharr\": \"02:59\", \"halt\": 1, \"no\": 22, \"schdep\": \"03:00\", \"day\": 2, \"station\": {\"lng\": -80.82464809999999, \"name\": \"TANDUR\", \"code\": \"TDU\", \"lat\": 35.1567867}}, {\"distance\": 715.0, \"scharr\": \"03:54\", \"halt\": 1, \"no\": 23, \"schdep\": \"03:55\", \"day\": 2, \"station\": {\"lng\": 77.9048483, \"name\": \"VIKARABAD JN\", \"code\": \"VKB\", \"lat\": 17.3364298}}, {\"distance\": 764.0, \"scharr\": \"04:33\", \"halt\": 1, \"no\": 24, \"schdep\": \"04:34\", \"day\": 2, \"station\": {\"lng\": 78.3177875, \"name\": \"LINGAMPALLI\", \"code\": \"LPI\", \"lat\": 17.4830213}}, {\"distance\": 782.0, \"scharr\": \"04:57\", \"halt\": 1, \"no\": 25, \"schdep\": \"04:58\", \"day\": 2, \"station\": {\"lng\": 78.4663812, \"name\": \"BEGAMPET\", \"code\": \"BMT\", \"lat\": 17.4447068}}, {\"distance\": 788.0, \"scharr\": \"05:55\", \"halt\": -1, \"no\": 26, \"schdep\": \"DEST\", \"day\": 2, \"station\": {\"lng\": 78.4675753, \"name\": \"HYDERABAD DECAN\", \"code\": \"HYB\", \"lat\": 17.3924556}}], \"train\": {\"classes\": [{\"available\": \"N\", \"name\": \"FIRST CLASS\", \"code\": \"FC\"}, {\"available\": \"Y\", \"name\": \"SECOND AC\", \"code\": \"2A\"}, {\"available\": \"N\", \"name\": \"SECOND SEATING\", \"code\": \"2S\"}, {\"available\": \"Y\", \"name\": \"SLEEPER CLASS\", \"code\": \"SL\"}, {\"available\": \"N\", \"name\": \"FIRST AC\", \"code\": \"1A\"}, {\"available\": \"Y\", \"name\": \"THIRD AC\", \"code\": \"3A\"}, {\"available\": \"N\", \"name\": \"AC CHAIR CAR\", \"code\": \"CC\"}, {\"available\": \"N\", \"name\": \"3rd AC ECONOMY\", \"code\": \"3E\"}], \"number\": \"17031\", \"name\": \"MUMBAI CSMT-HYB EXP\", \"days\": [{\"runs\": \"Y\", \"code\": \"MON\"}, {\"runs\": \"Y\", \"code\": \"TUE\"}, {\"runs\": \"Y\", \"code\": \"WED\"}, {\"runs\": \"Y\", \"code\": \"THU\"}, {\"runs\": \"Y\", \"code\": \"FRI\"}, {\"runs\": \"Y\", \"code\": \"SAT\"}, {\"runs\": \"Y\", \"code\": \"SUN\"}]}, \"response_code\": 200, \"debit\": 1}";

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
