package com.example.mareu.ui.add;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.MeetingRepository;
import com.example.mareu.utils.SingleLiveEvent;


public class AddMeetingViewModel extends ViewModel {

    private final MeetingRepository meetingRepository;
    private final MutableLiveData<Boolean> calendarMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> hourMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> roomMutableLiveData = new MutableLiveData<>(false);

    private final MediatorLiveData<Boolean> isSaveButtonEnabledMediatorLiveData = new MediatorLiveData<>();

    @NonNull
    public String roomSelected = "";

    public AddMeetingViewModel(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;

        isSaveButtonEnabledMediatorLiveData.addSource(calendarMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calendarOnChanged) {
                combine(calendarOnChanged, hourMutableLiveData.getValue(), roomMutableLiveData.getValue());
            }
        });
        isSaveButtonEnabledMediatorLiveData.addSource(hourMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hourOnchanged) {
                combine(hourOnchanged, calendarMutableLiveData.getValue(), roomMutableLiveData.getValue());
            }
        });
        isSaveButtonEnabledMediatorLiveData.addSource(roomMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean roomOnChanged) {
                combine(roomOnChanged, calendarMutableLiveData.getValue(), hourMutableLiveData.getValue());
            }
        });
    }

    private void combine(Boolean calendar, Boolean hour, Boolean room) {
        Boolean calendarCombine = calendar;
        Boolean hourCombine = hour;
        Boolean roomCombine = hour;
        if (calendarCombine && hourCombine && roomCombine) {
            isSaveButtonEnabledMediatorLiveData.setValue(true);
        }
    }


    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();


    public LiveData<Boolean> getIsSaveButtonEnabledLiveData() {
        return isSaveButtonEnabledMediatorLiveData;
    }


    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    public void onCalendarValueChanged(String calendar) {
        if ((!calendar.isEmpty())) {
            calendarMutableLiveData.setValue(true);
        }
    }
    public void onTimeValueChanged(String calendar) {
        if ((!calendar.isEmpty())) {
            hourMutableLiveData.setValue(true);
        }
    }
    public void onRoomValueChanged(String calendar) {
        if ((!calendar.isEmpty())) {
            roomMutableLiveData.setValue(true);
        }
    }

    public void onAddButtonClicked(
            @Nullable String day,
            @Nullable String time,
            @Nullable String meetingSubject,
            @Nullable String participants
    ) {
        //todo David check pour plusieurs mails séparés d'une virgule (ou autre)
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (participants.matches(regex)) {
            meetingRepository.addMeeting(day, time, roomSelected, meetingSubject, participants);
            closeActivitySingleLiveEvent.call();
        } else {
            //todo Nino toast pour prévenir l'utilisateur si adresse non valide non fonctionnelle
//            Toast.makeText(AddMeetingViewModel.this, "Email non-valide", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRoomSelected(CharSequence room) {
        roomSelected = room.toString();
    }

}
