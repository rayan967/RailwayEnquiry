package com.example.railwayenquiry.ViewModels;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.railwayenquiry.Adapters.CancelItem;
import com.example.railwayenquiry.Repositories.CancelRepository;
import com.example.railwayenquiry.Repositories.TrainRepository;
import java.util.List;

public class CpageViewModel extends AndroidViewModel {
    private CancelRepository mRepository;
    private LiveData<List<CancelItem>> mAllRows;


    public CpageViewModel(Application application) {
        super(application);
        mRepository = new CancelRepository(application);
        mAllRows = mRepository.getAllRows();

    }

    public LiveData<List<CancelItem>> getAllRows() {
        return mAllRows;
    }

    public void insert(CancelItem train) {
        mRepository.insert(train);
    }

}
