package com.example.mareu.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;

public class FilterParametersRepository {

    private final MutableLiveData<LocalDate> dateFilterMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> roomFilterMutableLiveData = new MutableLiveData<>();

    public LiveData<LocalDate> getDateFilterMutableLiveData() {
        return dateFilterMutableLiveData;
    }

    public LiveData<String> getRoomFilterMutableLiveData() {
        return roomFilterMutableLiveData;
    }

    public void onDateChanged(int selectedDayOfMonth, int selectedMonthOfYear, int selectedYear) {
        dateFilterMutableLiveData.setValue(LocalDate.of(selectedYear, selectedMonthOfYear, selectedDayOfMonth));
    }

    public void onRoomSelected(CharSequence room) {
        roomFilterMutableLiveData.setValue(room.toString());
    }

    public void removeFilterButton() {
        dateFilterMutableLiveData.setValue(null);
        roomFilterMutableLiveData.setValue(null);
    }
}
