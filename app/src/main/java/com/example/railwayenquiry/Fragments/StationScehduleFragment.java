package com.example.railwayenquiry.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.railwayenquiry.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.railwayenquiry.Activities.MainActivity.hideKeyboardFrom;


public class StationScehduleFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    View _rootView;

    private OnFragmentInteractionListener mListener;

    public StationScehduleFragment() {

    }

    public static StationScehduleFragment newInstance(String param1, String param2) {
        StationScehduleFragment fragment = new StationScehduleFragment();
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
        Transition sharedElementEnterTransition = (Transition) getSharedElementEnterTransition();
        sharedElementEnterTransition.addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(android.transition.Transition transition) {
                super.onTransitionEnd(transition);
                ConstraintLayout cl=(ConstraintLayout) getView().findViewById(R.id.constraintLayout);
                Slide slide = new Slide(Gravity.BOTTOM);
                TransitionManager.beginDelayedTransition(cl, slide);
                cl.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(_rootView==null)
            _rootView= inflater.inflate(R.layout.fragment_station_schedule, container, false);
        return _rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {


        final AutoCompleteTextView textView = (AutoCompleteTextView) getView().findViewById(R.id.autocomplete_card5);
        final String[] stations = getResources().getStringArray(R.array.stations);
        final List<String> stationlist = new ArrayList<>(Arrays.asList(stations));
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), R.layout.material_spinner_item, stations);
        textView.setAdapter(adapter);

        final TextInputLayout til = (TextInputLayout) getView().findViewById(R.id.textInputLayout);


        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                hideKeyboardFrom(getContext(), getView());
                til.setError(null);
            }
        });

    Button button=(Button) getView().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v)
        {

            String station=textView.getText().toString();
            if(stationlist.contains(station)) {
                StationScheduleFragment2 simpleFragmentB = StationScheduleFragment2.newInstance(station, null);
                simpleFragmentB.setEnterTransition(new Slide(Gravity.BOTTOM));
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content, simpleFragmentB)
                        .addToBackStack(TAG);

                ft.commit();
            }
            else
            {
                til.setError("\t Please select correct station");
            }
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
