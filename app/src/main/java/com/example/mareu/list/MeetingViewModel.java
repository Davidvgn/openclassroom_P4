package com.example.mareu.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.Meeting;
import com.example.mareu.data.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingViewModel extends ViewModel {

    private final MeetingRepository meetingRepository;

    public MeetingViewModel(MeetingRepository meetingRepository) {
    this.meetingRepository = meetingRepository;
    }
    public LiveData<List<MeetingViewStateItem>> getMeetingViewStateItemsLiveData() {
        return Transformations.map(meetingRepository.getMeetingsLiveData(), meetings -> {
            List<MeetingViewStateItem> meetingViewStateItem = new ArrayList<>();

            for (Meeting meeting : meetings) {
                meetingViewStateItem.add(
                new MeetingViewStateItem(
                        meeting.getId(),
                        meeting.getHour(),
                        meeting.getMin(),
                        meeting.getMeetingRoom(),
                        meeting.getMeetingSubject(),
                        meeting.getParticipants()
                )
                );
            }
            return meetingViewStateItem;
        });
    }

    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }
}

