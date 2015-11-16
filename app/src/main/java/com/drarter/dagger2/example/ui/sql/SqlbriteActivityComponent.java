package com.drarter.dagger2.example.ui.sql;

import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.PerActivity;
import com.drarter.dagger2.example.internal.di.component.ActivityComponent;
import com.drarter.dagger2.example.internal.di.component.ApplicationComponent;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                SqlbriteActivityModule.class
        }
)
public interface SqlbriteActivityComponent extends ActivityComponent {

        void inject(@NonNull SqlbriteActivity sqlbriteActivity);

        void inject(@NonNull ListsFragment fragment);

        void inject(@NonNull NewItemFragment newItemFragment);

        void inject(@NonNull ItemsFragment itemsFragment);
}
