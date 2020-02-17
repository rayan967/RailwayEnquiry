package com.example.railwayenquiry.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModelFactory.TTViewModelFactory;
import com.example.railwayenquiry.ViewModels.MainViewModel;
import com.example.railwayenquiry.ViewModels.TTViewModel;
import com.example.railwayenquiry.Adapters.TimeTableAdapter;
import com.example.railwayenquiry.Adapters.TimeTableItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;


public class LiveStatusFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String Train;
    private String Date;
    private List<TimeTableItem> TTList=new ArrayList<>();;
    TimeTableAdapter mAdapter;
    private TTViewModel mTTViewModel;

    private OnFragmentInteractionListener mListener;

    public LiveStatusFragment2() {}



    public static LiveStatusFragment2 newInstance(String param1, String param2) {
        LiveStatusFragment2 fragment = new LiveStatusFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Train = getArguments().getString(ARG_PARAM1);
            Date = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_status2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        final TextView Status = (TextView) getView().findViewById(R.id.textView9);
        final TextView Title= getView().findViewById(R.id.textView11);


        mAdapter = new TimeTableAdapter(TTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        LottieAnimationView animationView=view.findViewById(R.id.animation_view);
        animationView.playAnimation();
        animationView.enableMergePathsForKitKatAndAbove( true );




        String status="<b>Status:</b> Please wait while the schedule is being retrieved...";
        Status.setText(Html.fromHtml(status));



        String[] words=Train.split("\\s");

        final String train_no=TTViewModel.getTrain_no(Train);
        Log.d("Split: ",train_no);
        Log.d("Date: ",Date);
        String title=train_no+" - Schedule";
        Title.setText(title);

        TTViewModelFactory factory = new TTViewModelFactory(this.getActivity().getApplication(), train_no,Date);

        mTTViewModel = new ViewModelProvider(this,factory).get(TTViewModel.class);

        int flag=0;
        mTTViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<TimeTableItem>>() {
            @Override
            public void onChanged(@Nullable final List<TimeTableItem> words) {

                if(words.size()>0) {
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.GONE);
                }
                mAdapter.setRows(words,true);

            }
        });

        mTTViewModel.getProperties().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable final HashMap<String, String> stringHashMap) {

                String schedule=stringHashMap.get("schedule");
                String title= TTViewModel.getTitle(train_no,schedule);
                Title.setText(title);
                String status=stringHashMap.get("status");

                String statusmessage=stringHashMap.get("status_message");
                if(MainViewModel.isUnsuccessful(status,statusmessage)){

                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Live Status Enquiry")
                            .setMessage(statusmessage)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().onBackPressed();
                                }
                            })
                            .show();

                }
                statusmessage="<b>Status:</b> "+statusmessage;
                Status.setText(Html.fromHtml(statusmessage));
                Log.d("Values",stringHashMap.get("name"));
            }
        });


    }
    

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
