package com.example.studious;

import android.database.sqlite.SQLiteDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;


/*
 NOTE: for coverage has to be run as JUNIT test.
 (the red green back to back arrows. under edit configurations)
 add to jvm options
 -ea -noverify
 */


/**
 * Unit tests for the DatabaseHelper.
 **/
@RunWith(RobolectricTestRunner.class)
public class DBHelperTest {

    /** Dummy name for testing database. **/
    private DataBaseHelper databaseHelper;

    /** Create a temporary database. */
    @Before
    public void setUp() {
        RuntimeEnvironment.getApplication().deleteDatabase(DataBaseHelper.DATABASE_NAME);
        databaseHelper = new DataBaseHelper(RuntimeEnvironment.getApplication());
    }

    /** Delete temporary database. */
    @After
    public void tearDown() {
        databaseHelper.close();
        RuntimeEnvironment.getApplication().deleteDatabase(DataBaseHelper.DATABASE_NAME);
    }


    /** test help open or create new database. */
    @Test
    public void testSetup() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        assertFalse(database.isReadOnly());
        database.close();
    }

    /** Test closing of the database helper. */
    @Test
    public void testClose() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        databaseHelper.close();
        assertFalse(database.isOpen());
    }

    /** Test helper creates database on first run of application. */
    @Test
    public void onCreate() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        assertNotNull(database);
        assertTrue(database.isOpen());
        assertTrue(databaseHelper.onCreateCalled);
        database.close();
    }

    /** Test helper upgrades older databases. */
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

    /** Test of addItem. */
    @Test
    public void addItem() {
        String title = "debug alarm";
        int type = Items.TYPE_ALARM;
        Items newItem = new Items(-1, title, type, "here", "12:30", "13:00", "04/17/2022", "04/17/2022");
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);
    }

    /** test of updateItem. */
    @Test
    public void updateItem() {
        // add
        String title = "debug alarm";
        int type = Items.TYPE_ALARM;
        Items newItem = new Items(-1, title, type, "here", "12:30", "13:00", "04/17/2022", "04/17/2022");
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

    /** Test of deleteItem. */
    @Test
    public void deleteItem() {
        // add
        String title = "debug alarm";
        int type = Items.TYPE_ALARM;
        Items newItem = new Items(-1, title, type, "here", "12:30", "13:00", "04/17/2022", "04/17/2022");
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);

        boolean success = databaseHelper.deleteItem(row_id);
        assertTrue(success);
    }

    /** Test of getItem. */
    @Test
    public void getItem() {
        String title = "debug cal";
        int type = Items.TYPE_CALENDAR;
        Items newItem = new Items(-1, title, type, "here", "12:30", "13:00", "04/17/2022", "04/17/2022");
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);

        Items I = databaseHelper.getItem(row_id);
        assertNotNull(I);
        assertEquals(title, I.getItemTitle());
        assertEquals(type, I.getType());
        assertEquals(row_id, I.getId());
    }

    /** test of listItems. */
    @Ignore("gradle coverage won't work on failure.")
    @Test
    public void listItems() {
        fail("Implement me!!!!");
    }
}