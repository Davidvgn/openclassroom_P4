package com.example.mareu.ui.add;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.app.TimePickerDialog;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.ui.ViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayAdapter<CharSequence> arrayAdapter_room;
    TimePickerDialog picker;
    DatePickerDialog datePickerDialog;


    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

        TextInputEditText subjectEditText = findViewById(R.id.add_meeting_subject_tie_subject);
        TextInputEditText participantEditText = findViewById(R.id.add_meeting_tie_participants);
        Button addMeetingButton = findViewById(R.id.add_meeting_button);

        TextInputLayout roomSpinner = findViewById(R.id.add_meeting_til_room);
        AutoCompleteTextView act_room = findViewById(R.id.add_meeting_act_room);
        arrayAdapter_room = ArrayAdapter.createFromResource(this, R.array.room, R.layout.support_simple_spinner_dropdown_item);
        act_room.setAdapter(arrayAdapter_room);
        act_room.setThreshold(1);


        TextInputEditText date = findViewById(R.id.add_meeting_tie_day);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/" + checkDigit(monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        TextInputEditText time_editText = findViewById(R.id.add_meeting_tie_time);
        time_editText.setInputType(InputType.TYPE_NULL);
        time_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                picker = new TimePickerDialog(AddMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                time_editText.setText("" + checkDigit(sHour) + ":" + checkDigit(sMinute));
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        bindTime(viewModel, date);
        bindAddButton(viewModel, date, time_editText, roomSpinner, subjectEditText, participantEditText, addMeetingButton);

        viewModel.getCloseActivitySingleLiveEvent().observe(this, aVoid -> finish());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindTime(AddMeetingViewModel viewModel, TextInputEditText date) {
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onValueChanged(s.toString());
            }
        });
    }

    private void bindAddButton(AddMeetingViewModel viewModel, TextInputEditText date, TextInputEditText time_editText, TextInputLayout roomSpinner, TextInputEditText subjectEditText, TextInputEditText participantsEditText, Button addMeetingButton) {
        addMeetingButton.setOnClickListener(v -> viewModel.onAddButtonClicked(
                date.getText().toString(),
                time_editText.getText().toString(),
                roomSpinner.getTransitionName(),
                subjectEditText.getText().toString(),
                participantsEditText.getText().toString()
        ));
        viewModel.getIsSaveButtonEnabledLiveData().observe(this, isSaveButtonEnabled -> addMeetingButton.setEnabled(isSaveButtonEnabled));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}


