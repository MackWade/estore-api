package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.UserAccount;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/***
 * Interface for user account
 * 
 * @author SWEN-261 Project Team One
 */
@Component
public interface UsersDAO {

    /***
     * Creates and saves a user account
     * 
     * @param userAccount The user account being created
     * @return User Account if successfully created
     * 
     * @throws IOException if file cannot be accessed
     */
    UserAccount createUserAccount(UserAccount userAccount) throws IOException;

    /***
     * Retrieves a User Account
     * 
     * @param username The username of the Account to be retrieved
     * @return The user Account if the id passed in is found
     * 
     * @throws IOException if file cannot be accessed
     */
    UserAccount getUserAccount(String username) throws IOException;

    /***
     * Updates a User Account
     * 
     * @param userAccount The userAccount to be updated
     * @return The updated user Account
     * 
     * @throws IOException if the file cannot be accessed
     */
    UserAccount updateUserAccount(UserAccount userAccount) throws IOException;

    /***
     * Deletes a user Id
     * 
     * @param username The username of the account to be deleted
     * @return true if the account is successfully deleted, false otherwise
     * 
     * @throws IOException if the file cannot be accessed
     */
    boolean deleteUserAccount(String username) throws IOException;
    
    /***
     * logs in to user account
     * 
     * @param username The username of the account 
     * @param password The password of the account
     * @return true if the account information is correct, false otherwise
     * 
     * @throws IOException if the file cannot be accessed
     */
    boolean logInUserAccount(String username, String password) throws IOException;

    /***
     * returns cart from user account
     * 
     * @param username The username of the account 
     * @return true if the account information is correct, false otherwise
     * 
     * @throws IOException if the file cannot be accessed
     */
    Map<Integer, Integer> getCart(String username) throws IOException;

    /***
     * clears cart from user account
     * 
     * @throws IOException if the file cannot be accessed
     */
    void clearCart(String username) throws IOException;
}
