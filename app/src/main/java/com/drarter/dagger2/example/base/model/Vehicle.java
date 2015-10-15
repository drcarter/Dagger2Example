package com.drarter.dagger2.example.base.model;

import android.support.annotation.NonNull;

public class Vehicle {

    private Motor motor;

    public Vehicle(@NonNull Motor motor) {
        this.motor = motor;
    }

    public void increaseSpeed(int value) {
        motor.accelerate(value);
    }

    public void stop() {
        motor.brake();
    }

    public int getSpeed() {
        return motor.getSpeed();
    }
}
