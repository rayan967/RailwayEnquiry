package com.example.railwayenquiry.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.TrainItem;
import com.example.railwayenquiry.Repositories.TrainRepository;

import java.util.List;

public class TrainViewModel extends AndroidViewModel {

    private TrainRepository mRepository;
    private LiveData<List<TrainItem>> mAllRows;


    public TrainViewModel(Application application, String station) {
        super(application);
        mRepository = new TrainRepository(application,station);
        mAllRows = mRepository.getAllRows();

    }

    public LiveData<List<TrainItem>> getAllRows() {
        return mAllRows;
    }

    public void insert(TrainItem train) {
        mRepository.insert(train);
    }

}
