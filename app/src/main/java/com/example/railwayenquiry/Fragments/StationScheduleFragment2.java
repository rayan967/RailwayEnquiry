package com.example.railwayenquiry.Fragments;

import android.content.Context;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.railwayenquiry.Adapters.TrainAdapter;
import com.example.railwayenquiry.Adapters.TrainItem;
import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModels.TrainViewModel;

import java.util.ArrayList;
import java.util.List;


public class StationScheduleFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StationScheduleFragment2() {
    }



    public static StationScheduleFragment2 newInstance(String param1, String param2) {
        StationScheduleFragment2 fragment = new StationScheduleFragment2();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station_schedule2, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        List<TrainItem> TrainList=new ArrayList<>();
        final TrainAdapter mAdapter = new TrainAdapter(TrainList,null,null,"StationScheduleFragment2.class");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        TrainViewModel mTrainViewModel = new ViewModelProvider(this).get(TrainViewModel.class);


        mTrainViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<TrainItem>>() {
            @Override
            public void onChanged(@Nullable final List<TrainItem> words) {
                mAdapter.setRows(words,null,null);
            }
        });

        TextView title=view.findViewById(R.id.textView10);
        title.setText(mParam1);

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
