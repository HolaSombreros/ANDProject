package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group2.foodie.R;

import java.util.ArrayList;
import java.util.List;

public class FollowingFollowersFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followingfollowers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.followingFollowers_tabLayout);
        viewPager = view.findViewById(R.id.followingFollowers_viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyFollowersFragment());
        fragments.add(new MyFollowingFragment());

        adapter = new ViewPagerAdapter(fragments, getActivity());
        viewPager.setAdapter(adapter);

        String[] tabTitles = {"Followers", "Following"};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles[position]);
        }).attach();
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        private List<Fragment> fragments;

        public ViewPagerAdapter(List<Fragment> fragments, FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            this.fragments = fragments;
        }

        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}
