package com.drarter.dagger2.example.ui.main;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.module.ActivityModule;

import dagger.Module;

@Module
public class MainActivityModule extends ActivityModule{

    public MainActivityModule(@NonNull Activity activity) {
        super(activity);
    }
}
