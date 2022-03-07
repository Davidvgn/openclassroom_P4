package com.example.mareu.data;

import androidx.annotation.NonNull;
import java.util.Objects;

public class Meeting {

    private final long id;
    @NonNull
    private final String day;
    @NonNull
    private final String time;
    @NonNull
    private final String meetingRoom;
    @NonNull
    private final String meetingSubject;
    @NonNull
    private final String participants;

    public Meeting(long id,
                   @NonNull String day,
                   @NonNull String time,
                   @NonNull String meetingRoom,
                   @NonNull String meetingSubject,
                   @NonNull String participants
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

    @NonNull
    public String getDay() {
        return day;
    }
    @NonNull
    public String getTime() {
        return time;
    }
    @NonNull
    public String getMeetingRoom() {
        return meetingRoom;
    }
    @NonNull
    public String getMeetingSubject() {
        return meetingSubject;
    }
    @NonNull
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

