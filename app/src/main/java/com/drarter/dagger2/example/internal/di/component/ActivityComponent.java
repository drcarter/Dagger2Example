package com.drarter.dagger2.example.internal.di.component;

import com.drarter.dagger2.example.internal.di.PerActivity;
import com.drarter.dagger2.example.internal.di.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(
        modules = ActivityModule.class
)
public interface ActivityComponent {
}
