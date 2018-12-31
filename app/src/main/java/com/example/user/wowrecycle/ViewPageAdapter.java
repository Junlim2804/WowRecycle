package com.example.user.wowrecycle;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {


    private Context context;

    public ViewPageAdapter(FragmentManager fm, Context mContext){
        super(fm);
        context = mContext;

    }

    @Override
    public Fragment getItem(int position){
        if (position == 0){
            return new FragmentBrowse();
        }
        else{
            return new FragmentMyRewards();
        }
    }

    @Override
    public int getCount() {return 2;}

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return context.getResources().getString(R.string.app_name);
            case 1:
                return context.getResources().getString(R.string.app_name);
            default:
                return null;
        }
    }
}
