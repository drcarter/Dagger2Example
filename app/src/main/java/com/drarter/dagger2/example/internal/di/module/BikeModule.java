package com.drarter.dagger2.example.internal.di.module;

import com.drarter.dagger2.example.base.model.Bike;
import com.drarter.dagger2.example.base.model.Motor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = MotorModule.class
)
public class BikeModule {

    @Provides
    Bike provideBike(Motor motor) {
        return new Bike(motor);
    }
}
