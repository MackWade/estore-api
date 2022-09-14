package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.UserAccount;
import com.estore.api.estoreapi.persistence.UsersFileDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("Controller-Tier")
class UserAccControllerTest {
    private UserAccountController userAccountController;
    private UsersFileDAO mockUsersDAO;

    @BeforeEach
    void setup() {
        mockUsersDAO = mock(UsersFileDAO.class);
        userAccountController = new UserAccountController(mockUsersDAO);
    }

    @Test
    void testCreateUser() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "hi");

        when(mockUsersDAO.createUserAccount(user)).thenReturn(user);

        ResponseEntity<UserAccount> response = userAccountController.createUserAccount(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testCreateUserConflict() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        doThrow(new IllegalArgumentException()).when(mockUsersDAO).createUserAccount(user);

        ResponseEntity<UserAccount> response = userAccountController.createUserAccount(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testCreateUserIOException() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        doThrow(new IOException()).when(mockUsersDAO).createUserAccount(user);

        ResponseEntity<UserAccount> response = userAccountController.createUserAccount(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetUser() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        when(mockUsersDAO.getUserAccount("JD1")).thenReturn(user);

        ResponseEntity<UserAccount> response = userAccountController.getUserAccount("JD1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserUnavailable() throws IOException {
        ResponseEntity<UserAccount> response = userAccountController.getUserAccount("JD1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserIOExcept() throws IOException {
        doThrow(new IOException()).when(mockUsersDAO).getUserAccount("JD1");

        ResponseEntity<UserAccount> response = userAccountController.getUserAccount("JD1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdateUser() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        when(mockUsersDAO.updateUserAccount(user)).thenReturn(user);

        ResponseEntity<UserAccount> response = userAccountController.updateUserAccount(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testUpdateUserUnavailable() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        when(mockUsersDAO.updateUserAccount(user)).thenReturn(null);

        ResponseEntity<UserAccount> response = userAccountController.updateUserAccount(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateUserIOExcept() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        doThrow(new IOException()).when(mockUsersDAO).updateUserAccount(user);

        ResponseEntity<UserAccount> response = userAccountController.updateUserAccount(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteUser() throws IOException {
        when(mockUsersDAO.deleteUserAccount("JD1")).thenReturn(true);

        ResponseEntity<UserAccount> response = userAccountController.deleteUserAccount("JD1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteUserUnavailable() throws IOException {
        when(mockUsersDAO.deleteUserAccount("JD1")).thenReturn(false);

        ResponseEntity<UserAccount> response = userAccountController.deleteUserAccount("JD1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void testDeleteUserIOExcept() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        doThrow(new IOException()).when(mockUsersDAO).deleteUserAccount("JD1");

        ResponseEntity<UserAccount> response = userAccountController.deleteUserAccount("JD1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void testLogInUserAccount() throws IOException {
        when(mockUsersDAO.logInUserAccount("JD1", "password")).thenReturn(true);

        ResponseEntity<Boolean> response = userAccountController.logInUserAccount("JD1", "password");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLogInUserAccountInvalid() throws IOException {
        when(mockUsersDAO.logInUserAccount("JD1", "passwords")).thenReturn(false);

        ResponseEntity<Boolean> response = userAccountController.logInUserAccount("JD1", "passwords");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testLogInUserAccountIOExcept() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        doThrow(new IOException()).when(mockUsersDAO).logInUserAccount("JD1", "passwords");

        ResponseEntity<Boolean> response = userAccountController.logInUserAccount("JD1", "passwords");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetCart() throws IOException {
        when(mockUsersDAO.getCart("JD1")).thenReturn(new HashMap<Integer, Integer>());

        ResponseEntity<Map<Integer, Integer>> response = userAccountController.getCart("JD1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new HashMap<Integer, Integer>(), response.getBody());
    }

    @Test
    void testGetCartNotFound() throws IOException {
        when(mockUsersDAO.getCart("JD1")).thenReturn(null);

        ResponseEntity<Map<Integer, Integer>> response = userAccountController.getCart("JD1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetCartIOException() throws IOException {
        doThrow(new IOException()).when(mockUsersDAO).getCart("JD1");

        ResponseEntity<Map<Integer, Integer>> response = userAccountController.getCart("JD1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testClearCart() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        mockUsersDAO.clearCart(user.getUserName());

        ResponseEntity<HttpStatus> response = userAccountController.clearCart(user.getUserName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testClearCartNotFound() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        ResponseEntity<HttpStatus> response = userAccountController.clearCart(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testClearCartIOExcept() throws IOException {
        UserAccount user = new UserAccount("John", "Doe", "JD1", "password");

        doThrow(new IOException()).when(mockUsersDAO).clearCart("JD1");

        ResponseEntity<HttpStatus> response = userAccountController.clearCart("JD1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
