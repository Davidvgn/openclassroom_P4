package com.example.mareu.utils;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;

import com.example.mareu.R;

import org.hamcrest.Matchers;

import java.time.LocalDate;
import java.time.LocalTime;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MeetingTestUtils {

    public static void createMeeting(
        @NonNull LocalDate date,
        @NonNull LocalTime time,
        @NonNull String room,
        @NonNull String meetingSubject,
        @NonNull String participants
    ) {
        onView(withId(R.id.main_fab_add)).perform((click()));
        if (date != null) {
            setMeetingDate(date);
        }
        if (time != null) {
            setMeetingTime(time);
        }
        if (room != null) {
            setMeetingRoom(room);
        }
        if (meetingSubject != null) {
            setMeetingSubject(meetingSubject);
        }
        if (participants != null) {
            setMeetingParticipants(participants);
        }
    }

    public static void setMeetingDate(@NonNull LocalDate date) {
        onView(withId(R.id.add_meeting_tiet_day)).perform(scrollTo(), click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
            .perform(PickerActions.setDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        onView(withText("OK")).perform(click());


    }

    public static void setMeetingTime(@NonNull LocalTime time) {
        onView(withId(R.id.add_meeting_tiet_time)).perform(scrollTo(), click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
            .perform(PickerActions.setTime(time.getHour(), time.getMinute()));
        onView(withText("OK")).perform(click());


    }

    public static void setMeetingRoom(@NonNull String room) {
        onView(withId(R.id.add_meeting_act_room))
            .perform(scrollTo(),
                replaceText(room));
        onView(withText(room))
            .inRoot(RootMatchers.isPlatformPopup())
            .check(matches(isDisplayed())).perform(click());

    }

    public static void setMeetingSubject(@NonNull String meetingSubject) {
        onView(withId(R.id.add_meeting_subject_tiet_subject))
            .perform(scrollTo(),
                replaceText(meetingSubject),
                closeSoftKeyboard()
            );

    }

    public static void setMeetingParticipants(@NonNull String participants) {
        onView(withId(R.id.add_meeting_tiet_participants))
            .perform(scrollTo(),
                replaceText(participants),
                closeSoftKeyboard()
            );

    }
}
