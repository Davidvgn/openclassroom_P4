package com.example.mareu.ui.add;


import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.MeetingRepository;
import com.example.mareu.utils.SingleLiveEvent;


public class AddMeetingViewModel extends ViewModel {

    private final MeetingRepository meetingRepository;
    private final MutableLiveData<Boolean> isSaveButtonEnabledMutableLiveData = new MutableLiveData<>(false);

    @NonNull
    public String roomSelected = "";

    public AddMeetingViewModel(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();

    public LiveData<Boolean> getIsSaveButtonEnabledLiveData() {
        return isSaveButtonEnabledMutableLiveData;
    }

    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    public void onValueChanged(String subject) {
        isSaveButtonEnabledMutableLiveData.setValue(!subject.isEmpty());
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
