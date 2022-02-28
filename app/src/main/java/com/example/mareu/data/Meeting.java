package com.example.mareu.data;

import androidx.annotation.NonNull;
import java.util.Objects;

public class Meeting {

    private final long id;
    private final String day;
    private final String time;
    private final String meetingRoom;
    private final String meetingSubject;
    private final String participants;

    public Meeting(long id,
                   String day,
                   String time,
                   String meetingRoom,
                   String meetingSubject,
                   String participants
    ) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.meetingRoom = meetingRoom;
        this.meetingSubject = meetingSubject;
        this.participants = participants;
    }

    public long getId() {
        return id;
    }

    public String getDay() {
        return day;
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

    public String getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && day.equals(meeting.day) && time.equals(meeting.time) && meetingRoom.equals(meeting.meetingRoom) && meetingSubject.equals(meeting.meetingSubject) && participants.equals(meeting.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, meetingRoom, meetingSubject, participants);
    }

    @NonNull
    @Override
    public String toString() {
        return "Neighbour{" +
                "id=" + id +
                ", day=" + day +
                ", time=" + time +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingSubject='" + meetingSubject + '\'' +
                ", participants='" + participants + '\'' +
                '}';
    }
}

