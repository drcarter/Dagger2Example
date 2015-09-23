package com.drarter.dagger2.example.base.model;

public class Motor {

    private int rpm;

    public Motor() {
        this.rpm = 0;
    }

    public int getRpm() {
        return rpm;
    }

    public void accelerate(int value) {
        rpm = rpm + value;
    }

    public void setSpeed(int value) {
        rpm = value;
    }

    public void brake() {
        rpm = 0;
    }
}
