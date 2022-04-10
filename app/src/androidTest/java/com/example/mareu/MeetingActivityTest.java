package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.ui.MainActivity;
import com.example.mareu.ui.list.MeetingFragment;
import com.example.mareu.utils.MeetingTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class MeetingActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> mainActivity = activity );
    }

    @After
    public void tearDown(){
        mainActivity = null;
    }

    @Test
    public void create_multiple_meetings(){
        createMeeting(LocalDate.of(2022, 04, 24) , LocalTime.of(14, 00), "Java", "Test", "email@email.com");
        createMeeting(LocalDate.of(2022, 04, 24) , LocalTime.of(14, 00), "Swift", "Test", "email@email.com");
        createMeeting(LocalDate.of(2022, 04, 24) , LocalTime.of(14, 00), "Kotlin", "Test", "email@email.com");
        createMeeting(LocalDate.of(2022, 04, 24) , LocalTime.of(14, 00), "Itunes", "Test", "email@email.com");
    }

    private void createMeeting(
        @NonNull final LocalDate date,
        @NonNull final LocalTime time,
        @NonNull final String room,
        @NonNull final String meetingSubject,
        @NonNull final String participants
    ) {
        MeetingTestUtils.createMeeting(date, time, room, meetingSubject, participants);
        onView(withId(R.id.add_meeting_button)).perform(click());

    }



}
