package com.example.mareu.meeting;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import androidx.lifecycle.MutableLiveData;

import com.example.mareu.LiveDataTestUtils;
import com.example.mareu.data.Meeting;
import com.example.mareu.data.MeetingRepository;
import com.example.mareu.ui.list.MeetingViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MeetingViewModelTest {

    //todo Nino trouvé dans tes test : Pourquoi dans certains cas tout en majuscule avec underscore ?
//    private static final String DEFAULT_NEIGHBOUR_NAME = "DEFAULT_NEIGHBOUR_NAME";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private MutableLiveData<List<Meeting>> meetingsMutableLiveData;
    private MeetingViewModel viewModel;

    private MutableLiveData<Boolean> isSortingByRoomMutableLiveData;
    private MutableLiveData<Boolean> isSortingByDateMutableLiveData;



    @Before
    public void setUp() {
        meetingsMutableLiveData = new MutableLiveData<>();

        given(meetingRepository.getMeetingsLiveData()).willReturn(meetingsMutableLiveData);

        List<Meeting> meetings = getMeetings();
        meetingsMutableLiveData.setValue(meetings);

        isSortingByRoomMutableLiveData = new MutableLiveData<>();
        isSortingByDateMutableLiveData = new MutableLiveData<>();

        isSortingByDateMutableLiveData.setValue(false);
        isSortingByRoomMutableLiveData.setValue(false);

        viewModel = new MeetingViewModel(meetingRepository);

        verify(meetingRepository).getMeetingsLiveData();
    }


    @Test
    public void noMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        meetingsMutableLiveData.setValue(meetings);

        LiveDataTestUtils.observeForTesting(viewModel.getMeetingViewStateItemsLiveData(), value -> {
            assertEquals(0, value.size());
        });
    }

    @Test
    public void meetingsAddedAreEqualsInMeetingVm() {
        List<Meeting> meetings = getMeetings();
        meetingsMutableLiveData.setValue(meetings);
        Meeting meetingInFirstSpot = meetings.get(0);
        Meeting meetingAdded = new Meeting(
            0,
            LocalDateTime.of(2022, 3, 15, 14, 0),
            "Java",
            "SUJET 1",
            "particpant1@email.com, participant2@email.com");

        LiveDataTestUtils.observeForTesting(viewModel.getMeetingViewStateItemsLiveData(), value -> {
            assertEquals(4, value.size());
        });

        assertEquals(meetingInFirstSpot, meetingAdded); //todo Nino est-ce nécessaire ?

    }

    @Test
    public void setIsSortingByRoomMutableLiveData(){
        List<Meeting> meetings = getMeetings();
        isSortingByRoomMutableLiveData.setValue(true);

    }

    @Test
    public void setIsSortingByDateMutableLiveData(){

    }


    @Test
    public void onDeleteMeetingTest() {
        viewModel.onDeleteMeetingClicked(89);

        Mockito.verify(meetingRepository).deleteMeeting(89);
//        Mockito.verifyNoMoreInteractions(meetingRepository);
        //todo Nino : utilité de verifyNoInteractions/ verifyNoMoreInteractions ?

    }


    private List<Meeting> getMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        meetings.add(
            new Meeting(
                0, LocalDateTime.of(2022, 3, 15, 14, 0),
                "Java", "SUJET 1",
                "particpant1@email.com, participant2@email.com"));

        meetings.add(
            new Meeting(
                1, LocalDateTime.of(2022, 3, 22, 14, 0),
                "Dart", "SUJET 2",
                "particpant1@email.com, participant2@email.com"));

        meetings.add(
            new Meeting(
                2, LocalDateTime.of(2022, 3, 23, 14, 0),
                "Swift", "SUJET 3",
                "particpant1@email.com, participant2@email.com"));

        meetings.add(
            new Meeting(
                3, LocalDateTime.of(2022, 3, 28, 14, 0),
                "Kotlin", "SUJET 4",
                "particpant1@email.com, participant2@email.com"));

        return meetings;
    }
}
