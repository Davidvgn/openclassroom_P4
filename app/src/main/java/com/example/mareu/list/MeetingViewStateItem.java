package com.example.mareu.list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MeetingViewStateItem {

    private final long id;
    private final String time;
    private final String meetingRoom;
    private final String meetingSubject;

    public MeetingViewStateItem(long id, String time, String meetingRoom, String meetingSubject) {
        this.id = id;
        this.time = time;
        this.meetingRoom = meetingRoom;
        this.meetingSubject = meetingSubject;
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

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MeetingViewStateItem that = (MeetingViewStateItem) o;
            return id == that.id && time.equals(that.time) &&  meetingRoom.equals(that
            .meetingRoom) && meetingSubject.equals(that.meetingSubject);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, time, meetingRoom, meetingSubject);
        }

        @NonNull
        @Override
        public String toString() {
            return "NeighboursViewStateItem{" +
                    "id=" + id +
                    ", time='" + time + '\'' +
                    ", meetingRoom='" + meetingRoom + '\'' +
                    ", meetingSubject='" + meetingSubject + '\'' +
                    '}';
        }
    }

