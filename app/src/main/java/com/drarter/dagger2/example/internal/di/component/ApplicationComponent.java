package com.drarter.dagger2.example.internal.di.component;

import com.drarter.dagger2.example.base.model.Vehicle;
import com.drarter.dagger2.example.internal.di.module.ApplicationModule;
import com.drarter.dagger2.example.internal.di.module.VehicleModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                VehicleModule.class,
                ApplicationModule.class
        }
)
public interface ApplicationComponent {
    Vehicle getVehicle();
}
