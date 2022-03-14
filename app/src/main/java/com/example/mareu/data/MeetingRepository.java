package com.example.mareu.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.mareu.config.BuildConfigResolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {


    private final MutableLiveData<List<Meeting>> meetingLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public MeetingRepository(BuildConfigResolver buildConfigResolver) {
        if (buildConfigResolver.isDebug()) {
            generateRandomMeeting();
        }
    }

    public void addMeeting(
            String day,
            String time,
            String meetingRoom,
            String meetingSubject,
            String participants
    ) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) return;

        meetings.add(
                new Meeting(
                        maxId++,
                        day,
                        time,
                        meetingRoom,
                        meetingSubject,
                        participants
                )
        );

        meetingLiveData.setValue(meetings);
    }

    public void deleteMeeting(long meetingId) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) return;

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        meetingLiveData.setValue(meetings);
    }


    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingLiveData;
    }


    private void generateRandomMeeting() {
        addMeeting(
                "07/03/22", "14:00", "Android", "Sujet 1", "email@email.com"
        );
        addMeeting(
                "17/03/22", "14:45", "Kotlin", "Sujet 2", "email@email.com"
        );
    }
}

