/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drarter.dagger2.example.base.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.drarter.dagger2.example.base.database.Db;

import java.util.Arrays;
import java.util.Collection;

import rx.functions.Func1;

public class ListsItem implements Parcelable {
    private static String ALIAS_LIST = "list";
    private static String ALIAS_ITEM = "item";

    private static String LIST_ID = ALIAS_LIST + "." + TodoList.ID;
    private static String LIST_NAME = ALIAS_LIST + "." + TodoList.NAME;
    private static String ITEM_COUNT = "item_count";
    private static String ITEM_ID = ALIAS_ITEM + "." + TodoItem.ID;
    private static String ITEM_LIST_ID = ALIAS_ITEM + "." + TodoItem.LIST_ID;

    public static Collection<String> TABLES = Arrays.asList(TodoList.TABLE, TodoItem.TABLE);
    public static String QUERY = ""
            + "SELECT " + LIST_ID + ", " + LIST_NAME + ", COUNT(" + ITEM_ID + ") as " + ITEM_COUNT
            + " FROM " + TodoList.TABLE + " AS " + ALIAS_LIST
            + " LEFT OUTER JOIN " + TodoItem.TABLE + " AS " + ALIAS_ITEM + " ON " + LIST_ID + " = " + ITEM_LIST_ID
            + " GROUP BY " + LIST_ID;

    private long id;
    private String name;
    private int itemCount;

    public ListsItem(long id, String name, int itemCount) {
        this.id = id;
        this.name = name;
        this.itemCount = itemCount;
    }

    static Func1<Cursor, ListsItem> MAPPER = new Func1<Cursor, ListsItem>() {
        @Override
        public ListsItem call(Cursor cursor) {
            long id = Db.getLong(cursor, TodoList.ID);
            String name = Db.getString(cursor, TodoList.NAME);
            int itemCount = Db.getInt(cursor, ITEM_COUNT);
            return new ListsItem(id, name, itemCount);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.itemCount);
    }

    protected ListsItem(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.itemCount = in.readInt();
    }

    public static final Parcelable.Creator<ListsItem> CREATOR = new Parcelable.Creator<ListsItem>() {
        public ListsItem createFromParcel(Parcel source) {
            return new ListsItem(source);
        }

        public ListsItem[] newArray(int size) {
            return new ListsItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public static ListsItem newInstance(@NonNull Cursor cursor) {
        long id = Db.getLong(cursor, TodoList.ID);
        String name = Db.getString(cursor, TodoList.NAME);
        int itemCount = Db.getInt(cursor, ITEM_COUNT);
        return new ListsItem(id, name, itemCount);
    }
}
