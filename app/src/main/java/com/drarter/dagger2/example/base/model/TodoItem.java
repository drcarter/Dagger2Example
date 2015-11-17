package com.drarter.dagger2.example.base.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.base.database.Db;

import rx.functions.Func1;

public class TodoItem implements Parcelable {
    public static final String TABLE = "todo_item";

    public static final String ID = "_id";
    public static final String LIST_ID = "todo_list_id";
    public static final String DESCRIPTION = "description";
    public static final String COMPLETE = "complete";

    private long id;
    private long listId;
    private String description;
    private boolean isComplete;

    public TodoItem(long id, long listId, String description, boolean isComplete) {
        this.id = id;
        this.listId = listId;
        this.description = description;
        this.isComplete = isComplete;
    }

    public static final Func1<Cursor, TodoItem> MAPPER = new Func1<Cursor, TodoItem>() {
        @Override
        public TodoItem call(Cursor cursor) {
            long id = Db.getLong(cursor, ID);
            long listId = Db.getLong(cursor, LIST_ID);
            String description = Db.getString(cursor, DESCRIPTION);
            boolean complete = Db.getBoolean(cursor, COMPLETE);

            return new TodoItem(id, listId, description, complete);
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public static TodoItem createInstance(@NonNull Cursor cursor) {
        long id = Db.getLong(cursor, ID);
        long listId = Db.getLong(cursor, LIST_ID);
        String description = Db.getString(cursor, DESCRIPTION);
        boolean complete = Db.getBoolean(cursor, COMPLETE);

        return new TodoItem(id, listId, description, complete);
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder listId(long listId) {
            values.put(LIST_ID, listId);
            return this;
        }

        public Builder description(String description) {
            values.put(DESCRIPTION, description);
            return this;
        }

        public Builder complete(boolean complete) {
            values.put(COMPLETE, complete);
            return this;
        }

        public ContentValues build() {
            return values; // TODO defensive copy?
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.listId);
        dest.writeString(this.description);
        dest.writeByte(isComplete ? (byte) 1 : (byte) 0);
    }

    protected TodoItem(Parcel in) {
        this.id = in.readLong();
        this.listId = in.readLong();
        this.description = in.readString();
        this.isComplete = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TodoItem> CREATOR = new Parcelable.Creator<TodoItem>() {
        public TodoItem createFromParcel(Parcel source) {
            return new TodoItem(source);
        }

        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };
}
