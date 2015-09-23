package com.drarter.dagger2.example.internal.di.component;

import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.PerActivity;
import com.drarter.dagger2.example.internal.di.module.BikeModule;
import com.drarter.dagger2.example.internal.di.module.MainActivityModule;
import com.drarter.dagger2.example.ui.MainActivity;

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
