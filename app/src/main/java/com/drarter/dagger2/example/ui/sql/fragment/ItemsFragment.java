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
import android.widget.AdapterView;
import android.widget.ListView;

import com.drarter.dagger2.example.R;
import com.drarter.dagger2.example.base.database.Db;
import com.drarter.dagger2.example.base.model.TodoItem;
import com.drarter.dagger2.example.base.model.TodoList;
import com.drarter.dagger2.example.base.fragment.BaseFragment;
import com.drarter.dagger2.example.ui.sql.SqlbriteActivityComponent;
import com.drarter.dagger2.example.ui.sql.adapter.ItemsAdapter2;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_IF_ROOM;
import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT;
import static com.squareup.sqlbrite.SqlBrite.Query;

public final class ItemsFragment extends BaseFragment {
    private static final String KEY_LIST_ID = "list_id";
    private static final String LIST_QUERY = "SELECT * FROM "
            + TodoItem.TABLE
            + " WHERE "
            + TodoItem.LIST_ID
            + " = ? ORDER BY "
            + TodoItem.COMPLETE

            + " ASC";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM "
            + TodoItem.TABLE
            + " WHERE "
            + TodoItem.COMPLETE
            + " = "
            + Db.BOOLEAN_FALSE
            + " AND "
            + TodoItem.LIST_ID
            + " = ?";
    private static final String TITLE_QUERY =
            "SELECT " + TodoList.NAME + " FROM " + TodoList.TABLE + " WHERE " + TodoList.ID + " = ?";

    public interface OnItemsFragmentListener {
        void onNewItemClicked(long listId);
    }

    public static ItemsFragment newInstance(long listId) {
        Bundle arguments = new Bundle();
        arguments.putLong(KEY_LIST_ID, listId);

        ItemsFragment fragment = new ItemsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject
    BriteDatabase db;

    @Bind(android.R.id.list)
    ListView listView;
    @Bind(android.R.id.empty)
    View emptyView;

    private OnItemsFragmentListener onItemsFragmentListener;
    //    private ItemsAdapter adapter;
    private ItemsAdapter2 adapter;
    private CompositeSubscription subscriptions;

    private long getListId() {
        return getArguments().getLong(KEY_LIST_ID);
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof OnItemsFragmentListener)) {
            throw new IllegalStateException("Activity must implement fragment Listener.");
        }

        super.onAttach(activity);

        getComponent(SqlbriteActivityComponent.class).inject(this);

        setHasOptionsMenu(true);

        onItemsFragmentListener = (OnItemsFragmentListener) activity;
//        adapter = new ItemsAdapter(activity);
        adapter = new ItemsAdapter2(activity, null, true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.add(R.string.new_item)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        onItemsFragmentListener.onNewItemClicked(getListId());
                        return true;
                    }
                });
        MenuItemCompat.setShowAsAction(item, SHOW_AS_ACTION_IF_ROOM | SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.items;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                if (cursor != null) {
                    TodoItem item = TodoItem.createInstance(cursor);
                    boolean newValue = !item.isComplete();
                    db.update(TodoItem.TABLE, new TodoItem.Builder().complete(newValue).build(),
                            TodoItem.ID + " = ?", String.valueOf(item.getId()));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String listId = String.valueOf(getListId());

        subscriptions = new CompositeSubscription();

        Observable<Integer> itemCount = db.createQuery(TodoItem.TABLE, COUNT_QUERY, listId)
                .map(new Func1<Query, Integer>() {
                    @Override
                    public Integer call(Query query) {
                        Cursor cursor = query.run();
                        try {
                            if (!cursor.moveToNext()) {
                                throw new AssertionError("No rows");
                            }
                            return cursor.getInt(0);
                        } finally {
                            cursor.close();
                        }
                    }
                });
        Observable<String> listName =
                db.createQuery(TodoList.TABLE, TITLE_QUERY, listId).map(new Func1<Query, String>() {
                    @Override
                    public String call(Query query) {
                        Cursor cursor = query.run();
                        try {
                            if (!cursor.moveToNext()) {
                                throw new AssertionError("No rows");
                            }
                            return cursor.getString(0);
                        } finally {
                            cursor.close();
                        }
                    }
                });
        subscriptions.add(
                Observable.combineLatest(listName, itemCount, new Func2<String, Integer, String>() {
                    @Override
                    public String call(String listName, Integer itemCount) {
                        return listName + " (" + itemCount + ")";
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String title) {
                                getActivity().setTitle(title);
                            }
                        }));
        subscriptions.add(db.createQuery(TodoItem.TABLE, LIST_QUERY, listId)
                .map(new Func1<Query, Cursor>() {
                    @Override
                    public Cursor call(Query query) {
                        return query.run();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter));
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }
}
