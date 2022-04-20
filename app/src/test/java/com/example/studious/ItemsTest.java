package com.example.studious;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Items Class.
 */
public class ItemsTest {

    /**
     * test for getId
     **/
    @Test
    public void getId() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getId(), 0);
    }

    /**
     * test for setId
     **/
    @Test
    public void setId() {
        Items t = new Items(1, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getId(), 1);
        t.setId(2);
        assertEquals(t.getId(), 2);
    }

    /**
     * test for getItemTitle
     **/
    @Test
    public void getItemTitle() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemTitle(), "Test");
    }

    /**
     * test for setItemTitle
     **/
    @Test
    public void setItemTitle() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemTitle(), "Test");
        t.setItemTitle("New Test");
        assertEquals(t.getItemTitle(), "New Test");
    }

    /**
     * test for getType
     **/
    @Test
    public void getType() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getType(), 1);
    }

    /**
     * test for setType
     **/
    @Test
    public void setType() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getType(), 1);
        t.setType(5);
        assertEquals(t.getType(), 5);
    }

    /**
     * test for testToString
     **/
    @Test
    public void testToString() {
        Items t = new Items(5, "Test", Items.TYPE_TODO, "test",
                "12:30", "13:00",
                "04/17/2022", "04/17/2022");
        assertTrue(t.toString().contains("id=5"));
        assertTrue(t.toString().contains("itemTitle='Test'"));
        assertTrue(t.toString().contains("type=1"));
        assertTrue(t.toString().contains("itemNotes='test'"));
        assertTrue(t.toString().contains("itemStartDate='04/17/2022'"));
        assertTrue(t.toString().contains("itemEndDate='04/17/2022'"));
        assertTrue(t.toString().contains("itemStartTime='12:30'"));
        assertTrue(t.toString().contains("itemEndTime='13:00'"));
    }

    /**
     * test for testEquals
     **/
    @Test
    public void testEquals() {
        Items s = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        Items t = new Items(1, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(s, t);
    }

    /**
     * test for testNotEquals
     **/
    @Test
    public void testNotEquals() {
        Items s = new Items(0, "Test", Items.TYPE_TODO, "Test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        Items t = new Items(1, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        Items u = new Items(5, "Test", Items.TYPE_CALENDAR, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertNotEquals(s.getItemNotes(), u.getItemNotes());
        assertNotEquals(s.getItemNotes(), t.getItemNotes());
    }

    /**
     * test for testEqualsNull
     **/
    @Test
    public void testEqualsNull() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertNotEquals(t, null);
    }

    /**
     * test for testEqualsSelf
     **/
    @Test
    public void testEqualsSelf() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t, t);
    }

    /**
     * test for testEqualsNotClass
     **/
    @Test
    public void testEqualsNotClass() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertNotEquals(t, "String");
    }

    /**
     * test for testEquals1Class
     **/
    @Test
    public void testEquals1() {
        Items t = new Items(1, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertTrue(t.getId() == 1);
    }

    /**
     * test for setItemNotes
     **/
    @Test
    public void setItemNotes() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemNotes(), "test");
        t.setItemNotes("Hello");
        assertEquals(t.getItemNotes(), "Hello");
    }

    /**
     * test for getItemNotes
     **/
    @Test
    public void getItemNotes() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemNotes(), "test");
    }

    /**
     * test for getItemStartTime
     **/
    @Test
    public void getItemStartTime() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemStartTime(), "12:30");
    }

    /**
     * test for setItemStartTime
     **/
    @Test
    public void setItemStartTime() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemStartTime(), "12:30");
        t.setItemStartTime("12:45");
        assertEquals(t.getItemStartTime(), "12:45");
    }

    /**
     * test for setItemStartTime
     **/
    @Test
    public void setItemStartDate() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemStartDate(), "04/17/2022");
        t.setItemStartDate("04/17/2023");
        assertEquals(t.getItemStartDate(), "04/17/2023");
    }

    /**
     * test for getItemStartDate
     **/
    @Test
    public void getItemStartDate() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemStartDate(), "04/17/2022");
    }

    /**
     * test for setItemEndDate
     **/
    @Test
    public void setItemEndDate() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemEndDate(), "04/17/2022");
        t.setItemEndDate("04/19/2023");
        assertEquals(t.getItemEndDate(), "04/19/2023");
    }

    /**
     * test for getItemEndDate
     **/
    @Test
    public void getItemEndDate() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemEndDate(), "04/17/2022");
    }

    /**
     * test for getItemEndTime
     **/
    @Test
    public void getItemEndTime() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemEndTime(), "13:00");
    }

    /**
     * test for setItemEndTime
     **/
    @Test
    public void setItemEndTime() {
        Items t = new Items(0, "Test", Items.TYPE_TODO, "test", "12:30", "13:00", "04/17/2022", "04/17/2022");
        assertEquals(t.getItemEndTime(), "13:00");
        t.setItemEndTime("14:00");
        assertEquals(t.getItemEndTime(), "14:00");
    }
}