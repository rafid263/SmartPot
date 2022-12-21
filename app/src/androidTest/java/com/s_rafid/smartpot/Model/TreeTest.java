package com.s_rafid.smartpot.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {
    Tree t1=new Tree("Tree1","CSE470");
    Tree t2=new Tree("","CSE470");
    @Test
    public void getMood0() {
        assertEquals(0,t1.getMood(25,65,90));
    }
    @Test
    public void getMood1() {
        assertEquals(1,t1.getMood(45,65,90));
    }
    @Test
    public void getMood2() {
        assertEquals(2,t1.getMood(15,65,90));
    }
    @Test
    public void getMood3() {
        assertEquals(3,t1.getMood(25,25,90));
    }
    @Test
    public void getMood4() {
        assertEquals(4,t1.getMood(25,45,40));
    }
    @Test
    public void getName() {
        assertEquals("Tree1",t1.getName());
    }

    @Test
    public void getAuth() {
        assertEquals("CSE470",t1.getAuth());
    }

    @Test
    public void isValid1() {
        assertEquals(1,t1.isValid());
    }
    @Test
    public void isValid0() {
        assertEquals(0,t2.isValid());
    }

}