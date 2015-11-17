package com.drarter.dagger2.example.ui.sql.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.drarter.dagger2.example.R;
import com.drarter.dagger2.example.base.model.TodoItem;
import com.drarter.dagger2.example.internal.di.component.HasComponent;
import com.drarter.dagger2.example.ui.sql.SqlbriteActivityComponent;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import static butterknife.ButterKnife.findById;

public final class NewItemFragment extends DialogFragment {
    private static final String KEY_LIST_ID = "list_id";

    public static NewItemFragment newInstance(long listId) {
        Bundle arguments = new Bundle();
        arguments.putLong(KEY_LIST_ID, listId);

        NewItemFragment fragment = new NewItemFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject
    BriteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(SqlbriteActivityComponent.class).inject(this);
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    private long getListId() {
        return getArguments().getLong(KEY_LIST_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();
        View view = LayoutInflater.from(context).inflate(R.layout.new_item, null);

        final EditText name = findById(view, android.R.id.input);

        return new AlertDialog.Builder(context) //
                .setTitle(R.string.new_item)
                .setView(view)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        createClicked.onNext("clicked");

                        db.insert(TodoItem.TABLE,
                                new TodoItem.Builder().listId(getListId()).description(name.getText().toString()).build());

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                    }
                })
                .create();
    }
}
