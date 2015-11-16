package com.drarter.dagger2.example.ui.main;

import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.PerActivity;
import com.drarter.dagger2.example.internal.di.component.ActivityComponent;
import com.drarter.dagger2.example.internal.di.component.ApplicationComponent;
import com.drarter.dagger2.example.internal.di.module.BikeModule;
import com.drarter.dagger2.example.ui.main.MainActivityModule;
import com.drarter.dagger2.example.ui.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                MainActivityModule.class,
                BikeModule.class
        }
)
public interface MainActivityComponent extends ActivityComponent {
    void inject(@NonNull MainActivity mainActivity);
}
