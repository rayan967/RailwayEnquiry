package com.example.railwayenquiry.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.PNRItem;

import java.util.List;

public class PNRRepository {
    private PNRDao mPNRDao;
    private LiveData<List<PNRItem>> mAllRows;

    public PNRRepository(Application application, String pnr) {
        PNRRoomDatabase db = PNRRoomDatabase.getDatabase(application,pnr);
        mPNRDao = db.pnrDao();
        mAllRows = mPNRDao.getAllRows();
    }
    public LiveData<List<PNRItem>> getAllRows() {
        return mAllRows;
    }

    public void insert (PNRItem pnr) {
        new insertAsyncTask(mPNRDao).execute(pnr);
    }

    private static class insertAsyncTask extends AsyncTask<PNRItem, Void, Void> {

        private PNRDao mAsyncTaskDao;

        insertAsyncTask(PNRDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PNRItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
