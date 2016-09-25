package com.drarter.dagger2.example.internal.di.module;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.PerActivity;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    protected WeakReference<Activity> activity;

    public ActivityModule(@NonNull Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @PerActivity
    @Provides
    Activity provideActivity() {
        return this.activity.get();
    }
}
