package com.group2.foodie.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group2.foodie.view.fragment.MyFollowersFragment;
import com.group2.foodie.view.fragment.DailyRecipeFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DailyRecipeFragment();
            case 1:
                return new MyFollowersFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
