package com.drarter.dagger2.example.ui.sql.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.drarter.dagger2.example.base.model.ListsItem;

import rx.functions.Action1;

public class ListsAdapter2 extends CursorAdapter implements Action1<Cursor> {

    public ListsAdapter2(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ListsAdapter2(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ListsItem item = ListsItem.newInstance(cursor);
        ((TextView) view).setText(item.getName() + " (" + item.getItemCount() + ")");
    }

    @Override
    public void call(Cursor cursor) {
        changeCursor(cursor);
        notifyDataSetChanged();
    }
}
