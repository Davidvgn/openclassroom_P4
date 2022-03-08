package com.example.mareu.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.ui.ViewModelFactory;

public class MeetingFragment extends Fragment {

    MeetingViewModel viewModel;

    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private MeetingViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Allows to control Menu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meetings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("Please use MeetingFragment.newInstance() to build the Fragment");
        }

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.meeting_rv);

        MeetingAdapter adapter = new MeetingAdapter(viewModel::onDeleteMeetingClicked);
        recyclerView.setAdapter(adapter);

        viewModel.getMeetingViewStateItemsLiveData().observe(getViewLifecycleOwner(), adapter::submitList);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date:

                viewModel.onDateSortButtonClicked();
                Toast.makeText(getContext(), "Date", Toast.LENGTH_SHORT).show();

                viewModel.onDateSortButtonClicked();

                return true;
            case R.id.menu_room:
                Toast.makeText(getContext(), "Salle", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

