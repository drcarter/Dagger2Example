package com.drarter.dagger2.example.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.drarter.dagger2.example.ExampleApplication;
import com.drarter.dagger2.example.internal.di.component.ActivityComponent;
import com.drarter.dagger2.example.internal.di.component.ApplicationComponent;
import com.drarter.dagger2.example.internal.di.component.HasComponent;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements HasComponent<ActivityComponent> {

    protected abstract int getContentViewResource();

    protected abstract ActivityComponent getInitializeCompoent();

    protected abstract void onInject(@Nullable ActivityComponent component);

    protected ActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResource());

        ButterKnife.bind(this);

        this.component = getInitializeCompoent();
        if (this.component != null) {
            onInject(this.component);
        }
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((ExampleApplication) getApplication()).getApplicationComponent();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public ActivityComponent getComponent() {
        return this.component;
    }
}
