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
    /* cureent database and schema. */
    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_ITEMS = "ITEMS";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_ITEMTYPE = "ITEMTYPE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    /* database doesn't exist. create. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_ITEMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT, " + COL_ITEMTYPE + " INT)";

        db.execSQL(createTableStatement);
    }

    /* database version (most likely the schema) changed.
    update to new db. */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /* code goes here */
    }

    /* add item to database.
     * Let ContentValues and SQLiteDatabase handle all the backend sql
     * type stuff. */
    public boolean addItem(Items items) {
        /* class to store values */
        ContentValues cv = new ContentValues();

        /* add data */
        cv.put(COL_TITLE, items.getItemTitle());
        cv.put(COL_ITEMTYPE, items.getType());

        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(TABLE_ITEMS, null, cv);
        return insert != -1;
    }

    /* delete item from database. */
    public boolean deleteItem(int id) {
        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        /* somewhat overcomplicate. I could have just done
         * COL_ID + " = " + id
         * and left the last parameter null. But it illustrated
         * a function of the class. */
        int n_deleted = db.delete(
                TABLE_ITEMS,
                COL_ID + "= ? ",
                new String[]{Integer.toString(id)});
        db.close();
        return n_deleted > 0;
    }

    /* again let SQLiteDatabase handle all the nasty backend sql stuff.
    Also helpfull later if the schema changes, we dont have to constantly
    be rewrting sql statments.
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
