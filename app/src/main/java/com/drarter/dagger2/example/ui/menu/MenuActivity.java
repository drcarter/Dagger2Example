package com.drarter.dagger2.example.ui.menu;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.drarter.dagger2.example.R;
import com.drarter.dagger2.example.base.activity.BaseActivity;
import com.drarter.dagger2.example.internal.di.component.ActivityComponent;
import com.drarter.dagger2.example.ui.main.MainActivity;
import com.drarter.dagger2.example.ui.sql.SqlbriteActivity;

import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_menu;
    }

    @Override
    protected ActivityComponent getInitializeCompoent() {
        return null;
    }

    @Override
    protected void onInject(@Nullable ActivityComponent component) {

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_dagger_ex)
    public void onDagger2Exclick() {
        onStartActivity(MainActivity.class);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_sqlbrite_ex)
    public void onSqlbriteExClick() {
        onStartActivity(SqlbriteActivity.class);
    }

    private void onStartActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
