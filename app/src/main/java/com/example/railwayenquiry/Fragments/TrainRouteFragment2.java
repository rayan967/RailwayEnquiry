package com.example.railwayenquiry.Fragments;

import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.railwayenquiry.Adapters.TimeTableAdapter;
import com.example.railwayenquiry.Adapters.TimeTableItem;
import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModels.TTViewModel;
import com.example.railwayenquiry.ViewModels.TrainRouteViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class TrainRouteFragment2 extends Fragment implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<TimeTableItem> mTTList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrainRouteFragment2() {
    }


    public static TrainRouteFragment2 newInstance(String param1, String param2) {
        TrainRouteFragment2 fragment = new TrainRouteFragment2();
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

        return inflater.inflate(R.layout.fragment_train_route2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        final TextView Title= getView().findViewById(R.id.textView11);
        List<TimeTableItem> TTList=new ArrayList<>();
        final TimeTableAdapter mAdapter = new TimeTableAdapter(TTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        final TTViewModel mTTViewModel = new ViewModelProvider(this).get(TTViewModel.class);

        mTTViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<TimeTableItem>>() {
            @Override
            public void onChanged(@Nullable final List<TimeTableItem> words) {
                mTTList=words;
                mAdapter.setRows(words,false);
            }
        });

        final TrainRouteViewModel mTRViewModel = new ViewModelProvider(this).get(TrainRouteViewModel.class);

        mTRViewModel.getProperties().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable final HashMap<String, String> stringHashMap) {
                String train_number=stringHashMap.get("number");
                String schedule=stringHashMap.get("schedule");
                String title=train_number+" - Schedule - "+schedule;
                Title.setText(title);
                Log.d("Values",stringHashMap.get("name"));
            }
        });


        FloatingActionButton fab=view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog_box, null);

                String[] station_list=new String[mTTList.size()];
                for(int i=0;i<mTTList.size();i++)
                {
                    station_list[i]=mTTList.get(i).station_name;
                }


                final List<String> stations=new ArrayList<>(Arrays.asList(station_list));
                final AutoCompleteTextView textView1 = (AutoCompleteTextView) layout.findViewById(R.id.start_autocomplete);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), R.layout.material_spinner_item, station_list);
                textView1.setAdapter(adapter);

                final AutoCompleteTextView textView2 = (AutoCompleteTextView) layout.findViewById(R.id.destination_autocomplete);
                ArrayAdapter<String> adapter2 =
                        new ArrayAdapter<>(getActivity(), R.layout.material_spinner_item, station_list);
                textView2.setAdapter(adapter2);


                textView1.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        textView1.showDropDown();
                        return false;
                    }
                });

                textView2.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        textView2.showDropDown();
                        return false;
                    }
                });


                final TextView date = (TextView) layout.findViewById(R.id.dateview);
                ImageView calendaricon= layout.findViewById(R.id.imageView6);
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

                        Calendar now = Calendar.getInstance();
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                TrainRouteFragment2.this,
                                now.get(Calendar.YEAR), // Initial year selection
                                now.get(Calendar.MONTH), // Initial month selection
                                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                        );
                        Calendar last_date=Calendar.getInstance();
                        last_date.add(Calendar.MONTH,4);
                        dpd.setMinDate(now);
                        dpd.setMaxDate(last_date);
                        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                        dpd.show(getChildFragmentManager(), "Datepickerdialog");
                    }

                });

                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Fare Enquiry")
                        .setView(layout)
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTTViewModel.getFare().observe(getViewLifecycleOwner(), new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        new MaterialAlertDialogBuilder(getContext())
                                                .setTitle("Fare Enquiry")
                                                .setMessage("Fare is Rs. "+s)
                                                .setPositiveButton("OK",null)
                                                .show();
                                    }
                                });

                            }
                        })
                        .show();
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


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
    }

}
