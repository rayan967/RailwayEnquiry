package com.example.railwayenquiry.Repositories;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.RescheduleItem;

import java.util.List;

public class RescheduleRepository {

    private RescheduleDao mTrainDao;
    private LiveData<List<RescheduleItem>> mAllRows;

    public RescheduleRepository (Application application) {
        RescheduleRoomDatabase db = RescheduleRoomDatabase.getDatabase(application);
        mTrainDao = db.trainDao();
        mAllRows = mTrainDao.getAllRows();
        Log.d("RescheduleRepository:", "Reached");
    }
    public LiveData<List<RescheduleItem>> getAllRows() {
        return mAllRows;
    }

    public void insert (RescheduleItem train) {
        new insertAsyncTask(mTrainDao).execute(train);
    }

    public void delete(){
        mTrainDao.deleteAll();
    }

    private static class insertAsyncTask extends AsyncTask<RescheduleItem, Void, Void> {

        private RescheduleDao mAsyncTaskDao;

        insertAsyncTask(RescheduleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RescheduleItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

