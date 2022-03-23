//package com.example.mareu.meeting;
//
//import com.example.mareu.config.BuildConfigResolver;
//import com.example.mareu.data.MeetingRepository;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//public class MeetingRepositoryTest {
//
//    @Rule
//    public MeetingRepository mMeetingRepository;
//    private BuildConfigResolver mBuildConfigResolver;
//
//    @Before
//    public void setUp() {
//        mMeetingRepository = new MeetingRepository(mBuildConfigResolver);
//    }
//
//    //addMeeting
//    @Test
//    public void shouldAddMeeting() {
//        LocalDateTime date = LocalDateTime.now();
//        String room = "JAVA";
//        String subject = "Sujet 1";
//        String participants = "email@email.com";
//
//        mMeetingRepository.addMeeting(
//            date,
//            room,
//            subject,
//            participants
//        );
//    }
//
//    //not able to had same room at same time
//    //deleteMeeting
//    @Test
//    public void shoulDeleteMeeting() {
//    }
//}
////check email regex
////check date format
////check time format
