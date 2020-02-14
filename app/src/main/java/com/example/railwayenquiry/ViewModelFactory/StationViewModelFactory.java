package com.example.railwayenquiry.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.railwayenquiry.ViewModels.TrainViewModel;
import com.example.railwayenquiry.ViewModels.TTViewModel;

public class StationViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mExtra;

    public StationViewModelFactory(Application application, String extra) {
        mApplication = application;
        mExtra = extra;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrainViewModel(mApplication, mExtra);
    }
}
