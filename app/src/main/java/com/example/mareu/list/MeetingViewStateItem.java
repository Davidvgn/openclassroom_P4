package com.example.mareu.list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MeetingViewStateItem {

    private final long id;
    private final String hour;
    private final String min;
    private final String meetingRoom;
    private final String meetingSubject;

    public MeetingViewStateItem(long id, String hour,String min, String meetingRoom, String meetingSubject) {
        this.id = id;
        this.hour = hour;
        this.min = min;
        this.meetingRoom = meetingRoom;
        this.meetingSubject = meetingSubject;
    }

    public long getId() {
        return id;
    }

    public String getHour() {
        return hour;
    }
    public String getMin() {
        return min;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MeetingViewStateItem that = (MeetingViewStateItem) o;
            return id == that.id && hour.equals(that.hour) && min.equals(that.min) &&  meetingRoom.equals(that
            .meetingRoom) && meetingSubject.equals(that.meetingSubject);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, hour, min, meetingRoom, meetingSubject);
        }

        @NonNull
        @Override
        public String toString() {
            return "NeighboursViewStateItem{" +
                    "id=" + id +
                    ", hour='" + hour + '\'' +
                    ", min='" + min + '\'' +
                    ", meetingRoom='" + meetingRoom + '\'' +
                    ", meetingSubject='" + meetingSubject + '\'' +
                    '}';
        }
    }

