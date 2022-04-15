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
    public static final String COL_START = "ITEMSTART";
    /** End of item **/
    public static final String COL_END = "ITEMEND";
    /** current database version. **/
    public static final int version = 1;

    /*
            Tutorials

            Playlist of youtube videos
            The 3 part tutorial had a good combination of the different parts.
            https://www.youtube.com/playlist?list=PLw2_IqRi5KfkAU-X06L7Vsh3g-_ga5R_5

            some Sqlite stuff
            https://www.tutorialspoint.com/android/android_sqlite_database.htm
            https://developer.android.com/reference/android/database/sqlite/SQLiteCursor
            https://aboutreact.com/see-saved-data-of-the-sqlite-database-in-device/
            https://www.sqlitetutorial.net/sqlite-date/
            https://www.sqlite.org/datatype3.html#datetime
            https://stackoverflow.com/questions/8291673/how-to-add-new-column-to-android-sqlite-database
            https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#public-methods_1
            https://findnerd.com/list/view/How-to-remove-item-from-RecyclerView-DataBase-Sqlite-helper-classRecyclerView/12515/
            https://www.tabnine.com/code/java/methods/android.database.sqlite.SQLiteOpenHelper/getReadableDatabase
            https://stackoverflow.com/questions/26711050/what-is-the-difference-between-query-and-rawquery-in-sqlite-and-which-one-is/26711112


            recyclerview
            https://developer.android.com/guide/topics/ui/layout/recyclerview
            https://github.com/android/views-widgets-samples/tree/main/RecyclerView/
            https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
            https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
            https://www.stacktips.com/tutorials/android/android-recyclerview-example
            https://developer.android.com/jetpack/androidx/releases/recyclerview
            https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter
            https://stackoverflow.com/questions/42176807/how-do-i-load-a-sqlite-database-into-recyclerview
            https://github.com/android/views-widgets-samples



            On my to get to list:

                TimePickerDialog
                https://www.youtube.com/watch?v=QMwaNN_aM3U

                DatePicket
                https://developer.android.com/reference/android/widget/DatePicker

                CalendarVeiw - Not sure if is userfull
                https://www.youtube.com/watch?v=hHjFIG0TtA0&list=PL7kJVa0DqITk1Kgz1E-nbyySgSVxMwRbm&index=3&t=10s

                Tutorial series on Notifications
                https://www.youtube.com/playlist?list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM
     */

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

        String createTableStatement = "CREATE TABLE " + TABLE_ITEMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT, " + COL_ITEMTYPE + " INT,"
                + COL_ITEMNOTES + " TEXT, " + COL_START + " TEXT,"
                + COL_END + " TEXT)";

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

        /* code goes here */
        //ALTER TABLE
    }


    /**
     * Insert one item of data into database.
     * Let ContentValues and SQLiteDatabase handle all the
     * backend sql type stuff.
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
        cv.put(COL_ITEMNOTES, items.getItemNotes());
        cv.put(COL_START, items.getItemStart());
        cv.put(COL_END, items.getItemEnd());

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
        cv.put(COL_ITEMNOTES, item.getItemNotes());
        cv.put(COL_START, item.getItemStart());
        cv.put(COL_END, item.getItemEnd());

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
            String itemNotes = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEMNOTES));
            String itemStart = cursor.getString(cursor.getColumnIndexOrThrow(COL_START));
            String itemEnd = cursor.getString(cursor.getColumnIndexOrThrow(COL_END));

            /* copy data into our item class. */
            item = new Items(itemId, titleText, itemType, itemNotes, itemStart, itemEnd);


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
                String itemStart = cursor.getString(cursor.getColumnIndexOrThrow(COL_START));
                String itemEnd = cursor.getString(cursor.getColumnIndexOrThrow(COL_END));

                /* copy data into our item class. */
                Items items = new Items(itemId, titleText, itemType, itemNotes, itemStart, itemEnd);

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
