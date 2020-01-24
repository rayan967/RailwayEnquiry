package com.example.railwayenquiry.ViewModels;

import android.app.Application;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.railwayenquiry.Repositories.TTRepository;
import com.example.railwayenquiry.Adapters.TimeTableItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;

public class TTViewModel extends AndroidViewModel {
    private TTRepository mRepository;
    private String jsonString = "{\"train\": {\"classes\": [{\"available\": \"N\", \"name\": \"FIRST CLASS\", \"code\": \"FC\"}, {\"available\": \"Y\", \"name\": \"SECOND AC\", \"code\": \"2A\"}, {\"available\": \"N\", \"name\": \"SECOND SEATING\", \"code\": \"2S\"}, {\"available\": \"Y\", \"name\": \"SLEEPER CLASS\", \"code\": \"SL\"}, {\"available\": \"N\", \"name\": \"FIRST AC\", \"code\": \"1A\"}, {\"available\": \"Y\", \"name\": \"THIRD AC\", \"code\": \"3A\"}, {\"available\": \"N\", \"name\": \"AC CHAIR CAR\", \"code\": \"CC\"}, {\"available\": \"N\", \"name\": \"3rd AC ECONOMY\", \"code\": \"3E\"}], \"number\": \"17031\", \"name\": \"MUMBAI CSMT-HYB EXP\", \"days\": [{\"runs\": \"Y\", \"code\": \"MON\"}, {\"runs\": \"Y\", \"code\": \"TUE\"}, {\"runs\": \"Y\", \"code\": \"WED\"}, {\"runs\": \"Y\", \"code\": \"THU\"}, {\"runs\": \"Y\", \"code\": \"FRI\"}, {\"runs\": \"Y\", \"code\": \"SAT\"}, {\"runs\": \"Y\", \"code\": \"SUN\"}]}, \"status\": {\"scharr_date\": \"Source\", \"actarr\": \"Source\", \"scharr\": \"Source\", \"latemin\": null, \"has_departed\": true, \"has_arrived\": false, \"actarr_date\": \"Source\", \"schdep\": \"12:45\", \"actdep\": \"12:46\"}, \"response_code\": 200, \"position\": \"Departed from Bale at 22:18 22-Jan. 451 Kms. ahead of C Shivaji Maharaj T.\", \"start_date\": \"22-01-2020\", \"station\": {\"lng\": 72.8354475, \"name\": \"C SHIVAJI MAHARAJ T\", \"code\": \"CSMT\", \"lat\": 18.9398446}, \"debit\": 2}";
    private LiveData<List<TimeTableItem>> mAllRows;
    private MutableLiveData<HashMap<String, String>> properties= new MutableLiveData<>();

    public TTViewModel(Application application) {
        super(application);
        mRepository = new TTRepository(application);
        mAllRows = mRepository.getAllRows();
    }

    public LiveData<List<TimeTableItem>> getAllRows() {
        return mAllRows;
    }

    public void insert(TimeTableItem tt) {
        mRepository.insert(tt);
    }

    public MutableLiveData<HashMap<String, String>> getProperties(){
        if (properties != null) {
            Handler mHandler = new Handler(Looper.getMainLooper(),new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {

                    try{
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
                    properties.setValue(propertyList);
                    Log.d("Wpr","w");
                    }catch (JSONException e){
                        Log.e("Exception:",e.toString());
                    }
                    return false;
                }

            });
        }
        return properties;

    }
}
