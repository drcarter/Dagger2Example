package com.drarter.dagger2.example.internal.di.module;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(@NonNull Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Activity provideActivity() {
        return this.activity;
    }
}
