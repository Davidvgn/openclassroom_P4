package com.example.mareu.meeting;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.LiveDataTestUtils;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class AddMeetingViewModelTest {
//todo Nino comment récupérer la room ? Il n'y a pas de getter dans le viewState
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private AddMeetingViewModel viewModel;
    private SingleLiveEvent<String> showToastSingleLiveEvent;

    private MutableLiveData<String> roomMutableLiveData;


    @Before
    public void setUp() {
        viewModel = new AddMeetingViewModel(meetingRepository);
        roomMutableLiveData = new MutableLiveData<>();
        showToastSingleLiveEvent = new SingleLiveEvent<>();
        roomMutableLiveData.setValue("Swift");
    }

    //todo Nino bloqué -> ça n'appelle pas le liveData
    @Test
    public void firstTest() {
//        String date = "14/04/2022";
//        String time = "15:00";
//        String Room = "Java";
//        String meetingSubject = "Sujet";
//        String participants = "participant@email.com";
//
//        //When
//        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
//        LiveDataTestUtils.observeForTesting(viewModel.getCloseActivitySingleLiveEvent(), value -> {
//            //Then
//            verify(meetingRepository).addMeeting(
//                eq(date),
//                eq(),
//                eq(meetingSubject),
//                eq(participants)
//            );
//        });
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
            assertEquals(value, "Format de date non-valide (format : DD/MM/YYYY)" );
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
            assertEquals(value, "Email non-valide");
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
            assertEquals(value, "Format heure non-valide (format : HH:MM)");
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
            assertEquals(value, "Heure : caractère(s) non-valide(s)");
        });
    }

    @Test
    public void test() {

        String date = "14/04/2022";
        String time = "douze:vingt";
        String meetingSubject = "Sujet";
        String participants = "participant@email.com";

        //When
        viewModel.onAddButtonClicked(date, time, meetingSubject, participants);
        LiveDataTestUtils.observeForTesting(viewModel.getShowToastSingleLiveEvent(), value -> {
            //Then
            assertEquals(value, "Heure : caractère(s) non-valide(s)");
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
            assertEquals(value.getDate(), expectedFormat );
        });
    }

    @Test
    public void onTimeChangedTest() {
        String time = LocalTime.of(14, 45).toString();

        viewModel.onTimeChanged(14, 45);
        LiveDataTestUtils.observeForTesting(viewModel.getAddMeetingViewStateLiveData(), value -> {
            assertEquals(value.getTime(), time );
        });
    }

    //todo Nino bloqué -> ça n'appelle pas le liveData
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
