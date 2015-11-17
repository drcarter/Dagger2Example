package com.drarter.dagger2.example.ui.sql.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.drarter.dagger2.example.R;
import com.drarter.dagger2.example.base.fragment.BaseFragment;
import com.drarter.dagger2.example.base.model.ListsItem;
import com.drarter.dagger2.example.ui.sql.SqlbriteActivityComponent;
import com.drarter.dagger2.example.ui.sql.adapter.ListsAdapter2;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_IF_ROOM;
import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT;

public final class ListsFragment extends BaseFragment {

    public interface OnListsFragmentListener {
        void onListClicked(long id);

        void onNewListClicked();
    }

    public static ListsFragment newInstance() {
        return new ListsFragment();
    }

    @Inject
    BriteDatabase db;

    @Bind(android.R.id.list)
    ListView listView;
    @Bind(android.R.id.empty)
    View emptyView;

    private OnListsFragmentListener onListsFragmentListener;
    private ListsAdapter2 adapter;
    private Subscription subscription;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(SqlbriteActivityComponent.class).inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof OnListsFragmentListener)) {
            throw new IllegalStateException("Activity must implement fragment Listener.");
        }

        super.onAttach(activity);
        setHasOptionsMenu(true);

        onListsFragmentListener = (OnListsFragmentListener) activity;
        adapter = new ListsAdapter2(activity, null, true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.add(R.string.new_list)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        onListsFragmentListener.onNewListClicked();
                        return true;
                    }
                });
        MenuItemCompat.setShowAsAction(item, SHOW_AS_ACTION_IF_ROOM | SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.lists;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
    }

    @OnItemClick(android.R.id.list)
    void listClicked(long listId) {
        onListsFragmentListener.onListClicked(listId);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("To-Do");

        subscription = db.createQuery(ListsItem.TABLES, ListsItem.QUERY)
                .map(new Func1<SqlBrite.Query, Cursor>() {
                    @Override
                    public Cursor call(SqlBrite.Query query) {
                        return query.run();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }
}
