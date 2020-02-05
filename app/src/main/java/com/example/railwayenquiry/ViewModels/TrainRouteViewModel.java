package com.example.railwayenquiry.ViewModels;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.railwayenquiry.Repositories.RoutePropertiesRepository;
import com.example.railwayenquiry.Repositories.TTRepository;
import com.example.railwayenquiry.Adapters.TimeTableItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;

public class TrainRouteViewModel extends AndroidViewModel {
    private TTRepository mRepository;
    private RoutePropertiesRepository mRepository2;
    private LiveData<List<TimeTableItem>> mAllRows;
    public MutableLiveData<HashMap<String, String>> properties= new MutableLiveData<>();
    public MutableLiveData<String> fare;

    public TrainRouteViewModel(Application application) {
        super(application);
        mRepository = new TTRepository(application);
        mRepository2=new RoutePropertiesRepository(application);
        mAllRows = mRepository.getAllRows();
        properties=mRepository2.getProperties();

    }

    public LiveData<List<TimeTableItem>> getAllRows() {
        return mAllRows;
    }

    public void insert(TimeTableItem tt) {
        mRepository.insert(tt);
    }

    public MutableLiveData<HashMap<String, String>> getProperties() {

        return properties;
    }

    public MutableLiveData<String> getFare() {
        TTRepository mTTRepository=new TTRepository();
        fare=mTTRepository.getFare();
        return fare;
    }
}
