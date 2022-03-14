package com.example.mareu.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.Meeting;
import com.example.mareu.data.MeetingRepository;
import com.example.mareu.utils.SingleLiveEvent;

import java.util.List;

public class AddMeetingViewModel extends ViewModel {

    boolean emailValidation = true;

    private final MeetingRepository meetingRepository;
    private final MutableLiveData<Boolean> calendarMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> hourMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> roomMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isRoomAlreadyUsed = new MutableLiveData<>(false);

    private final MediatorLiveData<Boolean> isSaveButtonEnabledMediatorLiveData = new MediatorLiveData<>();

    private final LiveData<List<Meeting>> meetingsList = new LiveData<List<Meeting>>() {};



    @NonNull
    public String roomSelected = "";

    public AddMeetingViewModel(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;

        isSaveButtonEnabledMediatorLiveData.addSource(calendarMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calendarOnChanged) {
                combine(calendarOnChanged, hourMutableLiveData.getValue(), roomMutableLiveData.getValue(), isRoomAlreadyUsed.getValue());
            }
        });
        isSaveButtonEnabledMediatorLiveData.addSource(hourMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hourOnchanged) {
                combine(calendarMutableLiveData.getValue(), hourOnchanged, roomMutableLiveData.getValue(), isRoomAlreadyUsed.getValue());
            }
        });
        isSaveButtonEnabledMediatorLiveData.addSource(roomMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean roomOnChanged) {
                combine(calendarMutableLiveData.getValue(), hourMutableLiveData.getValue(), roomOnChanged, isRoomAlreadyUsed.getValue());
            }
        });
        isSaveButtonEnabledMediatorLiveData.addSource(isRoomAlreadyUsed, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean roomAlreadyUsed) {
                combine(calendarMutableLiveData.getValue(), hourMutableLiveData.getValue(), roomMutableLiveData.getValue(), roomAlreadyUsed);
            }
        });
    }


    private void combine(Boolean calendar, Boolean hour, Boolean room, Boolean roomAlreadyUsed) {
        if (calendar && hour && room && !roomAlreadyUsed) {
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
        String[] participantsSplitted = participants.split(",");
        String regex = "^[\\s]?+[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z{2,5}]+$";
        for (String addresses : participantsSplitted) {
            if (!addresses.matches(regex)) {
                emailValidation = false;
                //todo Nino toast pour prévenir l'utilisateur si adresse non valide -> non fonctionnelle
//                Toast.makeText(AddMeetingViewModel.this, "Email non-valide", Toast.LENGTH_SHORT).show();
            } else {
                emailValidation = true;
            }
        }
        if (emailValidation) {
            meetingRepository.addMeeting(day, time, roomSelected, meetingSubject, participants);
            closeActivitySingleLiveEvent.call();
        }
    }

    public void onRoomSelected(CharSequence room) {
        roomSelected = room.toString();
    }
}