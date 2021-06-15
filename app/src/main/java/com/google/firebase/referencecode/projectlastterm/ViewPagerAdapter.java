package com.google.firebase.referencecode.projectlastterm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new ManageMenuFragment();
            case 1:
                return new ManageEmployeeFragment();
        }
        return new ManageMenuFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }



    @Nullable
    @Override

    public CharSequence getPageTitle(int position) {
        String title="";
        if (position==0)
        {
            title="Menu";
        }
        else
        {
            title="Employee";
        }

        return title;
    }
}
