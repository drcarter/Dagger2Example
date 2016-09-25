package com.drarter.dagger2.example.ui.sql;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.internal.di.module.ActivityModule;

import dagger.Module;

@Module
public class SqlbriteActivityModule extends ActivityModule{
    public SqlbriteActivityModule(@NonNull Activity activity) {
        super(activity);
    }
}
