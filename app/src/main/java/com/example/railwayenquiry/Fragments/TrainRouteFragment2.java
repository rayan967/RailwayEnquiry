package com.example.railwayenquiry.Fragments;

import android.app.Activity;

import android.app.Application;
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

import android.text.TextUtils;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.railwayenquiry.Adapters.TimeTableAdapter;
import com.example.railwayenquiry.Adapters.TimeTableItem;
import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModelFactory.FareVMFactory;
import com.example.railwayenquiry.ViewModelFactory.RouteViewModelFactory;
import com.example.railwayenquiry.ViewModelFactory.TTViewModelFactory;
import com.example.railwayenquiry.ViewModels.MainViewModel;
import com.example.railwayenquiry.ViewModels.TTViewModel;
import com.example.railwayenquiry.ViewModels.TrainRouteViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
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
    TextView date;
    String classes;
    String daysofweek;
    String Train_no;

    // TODO: Rename and change types of parameters
    private String train;
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
            train = getArguments().getString(ARG_PARAM1);
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
        mTTList=new ArrayList<>();
        final TimeTableAdapter mAdapter = new TimeTableAdapter(mTTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        String train_no=TTViewModel.getTrain_no(train);
        final Application app=this.getActivity().getApplication();

        RouteViewModelFactory factory = new RouteViewModelFactory(app, train_no);

        final TrainRouteViewModel mTRViewModel = new ViewModelProvider(this,factory).get(TrainRouteViewModel.class);

        final LottieAnimationView animationView=view.findViewById(R.id.animation_view);
        animationView.playAnimation();

        mTRViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<TimeTableItem>>() {
            @Override
            public void onChanged(@Nullable final List<TimeTableItem> words) {

                if(words.size()>0) {
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.GONE);
                }
                mTTList=words;
                mAdapter.setRows(words,false);
            }
        });


        mTRViewModel.getProperties().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable final HashMap<String, String> stringHashMap) {
                String train_number=stringHashMap.get("number");
                Train_no=train_number;
                String schedule=stringHashMap.get("schedule");

                String status=stringHashMap.get("status");
                String statusmessage=stringHashMap.get("status_message");
                if(MainViewModel.isUnsuccessful(status,statusmessage)){

                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Route Enquiry")
                            .setMessage(statusmessage)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().onBackPressed();
                                }
                            })
                            .show();

                }
                String title=TrainRouteViewModel.getTitle(train_number,schedule,status,statusmessage);
                Title.setText(title);
                Log.d("Values",stringHashMap.get("name"));
                classes=stringHashMap.get("Classes");
                daysofweek=stringHashMap.get("DaysOfWeek");
            }
        });


        FloatingActionButton fab=view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog_box, null);

                String[] station_list=TrainRouteViewModel.getStation_List(mTTList);
                String[] station_code_list=TrainRouteViewModel.getStation_CodeList(mTTList);


                final List<String> stations=new ArrayList<>(Arrays.asList(station_list));
                final AutoCompleteTextView textView1 = (AutoCompleteTextView) layout.findViewById(R.id.start_autocomplete);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), R.layout.material_spinner_item, station_list);
                textView1.setAdapter(adapter);

                final AutoCompleteTextView textView2 = (AutoCompleteTextView) layout.findViewById(R.id.destination_autocomplete);
                ArrayAdapter<String> adapter2 =
                        new ArrayAdapter<>(getActivity(), R.layout.material_spinner_item, station_list);
                textView2.setAdapter(adapter2);

                final AutoCompleteTextView textView3= layout.findViewById(R.id.Class_autocomplete);


                List<String> Classes=TrainRouteViewModel.getClasses(classes);
                String[] final_classes=new String[Classes.size()];
                final_classes=Classes.toArray(final_classes);

                ArrayAdapter<String> adapter3=new ArrayAdapter<>(getActivity(),R.layout.material_spinner_item,final_classes);
                textView3.setAdapter(adapter3);


                List<Integer> weekdays=TrainRouteViewModel.getWeekDays(daysofweek);

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

                textView3.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        textView3.showDropDown();
                        return false;
                    }
                });



                date = (TextView) layout.findViewById(R.id.dateview);
                ImageView calendaricon= layout.findViewById(R.id.imageView6);


                String dateText=TrainRouteViewModel.getToday();
                date.setText(dateText);


                calendaricon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar now=Calendar.getInstance();
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                TrainRouteFragment2.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );
                        dpd = TrainRouteViewModel.modifyDatePicker(dpd, now, weekdays);
                        dpd.show(getChildFragmentManager(), "Datepickerdialog");
                    }

                });
                TextInputEditText age=layout.findViewById(R.id.age);

                AlertDialog builder = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Fare Enquiry")
                        .setView(layout)
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FareVMFactory factory = new FareVMFactory(app);
                                if (TrainRouteViewModel.NegativeCondition(stations, textView1, textView2, textView3, age, Classes))
                                    new MaterialAlertDialogBuilder(getContext())
                                            .setTitle("Fare Enquiry")
                                            .setMessage("Please enter correct details")
                                            .setPositiveButton("OK", null)
                                            .show();
                                 else
                                     {
                                         animationView.playAnimation();
                                         TrainRouteViewModel trainRouteViewModel = new ViewModelProvider(getActivity(), factory).get(TrainRouteViewModel.class);
                                         trainRouteViewModel.getFare(Train_no, station_code_list[stations.indexOf(textView1.getText().toString())], station_code_list[stations.indexOf(textView2.getText().toString())], age.getText().toString(), textView3.getText().toString(), date.getText().toString())
                                                 .observe(getViewLifecycleOwner(), new Observer<String>() {
                                             @Override
                                             public void onChanged(String s) {

                                                 animationView.pauseAnimation();
                                                 animationView.setVisibility(View.GONE);
                                                 new MaterialAlertDialogBuilder(getContext())
                                                         .setTitle("Fare Enquiry")
                                                         .setMessage("Fare is Rs. " + s)
                                                         .setPositiveButton("OK", null)
                                                         .show();
                                        }
                                    });
                                }

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
        if((monthOfYear+1)<10)
            date.setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
        else
            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

    }

}
