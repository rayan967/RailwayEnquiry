package com.example.railwayenquiry.Fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.railwayenquiry.Adapters.PNRAdapter;
import com.example.railwayenquiry.Adapters.PNRItem;
import com.example.railwayenquiry.Adapters.TimeTableItem;
import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModelFactory.PNRViewModelFactory;
import com.example.railwayenquiry.ViewModels.MainViewModel;
import com.example.railwayenquiry.ViewModels.PNRViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PNRStatusFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String pnr;
    private String mParam2;

    private List<PNRItem> PNRList=new ArrayList<>();
    PNRAdapter mAdapter;
    private PNRViewModel mPNRViewModel;


    private OnFragmentInteractionListener mListener;

    public PNRStatusFragment2() {
    }


    public static PNRStatusFragment2 newInstance(String param1, String param2) {
        PNRStatusFragment2 fragment = new PNRStatusFragment2();
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
            pnr = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pnrstatus2, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        final TextView Train_name = view.findViewById(R.id.trainname);
        final TextView date= view.findViewById(R.id.date);
        final TextView Class= view.findViewById(R.id.Class);
        final TextView last_updated= view.findViewById(R.id.lastupdated);
        final TextView charting= view.findViewById(R.id.chartingstatus);

        mAdapter = new PNRAdapter(PNRList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        PNRViewModelFactory factory=new PNRViewModelFactory(this.getActivity().getApplication(),pnr);
        mPNRViewModel = new ViewModelProvider(this,factory).get(PNRViewModel.class);

        LottieAnimationView animationView=view.findViewById(R.id.animation_view);
        animationView.playAnimation();
        animationView.enableMergePathsForKitKatAndAbove(true);



        mPNRViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<PNRItem>>() {
            @Override
            public void onChanged(@Nullable final List<PNRItem> words) {

                if(words.size()>0) {
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.GONE);
                }
                mAdapter.setRows(words,true);
            }
        });


        mPNRViewModel.getProperties().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable final HashMap<String, String> stringHashMap) {

                String status=stringHashMap.get("status");

                String statusmessage=stringHashMap.get("status_message");
                if(MainViewModel.isUnsuccessful(status,statusmessage)){

                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("PNR Enquiry")
                            .setMessage(statusmessage)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().onBackPressed();
                                }
                            })
                            .show();

                }
                Train_name.setText(Html.fromHtml("<b>"+stringHashMap.get("train_name")+"</b>"));
                date.setText("Boarding: "+stringHashMap.get("boarding_date"));
                Class.setText(stringHashMap.get("class")+" Class");
                last_updated.setText("Last Updated: "+stringHashMap.get("last_updated"));
                charting.setText("Charting Status: "+stringHashMap.get("charting_status"));

            }
        });

    }


    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
