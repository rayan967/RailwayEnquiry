package com.example.railwayenquiry.ViewModels;

import android.app.Application;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;
import android.widget.DatePicker;

public class TTViewModel extends AndroidViewModel {
    private TTRepository mRepository;
    private PropertiesRepository mRepository2;
    private LiveData<List<TimeTableItem>> mAllRows;
    public MutableLiveData<HashMap<String, String>> properties= new MutableLiveData<>();
    public MutableLiveData<String> fare;
    String train_no;
    String date;

    public TTViewModel(Application application, String train_no, String date) {
        super(application);
        mAllRows=null;
        mRepository = new TTRepository(application,train_no);
        mAllRows = mRepository.getAllRows();
        mRepository2=new PropertiesRepository(application,train_no,date);
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



    public static String getDateString(int mYear,int mMonth,int mDay)
    {
        String date;
        if((mMonth+1)<10)
            date=(mDay + "-0" + (mMonth+1) + "-" + mYear);
        else
            date=(mDay + "-" + (mMonth+1) + "-" + mYear);
        return  date;
    }
    public static String getTitle(String train_no, String schedule){
        if(!schedule.equals(""))
            return train_no+" - Schedule - "+schedule;
        else
            return train_no+" - Schedule Not Available";
    }
    public static String getTrain_no(String Train)
    {
        String[] words=Train.split("\\s");
        String train_no=words[0];
        return train_no;
    }
}


