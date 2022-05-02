package com.example.mareu.ui;

import androidx.lifecycle.ViewModel;

import com.example.mareu.data.FilterParametersRepository;

public class MainViewModel extends ViewModel {

    private final FilterParametersRepository filterParametersRepository;

    public MainViewModel(FilterParametersRepository filterParametersRepository) {
        this.filterParametersRepository = filterParametersRepository;
    }

    @SuppressWarnings("unused")
    public void onDateChanged(int selectedDayOfMonth, int selectedMonthOfYear, int selectedYear) {
        filterParametersRepository.onDateChanged(selectedDayOfMonth, selectedMonthOfYear, selectedYear);
    }

    public void onRoomSelected(CharSequence room) {
        filterParametersRepository.onRoomSelected(room);
    }

    public void removeFilterButton() {
        filterParametersRepository.removeFilterButton();
    }

}
