package com.drarter.dagger2.example.ui.main;

import android.support.annotation.NonNull;

import com.drarter.dagger2.example.ui.main.MainActivity;

import java.lang.ref.WeakReference;

import dagger.Module;

@Module
public class MainActivityModule {

    private WeakReference<MainActivity> activityWeakReference;

    public MainActivityModule(@NonNull MainActivity mainActivity) {
        this.activityWeakReference = new WeakReference<MainActivity>(mainActivity);
    }
}
