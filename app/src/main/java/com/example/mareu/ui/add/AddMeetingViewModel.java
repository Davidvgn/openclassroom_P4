package com.example.mareu.ui.add;

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
    public void onNameChanged(String subject) {
        isSaveButtonEnabledMutableLiveData.setValue(!subject.isEmpty());
    }
    public void onAddButtonClicked(
             String hour,
             String min,
             String meetingRoom,
             String meetingSubject,
             String participants
    ) {
        meetingRepository.addMeeting(hour, min, meetingRoom, meetingSubject, participants);
        closeActivitySingleLiveEvent.call();
    }
}
