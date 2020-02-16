package com.example.railwayenquiry.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;

import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.railwayenquiry.R;

import static android.content.ContentValues.TAG;


public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {

    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState)
    {
        final Context context=getActivity();
        final ImageView imageView=(ImageView) getView().findViewById(R.id.imageView);
        final CardView ll=(CardView) getView().findViewById(R.id.card1);
        final TextView tv=(TextView) getView().findViewById(R.id.textView);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LiveStatusFragment simpleFragmentB = LiveStatusFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Slide(Gravity.TOP));
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content,simpleFragmentB,"LiveStatusFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(ll, ViewCompat.getTransitionName(ll));
                ft.addSharedElement(imageView,ViewCompat.getTransitionName(imageView));
                ft.addSharedElement(tv,ViewCompat.getTransitionName(tv));


                ft.commit();
            }
        });


        final ImageView imageView2=(ImageView) getView().findViewById(R.id.imageView2);
        final CardView ll2=(CardView) getView().findViewById(R.id.card2);
        final TextView tv2=(TextView) getView().findViewById(R.id.textView2);

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TrainRouteFragment simpleFragmentB = TrainRouteFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Slide(Gravity.TOP));
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content,simpleFragmentB,"TrainRouteFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(ll2, ViewCompat.getTransitionName(ll2));
                ft.addSharedElement(imageView2,ViewCompat.getTransitionName(imageView2));
                ft.addSharedElement(tv2,ViewCompat.getTransitionName(tv2));
                ft.commit();
            }
        });


        final ImageView imageView4=(ImageView) getView().findViewById(R.id.imageView4);
        final CardView ll4=(CardView) getView().findViewById(R.id.card4);
        final TextView tv4=(TextView) getView().findViewById(R.id.textView4);

        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PNRStatusFragment simpleFragmentB = PNRStatusFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Slide(Gravity.TOP));
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content,simpleFragmentB,"PNRStatus")
                        .addToBackStack(TAG)
                        .addSharedElement(ll4, ViewCompat.getTransitionName(ll4));
                ft.addSharedElement(imageView4,ViewCompat.getTransitionName(imageView4));
                ft.addSharedElement(tv4,ViewCompat.getTransitionName(tv4));
                ft.commit();
            }
        });

        final ImageView imageView5=(ImageView) getView().findViewById(R.id.imageView5);
        final CardView ll5=(CardView) getView().findViewById(R.id.card5);
        final TextView tv5=(TextView) getView().findViewById(R.id.textView5);

        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StationScehduleFragment simpleFragmentB = StationScehduleFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Slide(Gravity.TOP));
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content,simpleFragmentB,"StationScehduleFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(ll5, ViewCompat.getTransitionName(ll5));
                ft.addSharedElement(imageView5,ViewCompat.getTransitionName(imageView5));
                ft.addSharedElement(tv5,ViewCompat.getTransitionName(tv5));
                ft.commit();
            }
        });

        final CardView ll6=(CardView) getView().findViewById(R.id.card6);
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RescheduleFragment simpleFragmentB = RescheduleFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Slide(Gravity.TOP));
                simpleFragmentB.setEnterTransition(new Fade());
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content,simpleFragmentB,"StationScehduleFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(ll6, ViewCompat.getTransitionName(ll6));
                ft.commit();

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
