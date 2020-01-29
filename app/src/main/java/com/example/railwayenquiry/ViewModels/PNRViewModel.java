package com.example.railwayenquiry.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.railwayenquiry.Adapters.PNRItem;
import com.example.railwayenquiry.Repositories.DetailsRepository;
import com.example.railwayenquiry.Repositories.PNRRepository;

import java.util.HashMap;
import java.util.List;

public class PNRViewModel extends AndroidViewModel {

    private PNRRepository mRepository;
    private DetailsRepository mRepository2;
    private LiveData<List<PNRItem>> mAllRows;
    public MutableLiveData<HashMap<String, String>> properties= new MutableLiveData<>();

    public PNRViewModel(Application application) {
        super(application);
        mRepository = new PNRRepository(application);
        mRepository2=new DetailsRepository(application);
        mAllRows = mRepository.getAllRows();
        properties=mRepository2.getDetails();

    }

    public LiveData<List<PNRItem>> getAllRows() {
        return mAllRows;
    }

    public void insert(PNRItem pnr) {
        mRepository.insert(pnr);
    }

    public MutableLiveData<HashMap<String, String>> getProperties() {

        return properties;
    }
}
