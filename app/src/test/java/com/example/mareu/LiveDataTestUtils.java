package com.example.mareu;

import androidx.lifecycle.LiveData;

import static org.junit.Assert.fail;

public class LiveDataTestUtils {

    public static <T> void observeForTesting(LiveData<T> liveData, OnObservedListener<T> onObservedListener) {
        boolean[] called = {false};

        liveData.observeForever(value -> {
            called[0] = true;
            onObservedListener.onObserved(value);
        });

        if (!called[0]) {
            fail("LiveData was not called");
        }
    }

    public interface OnObservedListener<T> {
        void onObserved(T value);
    }
}
