package com.example.mareu.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.mareu.list.MeetingFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    @NonNull
    private final Context context;

    public MainPagerAdapter(@NonNull Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
            return MeetingFragment.newInstance();

    }
    @Override
    public int getCount() {
        return 1;
    }


}
