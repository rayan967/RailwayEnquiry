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

public class RoutePropertiesRepository {

    private MutableLiveData<HashMap<String,String>> mProperties=new MutableLiveData<>();
    String train_no;
    public RoutePropertiesRepository(Application application,String train_no)
    {
        this.train_no=train_no;
        try {
            GetProperties task = new GetProperties();
            task.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
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
                Log.d("RoutePropertiesRequest: ",url);

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                String jsonString=response.body().string();

                Log.d("PropertiesResponse2",jsonString);







                if(jsonString.charAt(0)=='<')
                    throw new Exception("  Server down");

                String schedule;
                String daysofweek="",train_classes="";
                JSONObject js = new JSONObject(jsonString);

                if(!js.getString("response_code").equals("200"))
                    throw new JSONException("Schedule unavailable");
                JSONArray days = js.getJSONObject("train").getJSONArray("days");
                int count = 0;
                if (days.length() == 7)
                    for (int i = 0; i < 7; i++) {
                        if (days.getJSONObject(i).getString("runs").equalsIgnoreCase("Y")) {
                            count++;
                            daysofweek=daysofweek+"Y";
                        }
                        else
                            daysofweek=daysofweek+"N";
                    }
                Log.d("Count: ", Integer.toString(count));
                if (count == 7)
                    schedule = "Daily";
                else if (count < 7 && count >= 1)
                    schedule = "Weekly";
                else
                    schedule = "Train not available";


                JSONArray Classes = js.getJSONObject("train").getJSONArray("classes");
                for (int i = 0; i < 8; i++) {
                        if (Classes.getJSONObject(i).getString("available").equalsIgnoreCase("Y")) {
                            train_classes=train_classes+"Y";
                        }
                        else
                            train_classes=train_classes+"N";
                    }

                propertyList.put("schedule", schedule);
                propertyList.put("number", js.getJSONObject("train").getString("number"));
                propertyList.put("name", js.getJSONObject("train").getString("name"));
                propertyList.put("DaysOfWeek", daysofweek);
                propertyList.put("Classes", train_classes);
                propertyList.put("status","SUCCESSFUL");
                propertyList.put("status_message", "");




                Log.d("Days",daysofweek);
                Log.d("Classes",train_classes);
                return propertyList;
            }catch (Exception e){
                Log.e("Exception:",e.toString());
                propertyList.put("schedule", "");
                propertyList.put("number", "Not Available");
                propertyList.put("name", "");
                propertyList.put("status","UNSUCCESSFUL");
                propertyList.put("status_message", "Schedule unavailable");

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
