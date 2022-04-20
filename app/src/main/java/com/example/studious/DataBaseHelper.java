package com.example.studious;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * responsible for managing the SQLite database
 *
 * @author Ben Allen
 * @author Devin Elenbaase
 * @author Bryan VanDyke
 * @version Release 1
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    /** record if onCreate was called **/
    public boolean onCreateCalled = false;
    /** record if OnUpgrade was called **/
    public boolean onUpgradeCalled = false;

    /* current database and schema. */
    /** database name. **/
    public static final String DATABASE_NAME = "items.db";
    /** main table name. **/
    public static final String TABLE_ITEMS = "ITEMS";
    /** Id. Primary key. **/
    public static final String COL_ID = "ID";
    /** Title or description **/
    public static final String COL_TITLE = "TITLE";
    /** Type of item **/
    public static final String COL_ITEMTYPE = "ITEMTYPE";
    /** Notes of item **/
    public static final String COL_ITEMNOTES = "ITEMNOTES";
    /** Start of item **/
    public static final String COL_STARTTIME = "ITEMSTARTTIME";
    /** End of item **/
    public static final String COL_ENDTIME = "ITEMENDTIME";
    /** Start of item **/
    public static final String COL_STARTDATE = "ITEMSTARTDATE";
    /** End of item **/
    public static final String COL_ENDDATE = "ITEMENDDATE";
    /** current database version. **/
    public static final int version = 2;

    /**
     * Constructor a new database helper.
     *
     * @param context Interface to global information about an
     *                application environment.
     */
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    /**
     * Constructor a new database helper.
     *
     * @param context Interface to global information about an
     *                application environment. Used to get paths to db.
     * @param name    Database name to open or create. null for in memory db.
     * @param factory used for creating cursor objects. null for default.
     * @param version Database version. If the db is older onUpgrade is
     *                called.
     */
    public DataBaseHelper(
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }


    /**
     * database doesn't exist. create.
     *
     * @param db SQLiteDatabase. the db.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        onCreateCalled = true;

        String createTableStatement = "CREATE TABLE " + TABLE_ITEMS
                + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT, "
                + COL_ITEMTYPE + " INT, "
                + COL_ITEMNOTES + " TEXT, "
                + COL_STARTTIME + " TEXT, "
                + COL_ENDTIME + " TEXT, "
                + COL_STARTDATE + " TEXT, "
                + COL_ENDDATE + " TEXT"
                + ")";
        db.execSQL(createTableStatement);
    }


    /**
     * database version (most likely the schema) changed.
     * update to new db.
     *
     * @param sqLiteDatabase SQLiteDatabase. the db.
     * @param oldVersion     The version of the db we opened.
     * @param newVersion     The current version of the database for this app.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgradeCalled = true;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(sqLiteDatabase);
    }


    /**
     * Insert one item of data into database.
     * Let ContentValues and SQLiteDatabase handle all the
     * backend sql type stuff.
     *
     * @param items data to be inserted
     * @return row id newly inserted row, -1 if and error occurred.
     */
    public int addItem(Items items) {
        /* class to store values */
        ContentValues cv = new ContentValues();

        /* add data */
        cv.put(COL_TITLE, items.getItemTitle());
        cv.put(COL_ITEMTYPE, items.getType());
        cv.put(COL_ITEMNOTES, items.getItemNotes());
        cv.put(COL_STARTTIME, items.getItemStartTime());
        cv.put(COL_ENDTIME, items.getItemEndTime());
        cv.put(COL_STARTDATE, items.getItemStartDate());
        cv.put(COL_ENDDATE, items.getItemEndDate());

        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        return (int)db.insert(TABLE_ITEMS, null, cv);
    }

    /**
     * Update data in one row
     *
     * @param items data in Items format
     * @return true if row was updated
     */
    public boolean updateItem(Items items) {
        /* class to store values */
        ContentValues cv = new ContentValues();

        /* add data */
        cv.put(COL_TITLE, items.getItemTitle());
        cv.put(COL_ITEMTYPE, items.getType());
        cv.put(COL_ITEMNOTES, items.getItemNotes());
        cv.put(COL_STARTTIME, items.getItemStartTime());
        cv.put(COL_ENDTIME, items.getItemEndTime());
        cv.put(COL_STARTDATE, items.getItemStartDate());
        cv.put(COL_ENDDATE, items.getItemEndDate());

        /* open database. locks database? */
        SQLiteDatabase db = this.getWritableDatabase();
        int updated = db.update(
                TABLE_ITEMS,
                cv,
                COL_ID + " = " + items.getId(),
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
            String itemNotes = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEMNOTES));
            String itemStartTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_STARTTIME));
            String itemEndTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_ENDTIME));
            String itemStartDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_STARTDATE));
            String itemEndDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_ENDDATE));

            /* copy data into our item class. */
            item = new Items(itemId, titleText, itemType, itemNotes, itemStartTime, itemEndTime, itemStartDate, itemEndDate);


        }

        /* clean up. */
        if (cursor != null)
            cursor.close();

        /* clean up. */
        db.close();

        return item;
    }


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


    /**
     * Get entire database in format to use in ItemAdapter.
     * again let SQLiteDatabase handle all the nasty backend sql stuff.
     * Also, helpfully later if the schema changes, we don't have to
     * constantly be rewriting sql statements.
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
                String itemNotes = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEMNOTES));
                String itemStartTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_STARTTIME));
                String itemEndTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_ENDTIME));
                String itemStartDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_STARTDATE));
                String itemEndDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_ENDDATE));

                /* copy data into our item class. */
                Items items = new Items(itemId, titleText, itemType, itemNotes, itemStartTime, itemEndTime, itemStartDate, itemEndDate);

                /* add to List. */
                returnList.add(items);
            }
            while (cursor.moveToNext());
        }

        /* clean up. */
        if (cursor != null)
            cursor.close();

        /* clean up. */
        db.close();

        return returnList;
    }

}
