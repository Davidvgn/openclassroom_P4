package com.example.mareu.ui.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;

import com.example.mareu.databinding.MeetingsFragmentBinding;
import com.example.mareu.ui.ViewModelFactory;

import java.util.List;

public class MeetingFragment extends Fragment {

    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private MeetingViewModel viewModel;
    private MeetingsFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Allows to control Menu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MeetingsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("Please use MeetingFragment.newInstance() to build the Fragment");
        }

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
        MeetingAdapter adapter = new MeetingAdapter(meetingId -> viewModel.onDeleteMeetingClicked(meetingId));
        binding.meetingRv.setAdapter(adapter);

        viewModel.getMeetingViewStateItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<MeetingViewStateItem>>() {
            @Override
            public void onChanged(List<MeetingViewStateItem> list) {
                adapter.submitList(list);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date:
                Toast.makeText(getContext(), this.getString(R.string.sort_by_date), Toast.LENGTH_SHORT).show();
                viewModel.onDateSortButtonClicked();
                return true;
            case R.id.menu_room:
                Toast.makeText(getContext(), this.getString(R.string.sort_by_room), Toast.LENGTH_SHORT).show();
                viewModel.onRoomSortButtonClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}

