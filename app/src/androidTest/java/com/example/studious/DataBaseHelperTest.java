package com.example.studious;

import android.database.sqlite.SQLiteDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;


@RunWith(AndroidJUnit4.class)
public class DataBaseHelperTest {

    public static final String DATABASE_NAME = "unit_test_items.db";
    private DataBaseHelper databaseHelper;

    @Before
    public void setUp() throws Exception {
        //        getApplicationContext().deleteDatabase(DataBaseHelper.DATABASE_NAME);
        //        databaseHelper = new DataBaseHelper(getApplicationContext());
        getApplicationContext().deleteDatabase(DATABASE_NAME);
        // create shiny new test database
        databaseHelper = new DataBaseHelper(
                getApplicationContext(),
                DATABASE_NAME,  // create debug database.
                null,
                DataBaseHelper.version);
    }

    @After
    public void tearDown() throws Exception {
        databaseHelper.close();
        getApplicationContext().deleteDatabase(DATABASE_NAME);
    }


    @Test
    public void testSetup() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        assertFalse(database.isReadOnly());
        database.close();
    }

    @Test
    public void testClose() {
        databaseHelper = new DataBaseHelper(
                getApplicationContext(),
                null,  // in memory db
                null,
                DataBaseHelper.version);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        databaseHelper.close();
        assertFalse(database.isOpen());
    }

    @Test
    public void onCreate() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        assertTrue(databaseHelper.onCreateCalled);
        database.close();
    }

    @Test
    public void onUpgrade() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        assertFalse(database.isReadOnly());
        assertFalse(databaseHelper.onUpgradeCalled);
        // TODO - add test when upgrade is implemented
        database.close();
    }

    @Test
    public void addItem() {
        String title = "debug alarm";
        int type = MainActivity.TYPE_ALARM;
        Items newItem = new Items(-1, title, type);
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);
    }

    @Test
    public void updateItem() {
        // add
        String title = "debug alarm";
        int type = MainActivity.TYPE_ALARM;
        Items newItem = new Items(-1, title, type);
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);
        // get
        newItem = databaseHelper.getItem(row_id);

        // Update
        String new_title = "new alarm";
        newItem.setItemTitle(new_title);
        boolean success = databaseHelper.updateItem(newItem);
        assertTrue(success);

        //check
        // get update
        Items updateItem = databaseHelper.getItem(row_id);
        assertNotNull(updateItem);

        assertEquals(newItem.getId(), updateItem.getId());
        assertEquals(newItem, updateItem);
    }

    @Test
    public void deleteItem() {
        // add
        String title = "debug alarm";
        int type = MainActivity.TYPE_ALARM;
        Items newItem = new Items(-1, title, type);
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);

        boolean success = databaseHelper.deleteItem(row_id);
        assertTrue(success);
    }

    @Test
    public void getItem() {
        String title = "debug cal";
        int type = MainActivity.TYPE_CALENDER;
        Items newItem = new Items(-1, title, type);
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);

        Items I = databaseHelper.getItem(row_id);
        assertNotNull(I);
        assertEquals(title, I.getItemTitle());
        assertEquals(type, I.getType());
        assertEquals(row_id, I.getId());
    }

    @Test
    public void listItems() {
    }
}