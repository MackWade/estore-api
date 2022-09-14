package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Persistence-Tier")
class UserAccFileDAOTest {
    UsersFileDAO usersFileDAO;
    ObjectMapper mockObjMapper;
    UserAccount[] users;

    @BeforeEach
    void setupDAO() throws IOException {
        mockObjMapper = mock(ObjectMapper.class);
        users = new UserAccount[1];
        users[0] = new UserAccount("Jane", "Doe", "JD0", "password");


        when(mockObjMapper.readValue(new File("UserFileTest.txt"), UserAccount[].class)).thenReturn(users);
        usersFileDAO = new UsersFileDAO(mockObjMapper, "UserFileTest.txt");
    }

    @Test
    void testCreateUser() {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");
        UserAccount actual = assertDoesNotThrow(() -> usersFileDAO.createUserAccount(user));

        assertNotNull(actual);
        assertEquals(user.getUserName(), actual.getUserName());
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(user.getCart(), actual.getCart());
    }

    @Test
    void testCreateUserIllegal() {
        UserAccount user = new UserAccount("John", "Doe", "JD0", "password");

        try {
            usersFileDAO.createUserAccount(user);
            assert (false);
        } catch (IllegalArgumentException e) {
            assert (true);
        } catch (IOException e) {
            assert (false);
        }
    }

    @Test
    void testGetUser() throws IOException {
        UserAccount expected = users[0];
        UserAccount actual = usersFileDAO.getUserAccount("JD0");

        assertNotNull(actual);
        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getCart(), actual.getCart());
    }

    @Test
    void testGetUserUnavailable() throws IOException {
        UserAccount result = usersFileDAO.getUserAccount("Eod");

        assertNull(result);
    }

    @Test
    void testUpdateUser() throws IOException {
        UserAccount updated = new UserAccount("John", "Eode", "JD0", "better password");

        UserAccount result = usersFileDAO.updateUserAccount(updated);

        assertNotNull(result);
        assertEquals(updated.getUserName(), result.getUserName());
        assertEquals(updated.getFirstName(), result.getFirstName());
        assertEquals(updated.getLastName(), result.getLastName());
        assertEquals(updated.getPassword(), result.getPassword());
        assertEquals(updated.getCart(), result.getCart());
    }

    @Test
    void testUpdateUserUnavailable() throws IOException {
        UserAccount result = usersFileDAO.updateUserAccount(new UserAccount("", "", "", ""));

        assertNull(result);
    }

    @Test
    void testDeleteUser() {
        boolean isProductDeleted = assertDoesNotThrow(() -> usersFileDAO.deleteUserAccount("JD0"));

        assertTrue(isProductDeleted);
    }

    @Test
    void testDeleteUserUnavailable() {
        boolean isDeleted = assertDoesNotThrow(() -> usersFileDAO.deleteUserAccount("Eod"));

        assertFalse(isDeleted);
    }

    @Test
    void testLoginUser() {
        boolean isVerified = assertDoesNotThrow(() -> usersFileDAO.logInUserAccount("JD0", "password"));

        assertTrue(isVerified);
    }

    @Test
    void testLoginUserFailedBadPass() {
        boolean isVerified = assertDoesNotThrow(() -> usersFileDAO.logInUserAccount("JD0", "passwordz"));

        assertFalse(isVerified);
    }

    @Test
    void testLoginUserFailedBadUsername() {
        boolean isVerified = assertDoesNotThrow(() -> usersFileDAO.logInUserAccount("bad", "password"));

        assertFalse(isVerified);
    }
    @Test
    void testLoginUserFailedBadPassUsername() {
        boolean isVerified = assertDoesNotThrow(() -> usersFileDAO.logInUserAccount("bad", "passwordz"));

        assertFalse(isVerified);
    }


    @Test
    void testClearCart() throws IOException {
        usersFileDAO.clearCart(users[0].getUserName());
        Map<Integer, Integer> actual = users[0].getCart();

        assertEquals(new HashMap<>(), actual);
    }

    @Test
    void testGetCart() throws IOException {
        Map<Integer, Integer> actual = users[0].getCart();
        Map<Integer, Integer> result = usersFileDAO.getCart(users[0].getUserName());

        assertEquals(actual, result);
    }

    @Test
    void testGetCartUserNull() throws IOException {
        assertNull(usersFileDAO.getCart(null));
    }
}
