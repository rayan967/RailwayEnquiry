package com.example.railwayenquiry.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.railwayenquiry.Adapters.RescheduleItem;
import com.example.railwayenquiry.Adapters.TrainAdapter;
import com.example.railwayenquiry.Adapters.TrainItem;
import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModels.RpageViewModel;

import java.util.ArrayList;
import java.util.List;

public class RPageFragment extends Fragment {

    private RpageViewModel mViewModel;
    private LiveStatusFragment.OnFragmentInteractionListener mListener;

    public static RPageFragment newInstance() {
        return new RPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rpage_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RpageViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        List<RescheduleItem> TrainList=new ArrayList<>();
        final TrainAdapter mAdapter = new TrainAdapter(null,TrainList,null,"RPageFragment.class");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        LottieAnimationView animationView=view.findViewById(R.id.animation_view);
        animationView.playAnimation();
        animationView.enableMergePathsForKitKatAndAbove( true );


        RpageViewModel mRpageViewModel = new ViewModelProvider(this).get(RpageViewModel.class);


        mRpageViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<RescheduleItem>>() {
            @Override
            public void onChanged(@Nullable final List<RescheduleItem> words) {
                if(words.size()>0) {
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.GONE);
                }
                mAdapter.setRows(null,words,null);
            }
        });


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LiveStatusFragment.OnFragmentInteractionListener) {
            mListener = (LiveStatusFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
