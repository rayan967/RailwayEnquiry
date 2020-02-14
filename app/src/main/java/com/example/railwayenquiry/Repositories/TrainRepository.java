package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.TrainItem;

import java.util.List;

public class TrainRepository {

    private TrainDao mTrainDao;
    private LiveData<List<TrainItem>> mAllRows;

    public TrainRepository(Application application,String station) {
        TrainRoomDatabase db = TrainRoomDatabase.getDatabase(application,station);
        mTrainDao = db.trainDao();
        mAllRows = mTrainDao.getAllRows();
    }
    public LiveData<List<TrainItem>> getAllRows() {
        return mAllRows;
    }

    public void insert (TrainItem train) {
        new insertAsyncTask(mTrainDao).execute(train);
    }

    private static class insertAsyncTask extends AsyncTask<TrainItem, Void, Void> {

        private TrainDao mAsyncTaskDao;

        insertAsyncTask(TrainDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TrainItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
