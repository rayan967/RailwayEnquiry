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

import com.example.railwayenquiry.Repositories.PropertiesRepository;
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
    private PropertiesRepository mRepository2;
    private LiveData<List<TimeTableItem>> mAllRows;
    public MutableLiveData<HashMap<String, String>> properties= new MutableLiveData<>();

    public TTViewModel(Application application) {
        super(application);
        mRepository = new TTRepository(application);
        mRepository2=new PropertiesRepository(application);
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
}


