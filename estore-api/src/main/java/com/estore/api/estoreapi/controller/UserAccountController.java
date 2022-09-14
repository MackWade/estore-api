package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.UserAccount;
import com.estore.api.estoreapi.persistence.UsersDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Handles REST API requests for UserAccount
 * 
 * @author SWEN-261 Project Team One
 */
@RestController
@RequestMapping("user")
public class UserAccountController {
    private static final Logger LOG = Logger.getLogger(UserAccountController.class.getName());
    private final UsersDAO usersDAO;
    private static final String USER_NOT_FOUND_MSG = "USER NOT FOUND";

    /**
     * Creates a user account controller for handling requests
     * 
     * @param usersDAO DAO for handling data persistence
     */
    public UserAccountController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    /**
     * Handles POST requests for creating a new user Account
     *
     * @param userAccount New user account to be created and stored
     * @return ResponseEntity with user account and HTTP Status: OK
     *         ResponseEntity with HTTP Status: CONFLICT (user account already
     *         created)
     *         ResponseEntity with HTTP Status: INTERNAL_SERVER_ERROR (IOException)
     */
    @PostMapping("")
    public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
        LOG.info("POST /user" + userAccount);

        try {
            usersDAO.createUserAccount(userAccount);
            LOG.info("User Account created");

            return new ResponseEntity<>(userAccount, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.severe("IOException: Could not create account");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (IllegalArgumentException e) {
            LOG.warning("IllegalArgumentException: Account has been already created");

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /***
     * Handles GET requests for getting a user Account
     * 
     * @param username the username of the user account is to be retrieved
     * 
     * @return ResponseEntity with the user account and HTTP Status: OK
     *         ResponseEntity with HTTP Status: NOT_FOUND
     *         ResponseEntity with HTTP Status: INTERNAL_SERVER_ERROR (IOException)
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable String username) {
        LOG.info("GET /user/ " + username);

        try {
            UserAccount foundUserAccount = usersDAO.getUserAccount(username);

            if (foundUserAccount != null) {
                LOG.info("User Account found");
                return new ResponseEntity<>(foundUserAccount, HttpStatus.OK);
            } else {
                LOG.info(USER_NOT_FOUND_MSG);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("IOException: Could not find User Account");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * Handles GET requests for verifying user account
     * 
     * @param username used to verify user login
     * @param password used to verify user login
     * 
     * @return ResponseEntity with a boolean and HTTP Status: OK
     *         ResponseEntity with HTTP Status: boolean and NOT_FOUND
     *         ResponseEntity with HTTP Status: INTERNAL_SERVER_ERROR (IOException)
     */
    @GetMapping("/{username}/{password}")
    public ResponseEntity<Boolean> logInUserAccount(@PathVariable String username, @PathVariable String password) {
        LOG.info("GET /user/ " + username + "/" + password);

        try {
            boolean verifiedUser = usersDAO.logInUserAccount(username, password);

            if (verifiedUser) {
                LOG.info("User Account verified");
                return new ResponseEntity<>(verifiedUser, HttpStatus.OK);
            } else {
                LOG.info("User Account not verified");
                return new ResponseEntity<>(verifiedUser, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("IOException: Could not find User Account");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Put requests to update a user account
     *
     * @param userAccount The user account to be updated
     * @return: ResponseEntity with the user account and HTTP Status of OK
     *          ResponseEntity with Http Status of NOT FOUND
     *          ResponseEntity with HTTP Status of INTERNAL SERVER ERROR
     */
    @PutMapping("")
    public ResponseEntity<UserAccount> updateUserAccount(@RequestBody UserAccount userAccount) {
        LOG.info("PUT /user ");

        try {
            UserAccount newUserAccount = this.usersDAO.updateUserAccount(userAccount);

            if (newUserAccount != null) {
                LOG.info("User Account updated");

                return new ResponseEntity<>(newUserAccount, HttpStatus.OK);
            } else {
                LOG.severe(USER_NOT_FOUND_MSG);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("INTERNAL_SERVER_ERROR");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles DELETE requests for deleting a user account
     *
     * @param username The id of the user account to be deleted
     * @return ResponseEntity with HTTP Status: OK
     *          ResponseEntity with HTTP Status: NOT_FOUND
     *          ResponseEntity with HTTp Status: INTERNAL_SERVER_ERROR
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<UserAccount> deleteUserAccount(@PathVariable String username) {
        LOG.info("DELETE /user/" + username);

        try {
            boolean isUserAccountDeleted = usersDAO.deleteUserAccount(username);

            if (isUserAccountDeleted) {
                LOG.info("User Account deleted");

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                LOG.info(USER_NOT_FOUND_MSG);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("IOException");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET requests for retrieving a user's cart
     *
     * @param username: The username of the user account to retrive cart
     * @return: ResponseEntity with HTTP Status: OK and cart
     *          ResponseEntity with HTTP Status: NOT_FOUND and cart
     *          ResponseEntity with HTTp Status: INTERNAL_SERVER_ERROR and cart 
     */
    @GetMapping("/cart/{username}")
    public ResponseEntity<Map<Integer, Integer>> getCart(@PathVariable String username) {
        LOG.info("GET/cart/" + username);
    try {
        Map<Integer, Integer> userCart = usersDAO.getCart(username);

        if (userCart != null) {
            LOG.info("User cart found");

            return new ResponseEntity<>(userCart, HttpStatus.OK);
        } else {
            LOG.info("User Cart not found");

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (IOException e) {
        LOG.severe("IOException");

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

     /**
     * Put requests to clear a user account
     *
     * @param username The user account's username to be updated
     * @return: ResponseEntity with the user account and HTTP Status of OK
     *          ResponseEntity with Http Status of NOT FOUND
     *          ResponseEntity with HTTP Status of INTERNAL SERVER ERROR
     */
    @GetMapping("/clear/{username}")
    public ResponseEntity<HttpStatus> clearCart(@PathVariable String username) {
        LOG.info("Get /user/clear ");

        try {
            if (username != null) {
                LOG.info("User Account clear");
                this.usersDAO.clearCart(username);
                return new ResponseEntity<>( HttpStatus.OK);
            } else {
                LOG.severe(USER_NOT_FOUND_MSG);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("INTERNAL_SERVER_ERROR");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
