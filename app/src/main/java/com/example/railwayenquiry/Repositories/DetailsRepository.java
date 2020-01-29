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

public class DetailsRepository {

    private MutableLiveData<HashMap<String,String>> mDetails=new MutableLiveData<>();
    public static String jsonstring="{ \"pnr\": \"1234567890\", \"journeyDetails\": { \"trainNumber\": \"22629\", \"trainName\": \"DADAR TEN EXP\", \"boardingDate\": \"2-5-2014\", \"from\": \"DR\", \"to\": \"KKW\", \"reservedUpto\": \"KKW\", \"boardingPoint\": \"DR\", \"class\": \"SL\" }, \"lastUpdated\": \"9-3-2014 15:34\", \"bookingStatus\": [ { \"passengerNo\": \"1\", \"bookingStatus\": \"W/L 171,PQWL\", \"currentStatus\": \"W/L 137\" }, { \"passengerNo\": \"2\", \"bookingStatus\": \"W/L 172,PQWL\", \"currentStatus\": \"W/L 138\" }, { \"passengerNo\": \"3\", \"bookingStatus\": \"W/L 173,PQWL\", \"currentStatus\": \"W/L 139\" }, { \"passengerNo\": \"4\", \"bookingStatus\": \"W/L 174,PQWL\", \"currentStatus\": \"W/L 140\" }, { \"passengerNo\": \"5\", \"bookingStatus\": \"W/L 175,PQWL\", \"currentStatus\": \"W/L 141\" }, { \"passengerNo\": \"6\", \"bookingStatus\": \"W/L 176,PQWL\", \"currentStatus\": \"W/L 142\" } ], \"chartingStatus\": \"CHART NOT PREPARED\" }";
    public DetailsRepository(Application application)
    {
        try {
            GetDetails task = new GetDetails();
            task.execute();
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

            try{
                MutableLiveData<HashMap<String,String>> Details=new MutableLiveData<>();
                HashMap<String, String> detailList = new HashMap<>();
                JSONObject js = new JSONObject(jsonstring);
                detailList.put("train_name", js.getJSONObject("journeyDetails").getString("trainName"));
                detailList.put("boarding_date", js.getJSONObject("journeyDetails").getString("boardingDate"));
                detailList.put("class", js.getJSONObject("journeyDetails").getString("class"));
                detailList.put("last_updated", js.getString("lastUpdated"));
                detailList.put("charting_status", js.getString("chartingStatus"));
                Log.d("Wpr","w");
                return detailList;
            }catch (JSONException e){
                Log.e("Exception:",e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String,String> hm) {
            super.onPostExecute(hm);
            mDetails.setValue(hm);
        }
    }



}
