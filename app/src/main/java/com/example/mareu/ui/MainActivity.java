package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.ui.add.AddMeetingActivity;
import com.example.mareu.ui.list.MeetingFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolbar);

        MainViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MeetingFragment.newInstance())
                .commitNow();
        }

        binding.mainFabAdd.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));

        binding.mainButtonFilter.setVisibility(View.GONE);


        ArrayAdapter<CharSequence> roomArrayAdapter = ArrayAdapter.createFromResource(this, R.array.room, R.layout.support_simple_spinner_dropdown_item);
        binding.mainActRoom.setAdapter(roomArrayAdapter);

//        roomAutoCompleteTextView.setOnItemClickListener((parent, view, position, id)
//        -> viewModel.onRoomSelected(roomArrayAdapter.getItem(position)));

        binding.mainActRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onRoomSelected(roomArrayAdapter.getItem(position));
                binding.mainButtonFilter.setVisibility(View.VISIBLE);
            }
        });

        binding.mainFilterByDateButton.setOnClickListener(v -> {
            binding.mainButtonFilter.setVisibility(View.VISIBLE);

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR); // current year
            int month = c.get(Calendar.MONTH); // current month
            int day = c.get(Calendar.DAY_OF_MONTH); // current day
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                (view, selectedYear, selectedMonthOfYear, selectedDayOfMonth) -> 
                    viewModel.onDateChanged(selectedDayOfMonth, selectedMonthOfYear + 1, selectedYear),
                year,
                month,
                day
            );
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();

        });

        binding.mainButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeFilterButton();
                binding.mainButtonFilter.setVisibility(View.GONE);
                binding.mainActRoom.setText("");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }
}
