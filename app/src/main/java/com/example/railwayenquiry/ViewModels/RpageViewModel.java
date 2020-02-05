package com.example.railwayenquiry.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.RescheduleItem;
import com.example.railwayenquiry.Repositories.RescheduleRepository;
import com.example.railwayenquiry.Repositories.TrainRepository;

import java.util.List;

public class RpageViewModel extends AndroidViewModel {
    private RescheduleRepository mRepository;
    private LiveData<List<RescheduleItem>> mAllRows;


    public RpageViewModel(Application application) {
        super(application);
        Log.d("RescheduleViewModel:", "Reached");
        mRepository = new RescheduleRepository(application);
        mAllRows = mRepository.getAllRows();

    }

    public LiveData<List<RescheduleItem>> getAllRows() {
        return mAllRows;
    }

    public void delete(){
        mRepository.delete();
    }

    public void insert(RescheduleItem train) {
        mRepository.insert(train);
    }

}
