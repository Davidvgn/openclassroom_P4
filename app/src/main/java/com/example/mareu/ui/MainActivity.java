package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import com.example.mareu.R;

import com.example.mareu.ui.list.MeetingFragment;
import com.example.mareu.ui.add.AddMeetingActivity;
import com.example.mareu.ui.list.MeetingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    MeetingViewModel viewModel;
    ArrayAdapter<CharSequence> roomArrayAdapter;
//    String  my_var; //keep track!



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.main_toolbar));

//        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MeetingFragment.newInstance())
                .commitNow();
        }

        FloatingActionButton fab = findViewById(R.id.main_fab_add);
        fab.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));




        AutoCompleteTextView roomAutoCompleteTextView = findViewById(R.id.main_act_room);
        roomArrayAdapter = ArrayAdapter.createFromResource(this, R.array.room, R.layout.support_simple_spinner_dropdown_item);
        roomAutoCompleteTextView.setAdapter(roomArrayAdapter);
        roomAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> viewModel.onRoomSelected(roomArrayAdapter.getItem(position)));

//        roomAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                my_var = roomArrayAdapter.getItem(position).toString();
//            }
//        });
//        roomAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                my_var = null;
//            }
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });

        Button filterByDateButton = findViewById(R.id.main_filter_by_date_button);
        filterByDateButton.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR); // current year
            int month = c.get(Calendar.MONTH); // current month
            int day = c.get(Calendar.DAY_OF_MONTH); // current day
            datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                (view, selectedYear, selectedMonthOfYear, selectedDayOfMonth)
                    -> viewModel.onDateChanged(selectedDayOfMonth,
                    selectedMonthOfYear + 1, selectedYear),
                year,
                month,
                day
            );
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }
}
