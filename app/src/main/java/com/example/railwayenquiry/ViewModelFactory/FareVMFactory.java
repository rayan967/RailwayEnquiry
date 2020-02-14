package com.example.railwayenquiry.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.railwayenquiry.ViewModels.TTViewModel;
import com.example.railwayenquiry.ViewModels.TrainRouteViewModel;

public class FareVMFactory implements ViewModelProvider.Factory {

    private Application mApplication;


    public FareVMFactory(Application application) {

        mApplication = application;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrainRouteViewModel(mApplication);
    }
}
