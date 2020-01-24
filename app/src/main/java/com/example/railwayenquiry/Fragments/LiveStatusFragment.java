package com.example.railwayenquiry.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.railwayenquiry.R;
import com.google.android.material.textfield.TextInputLayout;

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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;



public class LiveStatusFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View _rootView;

    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public LiveStatusFragment() {
        // Required empty public constructor
    }


    public static LiveStatusFragment newInstance(String param1, String param2) {
        LiveStatusFragment fragment = new LiveStatusFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
                LinearLayout ll = (LinearLayout) getView().findViewById(R.id.livestatuscard);
                RelativeLayout rl = (RelativeLayout) getView().findViewById(R.id.rl2);
                Button button = (Button) getView().findViewById(R.id.button);
                Slide slide = new Slide();
                TransitionManager.beginDelayedTransition(rl, slide);
                ll.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(_rootView==null)
        _rootView= inflater.inflate(R.layout.fragment_live_status, container, false);
        return _rootView;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState)
    {

        final AutoCompleteTextView textView = (AutoCompleteTextView) getView().findViewById(R.id.autocomplete_card1);
        final String[] countries = getResources().getStringArray(R.array.train_list);
        final List<String> trainlist=new ArrayList<>(Arrays.asList(countries));
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);


        Button button=(Button) getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                String train=textView.getText().toString();
                if(trainlist.contains(train)) {
                    LiveStatusFragment2 simpleFragmentB = LiveStatusFragment2.newInstance(null, null);
                    simpleFragmentB.setEnterTransition(new Slide(Gravity.BOTTOM));
                    FragmentTransaction ft = getFragmentManager().beginTransaction()
                            .replace(R.id.f1content, simpleFragmentB)
                            .addToBackStack(TAG);
                    ft.commit();
                }
                else
                {
                    TextInputLayout til = (TextInputLayout) getView().findViewById(R.id.textinputlayout1);
                    til.setError("\t Please select correct train");
                }
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

    @Override
    public void onDestroyView() {
        if (_rootView.getParent() != null) {
            ((ViewGroup)_rootView.getParent()).removeView(_rootView);
        }
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

