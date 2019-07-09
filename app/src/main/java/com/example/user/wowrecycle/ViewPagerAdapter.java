package com.example.user.wowrecycle;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private final List<Fragment> lstFragment=new ArrayList<>();
    private final List<String> lstTitle=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm, Context mContext){
        super(fm);
        context = mContext;

    }




    public Fragment getItem(int position){
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {return 2;}

    @Override
    public CharSequence getPageTitle(int position) {
       return lstTitle.get(position);
    }

    public void addFragment(Fragment fragment,String title)
    {
        lstFragment.add(fragment);
        lstTitle.add(title);
    }
}
