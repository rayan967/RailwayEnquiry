package com.example.railwayenquiry.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import com.example.railwayenquiry.R;
import com.example.railwayenquiry.ViewModels.TTViewModel;
import com.example.railwayenquiry.Adapters.TimeTableAdapter;
import com.example.railwayenquiry.Adapters.TimeTableItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LiveStatusFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LiveStatusFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveStatusFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<TimeTableItem> TTList=new ArrayList<>();;
    TimeTableAdapter mAdapter;
    private TTViewModel mTTViewModel;

    private OnFragmentInteractionListener mListener;

    public LiveStatusFragment2() {
        // Required empty public constructor
    }



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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        mTTViewModel = ViewModelProviders.of(this).get(TTViewModel.class);

        mTTViewModel.getAllRows().observe(this, new Observer<List<TimeTableItem>>() {
            @Override
            public void onChanged(@Nullable final List<TimeTableItem> words) {
                mAdapter.setRows(words);
            }
        });


        mTTViewModel.getProperties().observe(this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable final HashMap<String, String> stringHashMap) {
                String train_number=stringHashMap.get("number");
                String schedule=stringHashMap.get("schedule");
                String title=train_number+" - Schedule - "+schedule;
                Title.setText(title);
                String status=stringHashMap.get("status");
                status="<b>Status:</b> "+status;
                Status.setText(Html.fromHtml(status));
                Log.d("Values",stringHashMap.get("name"));
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
