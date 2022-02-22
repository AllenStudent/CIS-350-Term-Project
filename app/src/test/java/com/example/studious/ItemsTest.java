package com.example.studious;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemsTest {

    @Test
    public void getId() {
        Items t = new Items(0,"Test",1);
        assertEquals(t.getId(),0);
    }

    @Test
    public void setId() {
        Items t = new Items(1,"Test",1);
        assertEquals(t.getId(),1);
        t.setId(2);
        assertEquals(t.getId(),2);
    }

    @Test
    public void getItemTitle() {
        Items t = new Items(1,"Test",1);
        assertEquals(t.getItemTitle(),"Test");
    }

    @Test
    public void setItemTitle() {
        Items t = new Items(1,"Test",1);
        assertEquals(t.getItemTitle(),"Test");
        t.setItemTitle("New Test");
        assertEquals(t.getItemTitle(),"New Test");
    }

    @Test
    public void getType() {
        Items t = new Items(1,"Test",1);
        assertEquals(t.getType(),1);
    }

    @Test
    public void setType() {
        Items t = new Items(1,"Test",1);
        assertEquals(t.getType(),1);
        t.setType(5);
        assertEquals(t.getType(),5);
    }

    @Test
    public void testToString() {
        Items t = new Items(5,"By",5);
        assertTrue(t.toString().contains("id=5"));
        assertTrue(t.toString().contains("itemTitle='By'"));
        assertTrue(t.toString().contains("type=5"));
    }

    @Test
    public void testEquals(){
        Items s = new Items(0,"By",5);
        Items t = new Items(5,"By",5);
        assertEquals(s, t);
    }

    @Test
    public void testNotEquals(){
        Items s = new Items(0,"By",5);
        Items t = new Items(5,"a",5);
        Items u = new Items(5,"By",0);
        assertNotEquals(s, t);
        assertNotEquals(s, u);
    }

    @Test
    public void testEqualsNull(){
        Items t = new Items(0,"By",5);
        assertNotEquals(t, null);
    }

    @Test
    public void testEqualsSelf(){
        Items t = new Items(0,"By",5);
        assertEquals(t, t);
    }

    @Test
    public void testEqualsNotClass(){
        Items t = new Items(0,"By",5);
        assertNotEquals(t, "String");
    }
}