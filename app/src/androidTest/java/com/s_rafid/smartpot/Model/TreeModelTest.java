package com.s_rafid.smartpot.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeModelTest {
    TreeModel t1=new TreeModel("Tree1","xyzzxzxzxzxz","salimrafid26@gmail.com");
    @Test
    public void getName() {
        assertEquals("Tree1",t1.getName());
    }

    @Test
    public void getAuth() {
        assertEquals("xyzzxzxzxzxz",t1.getAuth());
    }

    @Test
    public void getEmail() {
        assertEquals("salimrafid26@gmail.com",t1.getEmail());
    }

    @Test
    public void isValid() {
        assertEquals(1,t1.isValid());
    }
}