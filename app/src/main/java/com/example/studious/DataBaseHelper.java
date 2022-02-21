package com.example.studious;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/* responsible for managing the SQLite database */
public class DataBaseHelper extends SQLiteOpenHelper {
    public boolean onCreateCalled = false;
    public boolean onUpgradeCalled = false;

    /* current database and schema. */
    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_ITEMS = "ITEMS";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_ITEMTYPE = "ITEMTYPE";
    public static final int version = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    public DataBaseHelper(
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }


    /* database doesn't exist. create. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        onCreateCalled = true;

        String createTableStatement = "CREATE TABLE " + TABLE_ITEMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT, " + COL_ITEMTYPE + " INT)";

        db.execSQL(createTableStatement);
    }

    /* database version (most likely the schema) changed.
    update to new db. */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgradeCalled = true;

        /* code goes here */
    }

    /* add item to database.
     * Let ContentValues and SQLiteDatabase handle all the backend sql
     * type stuff. */

    /**
     * Insert one item of data into database.
     *
     * @param items data to be inserted
     * @return row id newly inserted row, -1 if and error occurred.
     */
    public long addItem(Items items) {
        /* class to store values */
        ContentValues cv = new ContentValues();

        /* add data */
        cv.put(COL_TITLE, items.getItemTitle());
        cv.put(COL_ITEMTYPE, items.getType());

        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_ITEMS, null, cv);
    }

    /**
     * Update data in one row
     *
     * @param item data in Items format
     * @return true if row was updated
     */
    public boolean updateItem(Items item) {
        /* class to store values */
        ContentValues cv = new ContentValues();

        /* add data */
        cv.put(COL_TITLE, item.getItemTitle());
        cv.put(COL_ITEMTYPE, item.getType());

        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        int updated = db.update(
                TABLE_ITEMS,
                cv,
                COL_ID + " = " + item.getId(),
                null);
        return updated == 1;
    }

    /**
     * get data for one row in database
     *
     * @param id row id to retrieve
     * @return data in Items Class format
     */
    public Items getItem(long id) {
        Items item = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_ITEMS,
                null,
                COL_ID + " = " + id,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst())
        {
            /* pull data from cursor. */
            /* get data by its column name. more flexible than assuming
            title is col 0, type in col 3, etc.
            */
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String titleText = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
            int itemType = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ITEMTYPE));

            /* copy data into our item class. */
            item = new Items(itemId, titleText, itemType);


        }

        /* clean up. */
        cursor.close();

        /* clean up. */
        db.close();

        return item;
    }

    /* delete item from database. */

    /**
     * Delete one item from database.
     *
     * @param id row id of item to be deleted
     * @return true if row id was deleted
     */
    public boolean deleteItem(long id) {
        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        /* somewhat overcomplicate. I could have just done
         * COL_ID + " = " + id
         * and left the last parameter null. But it illustrated
         * a function of the class. */
        int n_deleted = db.delete(
                TABLE_ITEMS,
                COL_ID + "=" + id,
                null);
        db.close();
        return n_deleted > 0;
    }

    /* again let SQLiteDatabase handle all the nasty backend sql stuff.
    Also, helpfully later if the schema changes, we don't have to constantly
    be rewriting sql statements.
     */

    /**
     * Get entire database in format to use in ItemAdapter.
     *
     * @return ArrayList of all items in database in Items Class format
     */
    ArrayList<Items> listItems() {
        /* open read only database. It doesn't have to wait for
        write permission'. Which takes significantly longer since it
        has to wait for everybody trying to write before us to finish.
        In this case it's only us but there are probably added complexity
        behind the scenes for locking that will slow it down. */
        SQLiteDatabase db = this.getReadableDatabase();

        /* variable to store data. ArrayList CIS163 :)  */
        ArrayList<Items> returnList = new ArrayList<>();

        /* get all items in table */
        Cursor cursor = db.query(
                TABLE_ITEMS,
                null,
                null,
                null,
                null,
                null,
                /* sort by newest added first. */
                COL_ID + " DESC"
        );

        /* get first item. false if query is empty. */
        if (cursor != null && cursor.moveToFirst())
        {
            // loop through cursor results. put them in the return list
            do
            {
                /* pull data from cursor. */
                /* get data by its column name. more flexible than assuming
                title is col 0, type in col 3, etc.
                */
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String titleText = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
                int itemType = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ITEMTYPE));

                /* copy data into our item class. */
                Items items = new Items(itemId, titleText, itemType);

                /* add to List. */
                returnList.add(items);
            }
            while (cursor.moveToNext());
        }

        /* clean up. */
        cursor.close();

        /* clean up. */
        db.close();

        return returnList;
    }

}
