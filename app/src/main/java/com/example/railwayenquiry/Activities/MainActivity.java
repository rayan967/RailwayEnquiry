package com.example.railwayenquiry.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.railwayenquiry.Fragments.LiveStatusFragment;
import com.example.railwayenquiry.Fragments.LiveStatusFragment2;
import com.example.railwayenquiry.Fragments.MainFragment;
import com.example.railwayenquiry.Fragments.TrainBwStationsFragment;
import com.example.railwayenquiry.Fragments.TrainRouteFragment;
import com.example.railwayenquiry.Fragments.TrainRouteFragment2;
import com.example.railwayenquiry.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, LiveStatusFragment.OnFragmentInteractionListener, LiveStatusFragment2.OnFragmentInteractionListener, TrainRouteFragment.OnFragmentInteractionListener, TrainRouteFragment2.OnFragmentInteractionListener, TrainBwStationsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Fragment mFragment= MainFragment.newInstance(null,null);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.f1content,mFragment,"MainFragment");
        transaction.commit();
        getSupportActionBar();

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }




}
