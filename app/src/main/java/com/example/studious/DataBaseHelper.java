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

    /* database version changed. update schema. */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /* code goes here */
    }

    /* add item to database. */
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
        int n_deleted = db.delete(
                TABLE_ITEMS,
                COL_ID + "= ? ",
                new String[]{Integer.toString(id)});
        db.close();
        return n_deleted > 0;
    }

    ArrayList<Items> listItems() {
        String queryString = "SELECT * FROM " + TABLE_ITEMS;

        /* open read only database. doesn't have to wait for
        write permission'*/
        SQLiteDatabase db = this.getReadableDatabase();

        /* variable to store data */
        ArrayList<Items> returnList = new ArrayList<>();

        /* get all items in table */
        Cursor cursor = db.query(
                TABLE_ITEMS,
                null,
                null,
                null,
                null,
                null,
                COL_ID + " DESC"
        );

        /* get first item. false if query is empty. */
        if (cursor != null && cursor.moveToFirst())
        {
            // loop through cursor results. put them in the return list
            do
            {
                /* pull data from cursor. */
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String titleText = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
                int itemType = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ITEMTYPE));

                /* copy data into our item model. */
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
