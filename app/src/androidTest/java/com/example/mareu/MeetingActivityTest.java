package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.ui.MainActivity;
import com.example.mareu.utils.DeleteViewAction;
import com.example.mareu.utils.MeetingTestUtils;
import com.example.mareu.utils.RecyclerViewMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalTime;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.example.mareu.utils.Waiter.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MeetingActivityTest {

    public MainActivity mainActivity;
    private static int meetingsFromRepo = 2;
    private static int noMeetings = 0;


    @Before
    public void setUp(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> mainActivity = activity );
    }

    @After
    public void tearDown(){
        mainActivity = null;
    }


    /** Checks if the 2 meetings created in meetingRepository are uploaded
     * and delete them
     */
    @Test
    public void fake_meetings_are_uploaded_and_delete(){
        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .check(withItemCount(meetingsFromRepo));

        delete_meetings(1);
        delete_meetings(0);

        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .check(withItemCount(noMeetings));
    }

    /** Checks if pressing on back button doesn't save the meeting created
     * then creates 4 meetings
     */

    @Test
    public void create_multiple_meetings(){

        createMeeting(LocalDate.of(2022, 04, 24),
            LocalTime.of(14, 0),
            "Java",
            "Subject 1",
            "email1@email.com");
        pressBack();
        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .check(withItemCount(noMeetings));



        createMeeting(
            LocalDate.of(2022, 04, 24),
            LocalTime.of(14, 0),
            "Java",
            "Subject 1",
            "email1@email.com");
        onView(withId(R.id.add_meeting_button)).perform(click());

        createMeeting(LocalDate.of(2022, 04, 26),
            LocalTime.of(13, 45),
            "Swift",
            "Subject 2",
            "email1@email.com");
        onView(withId(R.id.add_meeting_button)).perform(click());

        createMeeting(LocalDate.of(2022, 04, 22),
            LocalTime.of(11, 15),
            "Kotlin",
            "Subject 3",
            "email1@email.com");
        onView(withId(R.id.add_meeting_button)).perform(click());


        createMeeting(LocalDate.of(2022, 04, 20),
            LocalTime.of(16, 20),
            "Itunes",
            "Subject 4",
            "email1@email.com");
        onView(withId(R.id.add_meeting_button)).perform(click());

    }

    @Test
    public void sorting_by_date(){

        onView(withId(R.id.menu_date)).perform(click());
        onView(withText(R.string.sort_by_date))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));
        checkSortByDate(0, "Subject 4");
        checkSortByDate(1, "Subject 3");
        checkSortByDate(2, "Subject 1");
        checkSortByDate(3, "Subject 2");

        onView(withId(R.id.menu_date)).perform(click());
        onView(withText(R.string.sort_by_date))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));
        checkSortByDate(3, "Subject 4");
        checkSortByDate(2, "Subject 3");
        checkSortByDate(1, "Subject 1");
        checkSortByDate(0, "Subject 2");

    }

    @Test
    public void sorting_by_room(){

        onView(withId(R.id.menu_room)).perform(click());
        onView(withText(R.string.sort_by_room))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));

        onView(isRoot())
            .perform(waitFor(1000));

        checkSortByRoom(3, "Itunes");
        checkSortByRoom(2, "Java");
        checkSortByRoom(1, "Kotlin");
        checkSortByRoom(0, "Swift");

        onView(withId(R.id.menu_room)).perform(click());
        onView(withText(R.string.sort_by_room))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));

        checkSortByRoom(0, "Itunes");
        checkSortByRoom(1, "Java");
        checkSortByRoom(2, "Kotlin");
        checkSortByRoom(3, "Swift");
    }


    private void createMeeting(
        @NonNull final LocalDate date,
        @NonNull final LocalTime time,
        @NonNull final String room,
        @NonNull final String meetingSubject,
        @NonNull final String participants
    ) {
        MeetingTestUtils.createMeeting(date, time, room, meetingSubject, participants);
    }

    private void checkSortByDate(int position, String subject) {
        onView(new RecyclerViewMatcher(R.id.meeting_rv)
            .atPositionOnView(position, R.id.meeting_item_tv_subject))
            .check(matches(withText(subject)));
    }

    private void checkSortByRoom(int position, String room) {
        onView(new RecyclerViewMatcher(R.id.meeting_rv)
            .atPositionOnView(position, R.id.meeting_item_tv_meetingRoom))
            .check(matches(withText(room)));
    }

    private void delete_meetings(int position) {
        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition(position, new DeleteViewAction()));
        onView(isRoot())
            .perform(waitFor(1000));
    }
}
