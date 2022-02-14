package com.example.mareu.data;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class Meeting {

    private final long id;
    private final String time;
    private final String meetingRoom;
    private final String meetingSubject;
//    private final List<String> participantList;

    public Meeting(long id,
                   String time,
                   String meetingRoom,
                   String meetingSubject
//                   List participantList
    ) {
        this.id = id;
        this.time = time;
        this.meetingRoom = meetingRoom;
        this.meetingSubject = meetingSubject;
//        this.participantList = participantList;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

//    public List<String> getParticipantList() {
//        return participantList;
//    }
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Meeting meeting = (Meeting) o;
    return id == meeting.id && time.equals(meeting.time) && Objects.equals(meetingRoom, meeting.meetingRoom) && Objects.equals(meetingSubject, meeting.meetingSubject);
}

    @Override
    public int hashCode() {
        return Objects.hash(id, time, meetingRoom, meetingSubject);
    }

    @NonNull
    @Override
    public String toString() {
        return "Neighbour{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingSubject='" + meetingSubject + '\'' +
                '}';
    }
}

