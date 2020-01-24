package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.TimeTableItem;

import java.util.List;

public class TTRepository {
    private TTDao mTTDao;
    private LiveData<List<TimeTableItem>> mAllRows;

    public TTRepository(Application application) {
        TTRoomDatabase db = TTRoomDatabase.getDatabase(application);
        mTTDao = db.ttDao();
        mAllRows = mTTDao.getAllRows();
    }
    public LiveData<List<TimeTableItem>> getAllRows() {
        return mAllRows;
    }

    public void insert (TimeTableItem tt) {
        new insertAsyncTask(mTTDao).execute(tt);
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

}
