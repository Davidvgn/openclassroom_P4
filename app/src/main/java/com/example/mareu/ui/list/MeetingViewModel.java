package com.example.mareu.ui.list;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.Meeting;
import com.example.mareu.data.MeetingRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MeetingViewModel extends ViewModel {

    private final MeetingRepository meetingRepository;

    private final MediatorLiveData<List<MeetingViewStateItem>> mediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> isSortingAlphabeticallyMutableLiveData = new MutableLiveData<>();

    public MeetingViewModel(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;

        LiveData<List<Meeting>> meetingsLiveData = meetingRepository.getMeetingsLiveData();

        mediatorLiveData.addSource(meetingsLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                combine(meetings, isSortingAlphabeticallyMutableLiveData.getValue());
            }
        });

        mediatorLiveData.addSource(isSortingAlphabeticallyMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSortingAlphabetically) {
                combine(meetingsLiveData.getValue(), isSortingAlphabetically);
            }
        });
    }

    public LiveData<List<MeetingViewStateItem>> getMeetingViewStateItemsLiveData() {
        return mediatorLiveData;
    }

    private void combine(@Nullable List<Meeting> meetings, @Nullable Boolean isSortingAlphabetically) {
        if (meetings == null) {
            return;
        }

        List<MeetingViewStateItem> meetingViewStateItems = new ArrayList<>();

        for (Meeting meeting : meetings) {
            meetingViewStateItems.add(
                new MeetingViewStateItem(
                    meeting.getId(),
                    meeting.getDay(),
                    meeting.getTime(),
                    meeting.getMeetingRoom(),
                    meeting.getMeetingSubject(),
                    meeting.getParticipants()
                )
            );
        }

        //todo David pour test sur subject; à faire pour date et salle
        if (isSortingAlphabetically != null) {
            if ( isSortingAlphabetically) {
                Collections.sort(meetingViewStateItems, new Comparator<MeetingViewStateItem>() {
                    @Override
                    public int compare(MeetingViewStateItem o1, MeetingViewStateItem o2) {
                        return o2.getMeetingSubject().compareTo(o1.getMeetingSubject());
                    }
                });
            } else {
                Collections.sort(meetingViewStateItems, new Comparator<MeetingViewStateItem>() {
                    @Override
                    public int compare(MeetingViewStateItem o1, MeetingViewStateItem o2) {
                        return o1.getMeetingSubject().compareTo(o2.getMeetingSubject());
                    }
                });
            }
        }

        mediatorLiveData.setValue(meetingViewStateItems);
    }

    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }

    public void onDateSortButtonClicked() {
        Boolean previous = isSortingAlphabeticallyMutableLiveData.getValue();

        if (previous == null) {
            previous = false;
        }

        isSortingAlphabeticallyMutableLiveData.setValue(!previous);
    }
}

