package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.railwayenquiry.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PropertiesRepository {

    private MutableLiveData<HashMap<String,String>> mProperties=new MutableLiveData<>();
    public static String jsonstring = "{\"train\": {\"classes\": [{\"available\": \"N\", \"name\": \"FIRST CLASS\", \"code\": \"FC\"}, {\"available\": \"Y\", \"name\": \"SECOND AC\", \"code\": \"2A\"}, {\"available\": \"N\", \"name\": \"SECOND SEATING\", \"code\": \"2S\"}, {\"available\": \"Y\", \"name\": \"SLEEPER CLASS\", \"code\": \"SL\"}, {\"available\": \"N\", \"name\": \"FIRST AC\", \"code\": \"1A\"}, {\"available\": \"Y\", \"name\": \"THIRD AC\", \"code\": \"3A\"}, {\"available\": \"N\", \"name\": \"AC CHAIR CAR\", \"code\": \"CC\"}, {\"available\": \"N\", \"name\": \"3rd AC ECONOMY\", \"code\": \"3E\"}], \"number\": \"17031\", \"name\": \"MUMBAI CSMT-HYB EXP\", \"days\": [{\"runs\": \"Y\", \"code\": \"MON\"}, {\"runs\": \"Y\", \"code\": \"TUE\"}, {\"runs\": \"Y\", \"code\": \"WED\"}, {\"runs\": \"Y\", \"code\": \"THU\"}, {\"runs\": \"Y\", \"code\": \"FRI\"}, {\"runs\": \"Y\", \"code\": \"SAT\"}, {\"runs\": \"Y\", \"code\": \"SUN\"}]}, \"status\": {\"scharr_date\": \"Source\", \"actarr\": \"Source\", \"scharr\": \"Source\", \"latemin\": null, \"has_departed\": true, \"has_arrived\": false, \"actarr_date\": \"Source\", \"schdep\": \"12:45\", \"actdep\": \"12:46\"}, \"response_code\": 200, \"position\": \"Departed from Bale at 22:18 22-Jan. 451 Kms. ahead of C Shivaji Maharaj T.\", \"start_date\": \"22-01-2020\", \"station\": {\"lng\": 72.8354475, \"name\": \"C SHIVAJI MAHARAJ T\", \"code\": \"CSMT\", \"lat\": 18.9398446}, \"debit\": 2}";
    String train_no,date,first_station;
    public PropertiesRepository(Application application, String train_no, String date)
    {
        this.train_no=train_no;
        this.date=date;
        try {
            GetProperties task = new GetProperties();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        catch (Exception e)
        {
            Log.e("Exception:",e.toString());
        }
    }

    public MutableLiveData<HashMap<String,String>> getProperties()
    {
        return mProperties;
    }


    private class GetProperties extends AsyncTask<HashMap<String,String>, HashMap<String,String>, HashMap<String,String>> {


        @Override
        protected HashMap<String, String> doInBackground(final HashMap<String,String>... params) {

            HashMap<String, String> propertyList = new HashMap<>();

            try{



                String key= BuildConfig.APIKEY;
                String url="https://api.railwayapi.com/v2/route/train/"+train_no+"/apikey/"+key+"/";

                Log.d("PropertiesRequest1: ",url);

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                String jsonstring=response.body().string();
                JSONObject js0=new JSONObject(jsonstring);
                JSONObject temp = js0.getJSONArray("route").getJSONObject(0).getJSONObject("station");
                first_station= temp.getString("code");



                url="https://api.railwayapi.com/v2/live/train/"+train_no+"/station/"+first_station+"/date/"+date+"/apikey/"+key+"/";



                Log.d("PropertiesRequest2: ",url);

                client = new OkHttpClient();

                request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();

                response = client.newCall(request).execute();
                jsonstring=response.body().string();

                Log.d("PropertiesResponse2",jsonstring);




                if(jsonstring.charAt(0)=='<')
                    throw new Exception("  Server couldn't process request");


                String schedule;
                JSONObject js = new JSONObject(jsonstring);

                if(!js.getString("response_code").equals("200"))
                    throw new JSONException("Could not process query. Either train doesn't run on that day or it has been discontinued.");
                JSONArray days = js.getJSONObject("train").getJSONArray("days");
                int count = 0;
                if (days.length() == 7)
                    for (int i = 0; i < 7; i++) {
                        if (days.getJSONObject(i).getString("runs").equalsIgnoreCase("Y")) {
                            count++;
                        }
                    }
                Log.d("Count: ", Integer.toString(count));
                if (count == 7)
                    schedule = "Daily";
                else if (count < 7 && count >= 1)
                    schedule = "Weekly";
                else
                    schedule = "Train not available";
                propertyList.put("schedule", schedule);
                propertyList.put("number", js.getJSONObject("train").getString("number"));
                propertyList.put("name", js.getJSONObject("train").getString("name"));
                propertyList.put("status", js.getString("position"));
                propertyList.put("status_message","");
                Log.d("Wpr","w");
                return propertyList;
            }

            catch (Exception e){
                Log.e("Exception:",e.toString());
                propertyList.put("schedule", "");
                propertyList.put("number", "Not Available");
                propertyList.put("name", "");
                propertyList.put("status","UNSUCCESSFUL");
                propertyList.put("status_message", "Could not process query. Either train doesn't run on that day or it has been discontinued.");

                return propertyList;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String,String> hm) {
            super.onPostExecute(hm);
            mProperties.setValue(hm);
        }
    }



}
