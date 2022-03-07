package com.example.mareu.list;

import androidx.annotation.NonNull;

import com.example.mareu.data.Meeting;

import java.util.Comparator;
import java.util.Objects;

public class MeetingViewStateItem {

    private final long id;
    @NonNull
    private final String day;
    @NonNull
    private final CharSequence time;
    @NonNull
    private final String meetingRoom;
    @NonNull
    private final String meetingSubject;
    @NonNull
    private final String participants;

    public MeetingViewStateItem(long id, @NonNull String day, @NonNull CharSequence time, @NonNull String meetingRoom, @NonNull String meetingSubject, @NonNull String participants) {
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
    public CharSequence getTime() {
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
        MeetingViewStateItem that = (MeetingViewStateItem) o;
        return id == that.id && day.equals(that.day) && time.equals(that.time) && meetingRoom.equals(that
                .meetingRoom) && meetingSubject.equals(that.meetingSubject) && participants.equals((that.participants));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, time, meetingRoom, meetingSubject, participants);
    }

    public static Comparator<Meeting> SortByDate = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return o1.getDay().compareTo(o2.getDay());
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "NeighboursViewStateItem{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingSubject='" + meetingSubject + '\'' +
                ", participants='" + participants + '\'' +
                '}';
    }
}

