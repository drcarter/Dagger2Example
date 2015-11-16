package com.drarter.dagger2.example.internal.di.component;

import com.drarter.dagger2.example.base.model.Vehicle;
import com.drarter.dagger2.example.internal.di.module.ApplicationModule;
import com.drarter.dagger2.example.internal.di.module.DataBaseModule;
import com.drarter.dagger2.example.internal.di.module.VehicleModule;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                VehicleModule.class,
                DataBaseModule.class

        }
)
public interface ApplicationComponent {
    Vehicle getVehicle();
    BriteDatabase getBriteDataBase();
}
