package com.example.railwayenquiry.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Slide;
import android.transition.TransitionInflater;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.railwayenquiry.R;

import static android.content.ContentValues.TAG;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
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
        final LinearLayout ll=(LinearLayout) getView().findViewById(R.id.card1);
        final TextView tv=(TextView) getView().findViewById(R.id.textView);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


 /*               Pair<View, String> p1 = Pair.create((View)ll, ViewCompat.getTransitionName(ll));
                Pair<View, String> p2 = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                Pair[] p={p1,p2};
                Intent intent = new Intent(context, LiveStatus.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), p2);
                ActivityCompat.startActivity(context, intent, options.toBundle());*/

/*
                androidx.core.app.ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        Pair.create((View)imageView, ViewCompat.getTransitionName(imageView)),
                        Pair.create((View)ll, ViewCompat.getTransitionName(ll)));
                ActivityNavigator.Extras extras = new ActivityNavigator.Extras.Builder()
                        .setActivityOptions(options)
                        .build();
                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_live_status,
                        null, // Bundle of args
                        null, // NavOptions
                        extras);*/

                LiveStatusFragment simpleFragmentB = LiveStatusFragment.newInstance(null,null);
                MainFragment simpleFragmentA=(MainFragment)getFragmentManager().findFragmentByTag("MainFragment");
                simpleFragmentB.setExitTransition(new Slide(Gravity.TOP));
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.f1content,simpleFragmentB,"LiveStatusFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(ll, ViewCompat.getTransitionName(ll));
                ft.addSharedElement(imageView,ViewCompat.getTransitionName(imageView));
                ft.addSharedElement(tv,ViewCompat.getTransitionName(tv));


                ft.commit();

          /*      FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                        .addSharedElement(ll, ViewCompat.getTransitionName(ll))
                        .build();
                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_frag_live_status,
                        null, // Bundle of args
                        null, // NavOptions
                        extras);*/

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
