package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.CancelItem;


import java.util.List;

public class CancelRepository {

    private CancelDao mTrainDao;
    private LiveData<List<CancelItem>> mAllRows;

    public CancelRepository (Application application) {
        CancelRoomDatabase db = CancelRoomDatabase.getDatabase(application);
        mTrainDao = db.trainDao();
        mAllRows = mTrainDao.getAllRows();
        Log.d("CancelRepository:", "Reached");
    }
    public LiveData<List<CancelItem>> getAllRows() {
        return mAllRows;
    }

    public void insert (CancelItem train) {
        new insertAsyncTask(mTrainDao).execute(train);
    }

    public void delete(){mTrainDao.deleteAll();}

    private static class insertAsyncTask extends AsyncTask<CancelItem, Void, Void> {

        private CancelDao mAsyncTaskDao;

        insertAsyncTask(CancelDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CancelItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

