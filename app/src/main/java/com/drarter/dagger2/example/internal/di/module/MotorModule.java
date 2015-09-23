package com.drarter.dagger2.example.internal.di.module;

import com.drarter.dagger2.example.base.model.Motor;

import dagger.Module;
import dagger.Provides;

@Module
public class MotorModule {

    @Provides
    Motor provideMotor() {
        return new Motor();
    }

}
