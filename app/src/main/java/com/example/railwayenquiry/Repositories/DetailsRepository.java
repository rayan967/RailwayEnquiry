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

public class DetailsRepository {

    private MutableLiveData<HashMap<String,String>> mDetails=new MutableLiveData<>();
    String pnr;
    public static String jsonstring="{ \"pnr\": \"1234567890\", \"journeyDetails\": { \"trainNumber\": \"22629\", \"trainName\": \"DADAR TEN EXP\", \"boardingDate\": \"2-5-2014\", \"from\": \"DR\", \"to\": \"KKW\", \"reservedUpto\": \"KKW\", \"boardingPoint\": \"DR\", \"class\": \"SL\" }, \"lastUpdated\": \"9-3-2014 15:34\", \"bookingStatus\": [ { \"passengerNo\": \"1\", \"bookingStatus\": \"W/L 171,PQWL\", \"currentStatus\": \"W/L 137\" }, { \"passengerNo\": \"2\", \"bookingStatus\": \"W/L 172,PQWL\", \"currentStatus\": \"W/L 138\" }, { \"passengerNo\": \"3\", \"bookingStatus\": \"W/L 173,PQWL\", \"currentStatus\": \"W/L 139\" }, { \"passengerNo\": \"4\", \"bookingStatus\": \"W/L 174,PQWL\", \"currentStatus\": \"W/L 140\" }, { \"passengerNo\": \"5\", \"bookingStatus\": \"W/L 175,PQWL\", \"currentStatus\": \"W/L 141\" }, { \"passengerNo\": \"6\", \"bookingStatus\": \"W/L 176,PQWL\", \"currentStatus\": \"W/L 142\" } ], \"chartingStatus\": \"CHART NOT PREPARED\" }";
    public DetailsRepository(Application application, String pnr)
    {
        this.pnr=pnr;
        try {
            GetDetails task = new GetDetails();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        catch (Exception e)
        {
            Log.e("Exception:",e.toString());
        }
    }

    public MutableLiveData<HashMap<String,String>> getDetails()
    {
        return mDetails;
    }


    private class GetDetails extends AsyncTask<HashMap<String,String>, HashMap<String,String>, HashMap<String,String>> {


        @Override
        protected HashMap<String, String> doInBackground(final HashMap<String,String>... params) {

            HashMap<String, String> detailList = new HashMap<>();

            try{


                if(!pnr.equals("1234567890")) { //To Demonstrate UI in case 3rd party API is down
                    String key = BuildConfig.PNRAPIKEY;
                    String url = "https://indianrailways.p.rapidapi.com/index.php?pnr=" + pnr;
                    OkHttpClient client = new OkHttpClient();

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

                    Log.d("PNRResponse: ", jsonstring);
                }


                JSONObject js = new JSONObject(jsonstring);
                detailList.put("train_name", js.getJSONObject("journeyDetails").getString("trainName"));
                detailList.put("boarding_date", js.getJSONObject("journeyDetails").getString("boardingDate"));
                detailList.put("class", js.getJSONObject("journeyDetails").getString("class"));
                detailList.put("last_updated", js.getString("lastUpdated"));
                detailList.put("charting_status", js.getString("chartingStatus"));
                detailList.put("status","SUCCESSFUL");
                detailList.put("status_message", "200");
                Log.d("Wpr","w");
                return detailList;
            }catch (Exception e){
                Log.e("Exception:",e.toString());
                detailList.put("train_name", "PNR Does Not Exist");
                detailList.put("boarding_date","Not Available");
                detailList.put("class", "Not Available");
                detailList.put("last_updated", "Not Available");
                detailList.put("charting_status", "Not Available");
                detailList.put("status","UNSUCCESSFUL");
                detailList.put("status_message", "PNR Does Not Exist");


                return detailList;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String,String> hm) {
            super.onPostExecute(hm);
            mDetails.setValue(hm);
        }
    }



}
