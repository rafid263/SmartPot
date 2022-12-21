package com.s_rafid.smartpot.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User u1=new User("salimrafid26@gmail.com","12345678");
    User u2=new User("","1223");
    User u3=new User("salimrafid26@gmail.com","1223");
    User u4=new User("salimrafid26gmail.com","12345678");
    User u5=new User("salimrafid26@gmail.com","");
    @Test
    public void getEmail() {
        assertEquals("salimrafid26@gmail.com",u1.getEmail());
    }

    @Test
    public void getPassword() {
        assertEquals("12345678",u1.getPassword());
    }

    @Test
    public void isValid() {
        assertEquals(-1,u1.isValid());
    }
    @Test
    public void isInvalidEmail() {
        assertEquals(0,u2.isValid());
    }
    @Test
    public void isInvalidPass() {
        assertEquals(3,u3.isValid());
    }
    @Test
    public void isInvalidEmailPat() {
        assertEquals(1,u4.isValid());
    }
    @Test
    public void isInvalidPassEmpty() {
        assertEquals(2,u5.isValid());
    }
}