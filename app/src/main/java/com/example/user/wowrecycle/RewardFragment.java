package com.example.user.wowrecycle;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RewardFragment extends Fragment {

    private FragmentActivity mContext;

    public RewardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reward, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        // using getFragmentManager() will work too
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),mContext);
        adapter.addFragment(new FragmentBrowse(),"Browse");
        adapter.addFragment(new FragmentMyRewards(),"My Reward");

        viewPager.setAdapter(adapter);

       TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.sliding_tabs);
       tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onAttach(Activity activity) {
        mContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }




}