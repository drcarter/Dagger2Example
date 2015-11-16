package com.drarter.dagger2.example.internal.di.module;

import android.support.annotation.NonNull;

import com.drarter.dagger2.example.base.model.Motor;
import com.drarter.dagger2.example.base.model.Vehicle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                MotorModule.class
        }
)
public class VehicleModule {

    @Provides
    Vehicle privdeVehicle(@NonNull Motor motor) {
        return new Vehicle(motor);
    }

}
