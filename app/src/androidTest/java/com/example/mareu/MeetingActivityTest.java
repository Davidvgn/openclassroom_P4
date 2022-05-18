package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.example.mareu.utils.Waiter.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.ui.MainActivity;
import com.example.mareu.utils.DeleteViewAction;
import com.example.mareu.utils.MeetingTestUtils;
import com.example.mareu.utils.RecyclerViewMatcher;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(AndroidJUnit4.class)
public class MeetingActivityTest {

    public static final LocalDate BASE_TEST_DAY = LocalDate.of(2022, 4, 24);
    public MainActivity mainActivity;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> mainActivity = activity);
    }

    @After
    public void tearDown() {
        mainActivity = null;
    }


    /**
     * Checks if the 2 meetings created in meetingRepository are uploaded
     * and delete them
     */
    @Test
    public void fake_meetings_are_uploaded_and_delete() {
        int meetingsFromRepo = 2;

        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .check(withItemCount(meetingsFromRepo));

        delete_meetings(1);
        delete_meetings(0);

        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .check(withItemCount(0));
    }

    /**
     * Checks if pressing on back button doesn't save the meeting created
     * then creates 2 meetings
     */

    @Test
    public void creating_multiple_meetings() {

        MeetingTestUtils.createMeeting(
            BASE_TEST_DAY,
            LocalTime.of(14, 0),
            "Java",
            "Subject TEST",
            "email1@email.com"
        );
        pressBack();
        onView(allOf(withId(R.id.meeting_rv),
            isDisplayed()))
            .check(withItemCount(2));

        create_2_meetings();

    }

    @Test
    public void sorting_and_filtering_by_date() {

        create_2_meetings();

        onView(withId(R.id.menu_date)).perform(click());
        onView(withText(R.string.sort_by_date))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));
        checkSortByDate(0, "Subject 1");
        checkSortByDate(1, "Subject 2");
        checkSortByDate(2, "Subject 3");
        checkSortByDate(3, "Subject 4");

        onView(withId(R.id.menu_date)).perform(click());
        onView(withText(R.string.sort_by_date))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));
        checkSortByDate(3, "Subject 1");
        checkSortByDate(2, "Subject 2");
        checkSortByDate(1, "Subject 3");
        checkSortByDate(0, "Subject 4");

        //Checks if date filter works
        onView(withId(R.id.main_filter_by_date_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
            .perform(PickerActions.setDate(2022, 4, 26));
        onView(withText("OK")).perform(click());
        onView(new RecyclerViewMatcher(R.id.meeting_rv)
            .atPositionOnView(0, R.id.meeting_item_tv_subject))
            .check(matches(withText("Subject 4")));

        //Checks if remove filter works
        onView(withId(R.id.main_button_filter)).perform(click());
        checkSortByDate(3, "Subject 1");
        checkSortByDate(2, "Subject 2");
        checkSortByDate(1, "Subject 3");
        checkSortByDate(0, "Subject 4");
    }

    @Test
    public void sorting_and_filtering_by_room() {

        create_2_meetings();

        onView(withId(R.id.menu_room)).perform(click());
        onView(isRoot())
            .perform(waitFor(1000));
        onView(withText(R.string.sort_by_room))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));

        onView(isRoot())
            .perform(waitFor(1000));

        checkSortByRoom(3, "Android");
        checkSortByRoom(2, "Java");
        checkSortByRoom(1, "Kotlin");
        checkSortByRoom(0, "Swift");

        onView(withId(R.id.menu_room)).perform(click());
        onView(withText(R.string.sort_by_room))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));

        checkSortByRoom(0, "Android");
        checkSortByRoom(1, "Java");
        checkSortByRoom(2, "Kotlin");
        checkSortByRoom(3, "Swift");

        //Checks if room filter works
        onView(withId(R.id.main_act_room))
            .perform(
                replaceText("Android"));
        onView(withText("Android"))
            .inRoot(RootMatchers.isPlatformPopup())
            .check(matches(isDisplayed())).perform(click());
        onView(new RecyclerViewMatcher(R.id.meeting_rv)
            .atPositionOnView(0, R.id.meeting_item_tv_subject))
            .check(matches(withText("Subject 1")));

        //Checks if remove filter works
        onView(withId(R.id.main_button_filter)).perform(click());
        checkSortByRoom(0, "Android");
        checkSortByRoom(1, "Java");
        checkSortByRoom(2, "Kotlin");
        checkSortByRoom(3, "Swift");

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

    private void create_2_meetings(){
        MeetingTestUtils.createMeeting(
            BASE_TEST_DAY,
            LocalTime.of(14, 0),
            "Java",
            "Subject 3",
            "email1@email.com"
        );
        onView(withId(R.id.add_meeting_button)).perform(click());

        MeetingTestUtils.createMeeting(
            BASE_TEST_DAY.plusDays(2),
            LocalTime.of(13, 45),
            "Swift",
            "Subject 4",
            "email1@email.com"
        );
        onView(withId(R.id.add_meeting_button)).perform(click());

    }
}
