package com.drarter.dagger2.example.base.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.drarter.dagger2.example.base.database.Db;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class TodoList implements Parcelable {
    public static final String TABLE = "todo_list";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String ARCHIVED = "archived";

    private long id;
    private String name;
    private boolean isArchived;

    public TodoList(long id, String name, boolean isArchived) {
        this.id = id;
        this.name = name;
        this.isArchived = isArchived;
    }

    public static Func1<Cursor, List<TodoList>> MAP = new Func1<Cursor, List<TodoList>>() {
        @Override
        public List<TodoList> call(final Cursor cursor) {
            try {
                List<TodoList> values = new ArrayList<>(cursor.getCount());

                while (cursor.moveToNext()) {
                    long id = Db.getLong(cursor, ID);
                    String name = Db.getString(cursor, NAME);
                    boolean archived = Db.getBoolean(cursor, ARCHIVED);
                    values.add(new TodoList(id, name, archived));
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public Builder archived(boolean archived) {
            values.put(ARCHIVED, archived);
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
        dest.writeString(this.name);
        dest.writeByte(isArchived ? (byte) 1 : (byte) 0);
    }

    protected TodoList(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.isArchived = in.readByte() != 0;
    }

    public static final Creator<TodoList> CREATOR = new Creator<TodoList>() {
        public TodoList createFromParcel(Parcel source) {
            return new TodoList(source);
        }

        public TodoList[] newArray(int size) {
            return new TodoList[size];
        }
    };
}
