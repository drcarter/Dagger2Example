package com.drarter.dagger2.example.ui.main;

import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.drarter.dagger2.example.R;
import com.drarter.dagger2.example.base.activity.BaseActivity;
import com.drarter.dagger2.example.base.model.Bike;
import com.drarter.dagger2.example.base.model.Vehicle;
import com.drarter.dagger2.example.internal.di.component.ActivityComponent;
import com.drarter.dagger2.example.internal.di.module.BikeModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.text_vehicle_result)
    TextView textVehicleResult;

    @Bind(R.id.text_bike_result)
    TextView textBikeResult;

    @Inject
    Vehicle vehicle;

    @Inject
    Bike bike;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_main;
    }

    @Override
    protected ActivityComponent getInitializeCompoent() {
        return DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .bikeModule(new BikeModule())
                .build();
    }

    @Override
    protected void onInject(@Nullable ActivityComponent component) {
        if (component != null) {
            ((MainActivityComponent) component).inject(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_vehicle_increase_speed)
    public void onSpeedIncreaseClick() {
        this.vehicle.increaseSpeed(10);
        this.textVehicleResult.setText("vehicle speed : " + vehicle.getSpeed());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_vehicle_brake)
    public void onVehicleBrake() {
        this.vehicle.stop();
        this.textVehicleResult.setText("vehicle speed : " + vehicle.getSpeed());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_bike_increase_speed)
    public void onBikeIncreaseSpeed() {
        this.bike.increaseSpped();
        this.textBikeResult.setText("bike speed : " + bike.getSpeed());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_bike_decrease_speed)
    public void onBikeDecreaseSpeed() {
        this.bike.decreaseSpeed();
        this.textBikeResult.setText("bike speed : " + bike.getSpeed());
    }
}
