package com.example.railwayenquiry.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.railwayenquiry.ViewModels.PNRViewModel;
import com.example.railwayenquiry.ViewModels.TTViewModel;

public class PNRViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mExtra;

    public PNRViewModelFactory(Application application, String extra) {
        mApplication = application;
        mExtra = extra;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PNRViewModel(mApplication, mExtra);
    }
}
