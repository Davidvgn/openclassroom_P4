package com.example.mareu.ui.add;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.MeetingRepository;
import com.example.mareu.utils.SingleLiveEvent;


public class AddMeetingViewModel extends ViewModel {

    private final MeetingRepository meetingRepository;
    private final MutableLiveData<Boolean> isSaveButtonEnabledMutableLiveData = new MutableLiveData<>(false);

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
            String day,
            String time,
            String meetingRoom,
            String meetingSubject,
            String participants
    ) {
        meetingRepository.addMeeting(day, time, meetingRoom, meetingSubject, participants);
        closeActivitySingleLiveEvent.call();
    }
}
