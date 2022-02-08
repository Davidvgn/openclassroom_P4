package com.example.mareu.data;

public class Meeting {

    private final long id;
    private final String time;
    private final String meetingRoom;
    private final String meetingSubject;
    private final String participantList;

    public Meeting(long id,
                   String time,
                   String meetingRoom,
                   String meetingSubject,
                   String participantList) {
        this.id = id;
        this.time = time;
        this.meetingRoom = meetingRoom;
        this.meetingSubject = meetingSubject;
        this.participantList = participantList;
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

    public String getParticipantList() {
        return participantList;
    }
}
