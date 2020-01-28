package com.example.railwayenquiry.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.example.railwayenquiry.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TrainBwStationsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrainBwStationsFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static TrainBwStationsFragment newInstance(String param1, String param2) {
        TrainBwStationsFragment fragment = new TrainBwStationsFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
             //   LinearLayout ll = (LinearLayout) getView().findViewById(R.id.livestatuscard);
             //   RelativeLayout rl = (RelativeLayout) getView().findViewById(R.id.rl2);
              //  Button button = (Button) getView().findViewById(R.id.button);

                ConstraintLayout cl= (ConstraintLayout) getView().findViewById(R.id.animatableLayout);
                Slide slide = new Slide();
                TransitionManager.beginDelayedTransition(cl, slide);
                cl.setVisibility(View.VISIBLE);
             //   button.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_train_bw_stations, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState){
        final ScrollView scroll=view.findViewById(R.id.scrollView);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                if (view.getRootView().getHeight() - (r.bottom - r.top) > 500) {
                    scroll.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scroll.fullScroll(View.FOCUS_DOWN);
                        }
                    },100);

                } else {

                }
            }
        });

        final AutoCompleteTextView textView = (AutoCompleteTextView) getView().findViewById(R.id.autocomplete_card);
        final AutoCompleteTextView textView2 = (AutoCompleteTextView) getView().findViewById(R.id.autocomplete_card2);
        final String[] stations = getResources().getStringArray(R.array.stations);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stations);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stations);
        textView.setAdapter(adapter);
        textView2.setAdapter(adapter2);


        final TextView date = (TextView) getView().findViewById(R.id.dateview);
        ImageView calendaricon= getView().findViewById(R.id.imageView6);
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        if((mMonth+1)<10)
            date.setText(mDay + "-0" + (mMonth+1) + "-" + mYear);
        else
            date.setText(mDay + "-" + (mMonth+1) + "-" + mYear);
        calendaricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if((monthOfYear+1)<10)
                                    date.setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                else
                                    date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


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
