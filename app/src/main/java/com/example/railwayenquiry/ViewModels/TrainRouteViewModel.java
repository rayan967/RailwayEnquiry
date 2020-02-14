package com.example.railwayenquiry.ViewModels;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.railwayenquiry.Fragments.TrainRouteFragment2;
import com.example.railwayenquiry.Repositories.RoutePropertiesRepository;
import com.example.railwayenquiry.Repositories.TTRepository;
import com.example.railwayenquiry.Adapters.TimeTableItem;
import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class TrainRouteViewModel extends AndroidViewModel {
    private TTRepository mRepository;
    private RoutePropertiesRepository mRepository2;
    private LiveData<List<TimeTableItem>> mAllRows;
    public MutableLiveData<HashMap<String, String>> properties= new MutableLiveData<>();
    public MutableLiveData<String> fare;
    private TTRepository fareRepository;


    public TrainRouteViewModel(Application application, String train) {
        super(application);
        mRepository = new TTRepository(application,train);
        mAllRows = mRepository.getAllRows();
        mRepository2=new RoutePropertiesRepository(application,train);
        properties=mRepository2.getProperties();

    }

    public TrainRouteViewModel(Application application) {
        super(application);

    }

    public MutableLiveData<String> getFare( String train_no, String start_station, String destination_station, String age, String Class, String date) {
        fareRepository=new TTRepository( train_no, start_station,  destination_station,  age,  Class,  date);
        fare=fareRepository.getFare();
        return fare;
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

    public static String[] getStation_List(List<TimeTableItem> mTTList)
    {
        String[] station_list=new String[mTTList.size()];
        for(int i=0;i<mTTList.size();i++)
        {
            station_list[i]=mTTList.get(i).getStation_name();
        }
        return station_list;
    }

    public static String[] getStation_CodeList(List<TimeTableItem> mTTList)
    {
        String[] station_list=new String[mTTList.size()];
        for(int i=0;i<mTTList.size();i++)
        {
            station_list[i]=mTTList.get(i).getStation_code();
        }
        return station_list;
    }

    public static List<String> getClasses(String classes)
    {
        String[] classes_list={"FC","2A","2S","SL","1A","3A","CC","3E"};
        List<String> Classes=new ArrayList<>();
        for(int i=0;i<classes.length();i++){
            if(classes.charAt(i)=='Y'){
                Classes.add(classes_list[i]);
            }
        }
        return Classes;
    }

    public static List<Integer> getWeekDays(String daysofweek)
    {
        int[] weekday_list={Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY,Calendar.SUNDAY};
        List<Integer> weekdays=new ArrayList<Integer>();
        for(int i=0;i<daysofweek.length();i++){
            if(daysofweek.charAt(i)=='Y'){
                weekdays.add(weekday_list[i]);
            }
        }
        return weekdays;
    }

    public static String getToday()
    {
        final Calendar c = Calendar.getInstance();
        String date;
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        if((mMonth+1)<10)
            date=(mDay + "-0" + (mMonth+1) + "-" + mYear);
        else
            date=(mDay + "-" + (mMonth+1) + "-" + mYear);
        return date;
    }

    public static DatePickerDialog modifyDatePicker(DatePickerDialog dpd, Calendar now, List<Integer> weekdays)
    {
        Calendar last_date=Calendar.getInstance();
        last_date.add(Calendar.MONTH,4);
        dpd.setMinDate(now);
        dpd.setMaxDate(last_date);
        Calendar[] last =  new Calendar[1];
        last[0] = last_date;
        dpd.setDisabledDays(last);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);


        for (Calendar loopdate = now; loopdate.before(last_date); loopdate.add(Calendar.DATE, 1)) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            if (!weekdays.contains(dayOfWeek)) {
                Calendar[] disabledDays =  new Calendar[1];
                disabledDays[0] = loopdate;
                dpd.setDisabledDays(disabledDays);
            }
        }
        return dpd;
    }

    public static boolean NegativeCondition(List<String> stations, AutoCompleteTextView textView1, AutoCompleteTextView textView2, AutoCompleteTextView textView3, TextInputEditText age, List<String> Classes) {
        boolean bool = stations.indexOf(textView1.getText().toString()) > stations.indexOf(textView2.getText().toString()) || textView1.getText().toString().equals("")
                || textView2.getText().toString().equals("") || Integer.parseInt(age.getText().toString()) > 120 || age.getText().toString().equals("") || textView3.getText().toString().equals("");
        return bool;
    }

    public static String getTitle(String train_no, String schedule, String status, String statusmessage)
    {
        String title;
        if(!status.equals("SUCCESSFUL"))
        {
            title=train_no+" - "+statusmessage;
        }

        else
        {
            title=train_no+" - Schedule - "+schedule;

        }
        return title;
    }

}
