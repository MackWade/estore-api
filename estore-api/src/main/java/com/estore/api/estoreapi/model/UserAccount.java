package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.IgnoreJacocoGenerated;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * This class represents a User Account
 */
public class UserAccount {
    @JsonProperty("firstName") private String firstName;
    @JsonProperty("lastName") private String lastName;
    @JsonProperty("userName") private String userName;
    @JsonProperty("password") private String password;
    @JsonProperty("cart") private Map<Integer, Integer> cart;
    

   /***
    * Constructor for a user account 
    *
    * @param firstName The first name of the user
    * @param lastName The last name of the user
    * @param userName The user name of the user
    * @param userId The user's user id
    */
    public UserAccount(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("userName") String userName,  @JsonProperty("password") String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.cart = new HashMap<>();
    }

    /***
     * setter for the first Name of the user
     * 
     * @param firstName: The new first name of the user
     */
    @IgnoreJacocoGenerated
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }  

    /**
     * Getter for the first Name of the user
     * 
     * @return: The first name of the user
     */
    @IgnoreJacocoGenerated
    public String getFirstName() {
        return this.firstName; 
    }

    /**
     * setter for the last name of the user
     * 
     * @param lastName: The new last name of the user
     */
    @IgnoreJacocoGenerated
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /***
     * getter for the last name of the user
     * 
     * @return The user last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /***
     * getter for the username
     * 
     * @return the username
     */
    public String getUserName() {
        return this.userName;
    }

    /***
     * setter for the username
     * 
     * @param userName The new username preferred by the user
     */
    @IgnoreJacocoGenerated
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /***
     * setter for the password
     * 
     * @param password The new password entered by the user 
     */
    @IgnoreJacocoGenerated
    public void setPassword(String password) {
        this.password = password;
    }

    /***
     * Getter for the password
     * 
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter for cart
     *
     * @return user cart
     */
    public Map<Integer, Integer> getCart() {
        return this.cart;
    }

    /**
     * Clear cart
     *
     * @return user cart
     */
    public void clearCart() {
        this.cart = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @IgnoreJacocoGenerated
    public boolean equals(Object obj) {
        if (obj instanceof UserAccount) {
            UserAccount other = (UserAccount) obj;

            return this.userName.equals(other.userName);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @IgnoreJacocoGenerated
    public String toString() {
        return firstName + lastName + "#" + userName;
    }
}
