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
            String time,
            String meetingRoom,
            String meetingSubject
//            List participantList
    ) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) return;

        meetings.add(
                new Meeting(
                        maxId++,
                        time,
                        meetingRoom,
                        meetingSubject
//                        participantList
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

    public LiveData<Meeting> getMeetingLiveData(long meetingId) {
        return Transformations.map(meetingLiveData, meetings -> {
            for (Meeting meeting : meetings) {
                if (meeting.getId() == meetingId) {
                    return meeting;
                }
            }

            return null;
        });
    }

    private void generateRandomMeeting() {
        addMeeting(
                "14h",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "TEST"
        );
    }
}

