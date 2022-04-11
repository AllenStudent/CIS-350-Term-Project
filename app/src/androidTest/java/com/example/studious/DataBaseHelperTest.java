package com.example.studious;

import android.database.sqlite.SQLiteDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.*;

/*
    coverage: all test have to pass first
    short version:
    add: debug { testCoverageEnabled true }
    to (if not already there): buildTypes {
    have virtual device running
    run in terminal: ./gradlew createDebugCoverageReport

    work around failed tests
    add: debug { testCoverageEnabled true }
    to (if not already there): buildTypes {

    add: project.gradle.taskGraph.whenReady {
            connectedDebugAndroidTest {
                ignoreFailures = true
            }
        }
    have virtual device running
    run in terminal: ./gradlew createDebugCoverageReport
    maybe: ./gradlew createDebugCoverageReport --continue
    report in:  \CIS-350-Term-Project\app\build\reports\coverage\androidTest\debug


    ./gradlew clean


    https://developer.android.com/studio/test/command-line
    ./gradlew test
    ./gradlew connectedAndroidTest

 */

/**
 * Unit tests for the DatabaseHelper.
 **/
@RunWith(AndroidJUnit4.class)
public class DataBaseHelperTest {

    /** Dummy name for testing database. **/
    public static final String DATABASE_NAME = "unit_test_items.db";
    private DataBaseHelper databaseHelper;

    /** Create a temporary database. */
    @Before
    public void setUp() {
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

    /** Delete temporary database. */
    @After
    public void tearDown() {
        databaseHelper.close();
        getApplicationContext().deleteDatabase(DATABASE_NAME);
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
        Items newItem = new Items(-1, title, type);
        long row_id = databaseHelper.addItem(newItem);
        assertNotEquals(row_id, -1);
    }

    /** test of updateItem. */
    @Test
    public void updateItem() {
        // add
        String title = "debug alarm";
        int type = Items.TYPE_ALARM;
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

    /** Test of deleteItem. */
    @Test
    public void deleteItem() {
        // add
        String title = "debug alarm";
        int type = Items.TYPE_ALARM;
        Items newItem = new Items(-1, title, type);
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
        Items newItem = new Items(-1, title, type);
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