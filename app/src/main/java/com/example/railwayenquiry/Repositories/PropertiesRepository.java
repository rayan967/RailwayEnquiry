package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PropertiesRepository {

    private MutableLiveData<HashMap<String,String>> mProperties=new MutableLiveData<>();
    public static String jsonString = "{\"train\": {\"classes\": [{\"available\": \"N\", \"name\": \"FIRST CLASS\", \"code\": \"FC\"}, {\"available\": \"Y\", \"name\": \"SECOND AC\", \"code\": \"2A\"}, {\"available\": \"N\", \"name\": \"SECOND SEATING\", \"code\": \"2S\"}, {\"available\": \"Y\", \"name\": \"SLEEPER CLASS\", \"code\": \"SL\"}, {\"available\": \"N\", \"name\": \"FIRST AC\", \"code\": \"1A\"}, {\"available\": \"Y\", \"name\": \"THIRD AC\", \"code\": \"3A\"}, {\"available\": \"N\", \"name\": \"AC CHAIR CAR\", \"code\": \"CC\"}, {\"available\": \"N\", \"name\": \"3rd AC ECONOMY\", \"code\": \"3E\"}], \"number\": \"17031\", \"name\": \"MUMBAI CSMT-HYB EXP\", \"days\": [{\"runs\": \"Y\", \"code\": \"MON\"}, {\"runs\": \"Y\", \"code\": \"TUE\"}, {\"runs\": \"Y\", \"code\": \"WED\"}, {\"runs\": \"Y\", \"code\": \"THU\"}, {\"runs\": \"Y\", \"code\": \"FRI\"}, {\"runs\": \"Y\", \"code\": \"SAT\"}, {\"runs\": \"Y\", \"code\": \"SUN\"}]}, \"status\": {\"scharr_date\": \"Source\", \"actarr\": \"Source\", \"scharr\": \"Source\", \"latemin\": null, \"has_departed\": true, \"has_arrived\": false, \"actarr_date\": \"Source\", \"schdep\": \"12:45\", \"actdep\": \"12:46\"}, \"response_code\": 200, \"position\": \"Departed from Bale at 22:18 22-Jan. 451 Kms. ahead of C Shivaji Maharaj T.\", \"start_date\": \"22-01-2020\", \"station\": {\"lng\": 72.8354475, \"name\": \"C SHIVAJI MAHARAJ T\", \"code\": \"CSMT\", \"lat\": 18.9398446}, \"debit\": 2}";
    public PropertiesRepository(Application application)
    {
        try {
            GetProperties task = new GetProperties();
            task.execute();
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

            try{
                MutableLiveData<HashMap<String,String>> properties=new MutableLiveData<>();
                HashMap<String, String> propertyList = new HashMap<>();
                String schedule;
                JSONObject js = new JSONObject(jsonString);
                JSONArray days = js.getJSONObject("train").getJSONArray("days"); //To check running schedule of train
                int count = 0;
                if (days.length() == 7)
                    for (int i = 0; i < 7; i++) {
                        if (days.getJSONObject(i).getString("runs").equalsIgnoreCase("Y"))
                            count++;
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
                Log.d("Wpr","w");
                return propertyList;
            }catch (JSONException e){
                Log.e("Exception:",e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String,String> hm) {
            super.onPostExecute(hm);
            mProperties.setValue(hm);
        }
    }



}
