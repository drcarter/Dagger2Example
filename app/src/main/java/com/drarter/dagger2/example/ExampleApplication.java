package com.drarter.dagger2.example;

import android.app.Application;

import com.drarter.dagger2.example.internal.di.component.ApplicationComponent;
import com.drarter.dagger2.example.internal.di.component.DaggerApplicationComponent;
import com.drarter.dagger2.example.internal.di.module.ApplicationModule;
import com.drarter.dagger2.example.internal.di.module.DataBaseModule;

public class ExampleApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataBaseModule(new DataBaseModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
