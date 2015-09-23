package com.drarter.dagger2.example.internal.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.ExampleApplication;
import com.drarter.dagger2.example.internal.di.PerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@PerApp
@Module
public class ApplicationModule {

    private ExampleApplication billyApplication;

    public ApplicationModule(@NonNull ExampleApplication billyApplication) {

        this.billyApplication = billyApplication;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return this.billyApplication.getApplicationContext();
    }
}
