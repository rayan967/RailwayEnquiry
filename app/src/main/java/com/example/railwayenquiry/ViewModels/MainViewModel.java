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

public class MainViewModel extends AndroidViewModel {
    String train_no;
    String date;

    public MainViewModel(Application application) {
        super(application);
    }

    public static boolean isUnsuccessful(String status, String status_message)
    {
        if(status.equals("UNSUCCESSFUL"))
            return true;
        else
            return false;
    }

    public static String getStation_code(String station)
    {

        String[] words=station.split("\\s");

        String station_code=words[words.length-1];
        return station_code;
    }

}


