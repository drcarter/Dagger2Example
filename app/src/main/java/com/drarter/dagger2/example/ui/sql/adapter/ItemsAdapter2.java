package com.drarter.dagger2.example.ui.sql.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

import com.drarter.dagger2.example.base.model.TodoItem;

import rx.functions.Action1;

public class ItemsAdapter2 extends CursorAdapter implements Action1<Cursor> {

    public ItemsAdapter2(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ItemsAdapter2(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TodoItem item = TodoItem.createInstance(cursor);

        CheckedTextView textView = (CheckedTextView) view;
        textView.setChecked(item.isComplete());

        CharSequence description = item.getDescription();
        if (item.isComplete()) {
            SpannableString spannable = new SpannableString(description);
            spannable.setSpan(new StrikethroughSpan(), 0, description.length(), 0);
            description = spannable;
        }

        textView.setText(description);
    }

    @Override
    public void call(Cursor cursor) {
        changeCursor(cursor);
        notifyDataSetChanged();
    }
}
