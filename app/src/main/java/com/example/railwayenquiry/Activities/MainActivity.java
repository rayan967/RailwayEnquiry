package com.example.railwayenquiry.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.railwayenquiry.Fragments.CPageFragment;
import com.example.railwayenquiry.Fragments.LiveStatusFragment;
import com.example.railwayenquiry.Fragments.LiveStatusFragment2;
import com.example.railwayenquiry.Fragments.MainFragment;
import com.example.railwayenquiry.Fragments.PNRStatusFragment;
import com.example.railwayenquiry.Fragments.PNRStatusFragment2;
import com.example.railwayenquiry.Fragments.RPageFragment;
import com.example.railwayenquiry.Fragments.RescheduleFragment;
import com.example.railwayenquiry.Fragments.StationScehduleFragment;
import com.example.railwayenquiry.Fragments.StationScheduleFragment2;
import com.example.railwayenquiry.Fragments.TrainBwStationsFragment;
import com.example.railwayenquiry.Fragments.TrainRouteFragment;
import com.example.railwayenquiry.Fragments.TrainRouteFragment2;
import com.example.railwayenquiry.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, LiveStatusFragment.OnFragmentInteractionListener, LiveStatusFragment2.OnFragmentInteractionListener,
        TrainRouteFragment.OnFragmentInteractionListener, TrainRouteFragment2.OnFragmentInteractionListener, TrainBwStationsFragment.OnFragmentInteractionListener,
        PNRStatusFragment.OnFragmentInteractionListener, PNRStatusFragment2.OnFragmentInteractionListener, StationScehduleFragment.OnFragmentInteractionListener,
        StationScheduleFragment2.OnFragmentInteractionListener, RescheduleFragment.OnFragmentInteractionListener, RPageFragment.OnFragmentInteractionListener, CPageFragment.OnFragmentInteractionListener {

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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
