package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.railwayenquiry.Adapters.TimeTableItem;

import org.json.JSONObject;

import java.util.List;

public class TTRepository {
    private TTDao mTTDao;
    private LiveData<List<TimeTableItem>> mAllRows;
    public MutableLiveData<String> Fare;

    public TTRepository(Application application) {
        TTRoomDatabase db = TTRoomDatabase.getDatabase(application);
        mTTDao = db.ttDao();
        mAllRows = mTTDao.getAllRows();
    }
    public TTRepository(){
        Fare=new MutableLiveData<>();
    }
    public LiveData<List<TimeTableItem>> getAllRows() {
        return mAllRows;
    }

    public void insert (TimeTableItem tt) {
        new insertAsyncTask(mTTDao).execute(tt);
    }

    public MutableLiveData<String> getFare(){
        try {
            insertFareAsyncTask task = new insertFareAsyncTask();
            task.execute();
        }
        catch (Exception e)
        {
            Log.e("Exception:",e.toString());
        }
    return Fare;
    }

    private static class insertAsyncTask extends AsyncTask<TimeTableItem, Void, Void> {

        private TTDao mAsyncTaskDao;

        insertAsyncTask(TTDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeTableItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class insertFareAsyncTask extends AsyncTask<String, String, String> {

        public String fare;
        private TTDao mAsyncTaskDao;
        public String jsonString="{\"train\": {\"days\": [{\"runs\": \"Y\", \"code\": \"MON\"}, {\"runs\": \"Y\", \"code\": \"TUE\"}, {\"runs\": \"Y\", \"code\": \"WED\"}, {\"runs\": \"Y\", \"code\": \"THU\"}, {\"runs\": \"Y\", \"code\": \"FRI\"}, {\"runs\": \"Y\", \"code\": \"SAT\"}, {\"runs\": \"Y\", \"code\": \"SUN\"}], \"classes\": [{\"available\": \"N\", \"name\": \"FIRST AC\", \"code\": \"1A\"}, {\"available\": \"N\", \"name\": \"AC CHAIR CAR\", \"code\": \"CC\"}, {\"available\": \"Y\", \"name\": \"THIRD AC\", \"code\": \"3A\"}, {\"available\": \"N\", \"name\": \"3rd AC ECONOMY\", \"code\": \"3E\"}, {\"available\": \"N\", \"name\": \"FIRST CLASS\", \"code\": \"FC\"}, {\"available\": \"Y\", \"name\": \"SECOND AC\", \"code\": \"2A\"}, {\"available\": \"Y\", \"name\": \"SLEEPER CLASS\", \"code\": \"SL\"}, {\"available\": \"N\", \"name\": \"SECOND SEATING\", \"code\": \"2S\"}], \"name\": \"MUMBAI CSMT-HYB EXP\", \"number\": \"17031\"}, \"response_code\": 200, \"quota\": {\"name\": \"GENERAL QUOTA\", \"code\": \"GN\"}, \"to_station\": {\"name\": \"HYDERABAD DECAN\", \"lat\": 17.3924556, \"code\": \"HYB\", \"lng\": 78.4675753}, \"debit\": 1, \"journey_class\": {\"name\": \"SLEEPER CLASS\", \"code\": \"SL\"}, \"from_station\": {\"name\": \"C SHIVAJI MAHARAJ T\", \"lat\": 18.9398446, \"code\": \"CSMT\", \"lng\": 72.8354475}, \"fare\": 395.0}";

        @Override
        protected String doInBackground(final String... params) {
            try {
                JSONObject js = new JSONObject(jsonString);
                String fare=js.getString("fare");
                return fare;
            }
            catch (Exception e){ e.printStackTrace(); return null;}
        }
        @Override
        protected void onPostExecute(String fare)
        {
            super.onPostExecute(fare);
            Fare.setValue(fare);

        }
    }

}
