package com.example.railwayenquiry.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.railwayenquiry.ViewModels.TTViewModel;

public class TTViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mExtra;
    private String date;

    public TTViewModelFactory(Application application, String extra, String date) {
        mApplication = application;
        mExtra = extra;
        this.date=date;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TTViewModel(mApplication, mExtra, date);
    }
}
