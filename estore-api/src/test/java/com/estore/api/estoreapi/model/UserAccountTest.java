package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Persistence-Tier")
class UserAccountTest {
    @Test
    void testGetFirstName() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");

        assertEquals("John", user.getFirstName());
    }

    @Test
    void testGetLastName() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");

        assertEquals("Doe", user.getLastName());
    }

    @Test
    void testGetUserName() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");

        assertEquals("JD11", user.getUserName());
    }

    @Test
    void testGetPassword() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");

        assertEquals("11DJ", user.getPassword());
    }

    @Test
    void testGetCart() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");

        assertEquals(new HashMap<>(), user.getCart());
    }

    @Test
    void testSetFirstName() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");
        user.setFirstName("Jane");

        assertEquals("Jane", user.getFirstName());
    }

    @Test
    void testSetLastName() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");
        user.setLastName("Eod");

        assertEquals("Eod", user.getLastName());
    }

    @Test
    void testSetUserName() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");
        user.setUserName("JaneD03");

        assertEquals("JaneD03", user.getUserName());
    }

    @Test
    void testSetPassword() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");
        user.setPassword("Anon");

        assertEquals("Anon", user.getPassword());
    }

    @Test
    void testClearCart() {
        UserAccount user = new UserAccount("John", "Doe", "JD11", "11DJ");
        user.clearCart();

        assertEquals(new HashMap<>(), user.getCart());
    }
}
