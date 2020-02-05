package com.example.railwayenquiry.Adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.railwayenquiry.Fragments.CPageFragment;
import com.example.railwayenquiry.Fragments.RPageFragment;
import com.example.railwayenquiry.R;

public class ReschedulePagerAdapter extends FragmentStatePagerAdapter {

        Context mContext;
    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.tab_text_1, R.string.tab_text_2};

        public ReschedulePagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            Log.d("ReschedulePagerAdapter:", "R");
            this.mContext=mContext;
        }


        @Override
        public Fragment getItem(int position) {
            Log.d("ReschedulePager:", "Before Switch");
            switch (position) {
                case 0:
                    return RPageFragment.newInstance();
                case 1:
                    return CPageFragment.newInstance();
                default: {
                    Log.d("ReschedulePager:", "Null returned");
                    return null;
                }
            }
        }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }


    @Override
        public int getCount() {
            return 2;
        }

}
