package com.example.mareu.data;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class Meeting {

    private final long id;
//    private final String hour;
//    private final String min;
    private String time;
    private final String meetingRoom;
    private final String meetingSubject;
    private final String participants;

    public Meeting(long id,
//                   String hour,
//                   String min,
                   String time,
                   String meetingRoom,
                   String meetingSubject,
                   String participants
    ) {
        this.id = id;
//        this.hour = hour;
//        this.min = min;
        this.time = time;
        this.meetingRoom = meetingRoom;
        this.meetingSubject = meetingSubject;
        this.participants = participants;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    //    public String getHour() {
//        return hour;
//    }
//    public String getMin() {
//        return min;
//    }
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
//    return id == meeting.id && hour.equals(meeting.hour) && min.equals(meeting.min) && meetingRoom.equals(meeting.meetingRoom) && meetingSubject.equals(meeting.meetingSubject) && participants.equals(meeting.participants);
    return id == meeting.id && time.equals(meeting.time) && meetingRoom.equals(meeting.meetingRoom) && meetingSubject.equals(meeting.meetingSubject) && participants.equals(meeting.participants);
}

    @Override
    public int hashCode() {
//        return Objects.hash(id, hour, min, meetingRoom, meetingSubject, participants);
        return Objects.hash(id, time, meetingRoom, meetingSubject, participants);
    }

    @NonNull
    @Override
    public String toString() {
        return "Neighbour{" +
                "id=" + id +
                "time=" + time +
//                ", hour='" + hour + '\'' +
//                ", min='" + min + '\'' +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingSubject='" + meetingSubject + '\'' +
                ", participants='" + participants + '\'' +
                '}';
    }
}

