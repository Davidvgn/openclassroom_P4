package com.example.mareu.meeting;


import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.LiveDataTestUtils;
import com.example.mareu.R;
import com.example.mareu.data.MeetingRepository;
import com.example.mareu.ui.add.AddMeetingViewModel;
import com.example.mareu.utils.SingleLiveEvent;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class AddMeetingViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Application application;
    
    @Mock
    private MeetingRepository meetingRepository;

    private AddMeetingViewModel viewModel;
    private SingleLiveEvent<String> showToastSingleLiveEvent;

    private MutableLiveData<String> roomMutableLiveData;


    @Before
    public void setUp() {

        given(application.getString(R.string.incorrect_email_format)).willReturn("incorrect_email_format");
        given(application.getString(R.string.incorrect_date_format)).willReturn("incorrect_date_format");
        given(application.getString(R.string.incorrect_hour_format)).willReturn("incorrect_hour_format");
        given(application.getString(R.string.hour_characters)).willReturn("hour_characters");
//        given(application.getString(R.string.room_availability)).willReturn("room_availability");

        given(meetingRepository.addMeeting(any(), any(), any(), any())).willReturn(true);

        viewModel = new AddMeetingViewModel(application, meetingRepository);
        roomMutableLiveData = new MutableLiveData<>();
        showToastSingleLiveEvent = new SingleLiveEvent<>();
        roomMutableLiveData.setValue("Swift");
    }

    @Test
    public void nominalCase() {
        String date = "14/04/2022";
        String time = "15:00";
        String room = "Java";
        String meetingSubject = "Sujet";
        String participants = "participant@email.com";

        //When
        viewModel.onRoomSelected(room);
        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
        LiveDataTestUtils.observeForTesting(viewModel.getCloseActivitySingleLiveEvent(), value -> {
            //Then
            verify(meetingRepository).addMeeting(
                eq(LocalDateTime.of(2022, 04, 14, 15, 00)),
                eq(room),
                eq(meetingSubject),
                eq("[participant@email.com]")
            );
        });
    }

    @Test
    public void calling_toast_if_dateFormat_is_not_right(){

        String date = "14/04";
        String time = "15:00";
        String meetingSubject = "Sujet";
        String participants = "participant@email.com";

//        //When
        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
        LiveDataTestUtils.observeForTesting(viewModel.getShowToastSingleLiveEvent(), value -> {
            //Then
            assertEquals( "incorrect_date_format", value );
        });
    }

    @Test
    public void calling_toast_if_emailFormat_is_not_right() {

        String date = "14/04/2022";
        String time = "15:00";
        String meetingSubject = "Sujet";
        String participants = "email@email";

        //When
        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
        LiveDataTestUtils.observeForTesting(viewModel.getShowToastSingleLiveEvent(), value -> {
            //Then
            assertEquals("incorrect_email_format", value);
        });
    }

    @Test
    public void calling_toast_if_timeFormat_is_not_right() {

        String date = "14/04/2022";
        String time = "1500";
        String meetingSubject = "Sujet";
        String participants = "participant@email.com";

        //When
        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
        LiveDataTestUtils.observeForTesting(viewModel.getShowToastSingleLiveEvent(), value -> {
            //Then
            assertEquals("incorrect_hour_format", value);
        });
    }

    @Test
    public void calling_toast_if_timeFormat_has_wrong_characters() {

        String date = "14/04/2022";
        String time = "douze:vingt";
        String meetingSubject = "Sujet";
        String participants = "participant@email.com";

        //When
        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
        LiveDataTestUtils.observeForTesting(viewModel.getShowToastSingleLiveEvent(), value -> {
            //Then
            assertEquals("hour_characters",value);
        });
    }


    @Test
    public void onDateChangedTest() {
        LocalDate expected = LocalDate.of(2022, 4, 14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String expectedFormat = expected.format(formatter);


        //When
        viewModel.onDateChanged(14,3,2022);
        LiveDataTestUtils.observeForTesting(viewModel.getAddMeetingViewStateLiveData(), value -> {
            //Then
            assertEquals(expectedFormat, value.getDate());
        });
    }

    @Test
    public void onTimeChangedTest() {
        String time = LocalTime.of(14, 45).toString();

        viewModel.onTimeChanged(14, 45);
        LiveDataTestUtils.observeForTesting(viewModel.getAddMeetingViewStateLiveData(), value -> {
            assertEquals(time, value.getTime());
        });
    }

    @Test
    public void verify_if_room_is_free() {
//        meetingRepository.addMeeting(
//                LocalDateTime.of(2022, 4, 14, 16, 0),
//                "Swift",
//                "Sujet 1",
//                "email@email.com"
//            );
//
//        String date = "14/04/2022";
//        String time = "15:25";
//        String meetingSubject = "Sujet";
//        String participants = "participant@email.com";
//
//       viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
//        LiveDataTestUtils.observeForTesting(viewModel.getShowToastSingleLiveEvent(), value -> {
//            assertEquals(value, "La salle est déjà occupée à cet horaire");
//        });
    }
    //todo david test multiples mails
//Todo david pense à faire un toast pour l'utilisateur pour dire de séparer les mails par une virgule

}
