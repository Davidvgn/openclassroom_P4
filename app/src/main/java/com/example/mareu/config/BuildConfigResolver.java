package com.example.mareu.config;

import com.example.mareu.BuildConfig;

@SuppressWarnings("ALL")
public class BuildConfigResolver {

    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}

