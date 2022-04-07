package com.example.mareu.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.MainApplication;
import com.example.mareu.config.BuildConfigResolver;
import com.example.mareu.data.MeetingRepository;
import com.example.mareu.ui.list.MeetingViewModel;
import com.example.mareu.ui.add.AddMeetingViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            new MeetingRepository
                                    (
                                    new BuildConfigResolver()
                            )
                    );
                }
            }
        }

        return factory;
    }

    @NonNull
    private final MeetingRepository meetingRepository;

    private ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
            return (T) new MeetingViewModel(
                    meetingRepository
            );
        } else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
            return (T) new AddMeetingViewModel(
                MainApplication.getInstance(),
                    meetingRepository
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}