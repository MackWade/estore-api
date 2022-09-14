package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.IgnoreJacocoGenerated;
import com.estore.api.estoreapi.model.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.HashMap;


@Component
public class UsersFileDAO implements UsersDAO {
    private static final Logger LOG = Logger.getLogger(UsersFileDAO.class.getName());
    private HashMap<String, UserAccount> users;
    private ObjectMapper objectMapper;
    private static int nextID;
    private String filename;
    
    private static final String ADMIN_USERNAME = "admin";
    private static final String USER_ACC_LOG = "UserAccount: ";

    /***
     * Constructor for a UserAccount File DAO
     * 
     * @param objectMapper JSON object for serialization and deserialization
     * @param filename Filename for serialization and deserialization
     * 
     * @throws IOException if the file cannot be accessed
     */
    public UsersFileDAO(ObjectMapper objectMapper, @Value("${users.file}") String filename) throws IOException {
        this.objectMapper = objectMapper;
        this.filename = filename;
        this.users = new HashMap<>();

        loadUsers();
    }

    /***
     * Instantiates a new users file and writes an empty array into it
     * 
     * @param objectMapper
     * @param file
     * @throws IOException
     */
    @IgnoreJacocoGenerated
    private static void initializeFile(ObjectMapper objectMapper, File file) throws IOException {
        file.createNewFile();

        objectMapper.writeValue(file, new UserAccount[0]);
    }

    /***
     * Loads all accounts from the JSON into memory and adjusts the user id accordingly
     * 
     * @return true if the file was successfully loaded
     * 
     * @throws IOException if the file cannot be accessed
     */
    @IgnoreJacocoGenerated
    private boolean loadUsers() throws IOException {
        users = new HashMap<>();

        UserAccount[] userAccounts = new UserAccount[0];
        File file = new File(filename);

        try {
            userAccounts = objectMapper.readValue(new File(filename), UserAccount[].class);
        } catch (MismatchedInputException e) {
            file.delete();

            initializeFile(objectMapper, file);
        } catch (FileNotFoundException e) {
            initializeFile(objectMapper, file);
        }

        for (UserAccount userAccount : userAccounts) {
            users.put(userAccount.getUserName(), userAccount);
            LOG.info("Loaded: " + userAccount);
        }

        if (!users.containsKey(ADMIN_USERNAME)) {
            UserAccount admin = new UserAccount(ADMIN_USERNAME, "", ADMIN_USERNAME, ADMIN_USERNAME);
            createUserAccount(admin);
        }

        return true;
    } 

    /***
     * Saves all users into a JSON 
     * 
     * @return true if the file was saved successfully
     * 
     * @throws IOException if the file cannot be accessed
     */
    private boolean saveUsers() throws IOException {
        UserAccount[] userArr = users.values().toArray(new UserAccount[0]);

        objectMapper.writeValue(new File(filename), userArr);
        LOG.info(userArr.length + " users saved to file");

        return true;
    }

    /***
     * {@inheritDoc}
     */
    @Override
    public UserAccount createUserAccount(UserAccount userAccount) throws IOException {
        synchronized (users) {
            if (users.containsValue(userAccount)) {
                throw new IllegalArgumentException("user already exists");
            }

            UserAccount newUserAccount = new UserAccount(userAccount.getFirstName(), userAccount.getLastName(), userAccount.getUserName(),userAccount.getPassword());

            users.put(newUserAccount.getUserName(), newUserAccount);
            LOG.info(USER_ACC_LOG + newUserAccount.getUserName() + " created ");

            saveUsers();

            return newUserAccount;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccount getUserAccount(String username) throws IOException {
        synchronized (users) {
            if (users.containsKey(username)) {
                return users.get(username);
            }

            return null;
        }     
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) throws IOException {
        synchronized (users) {
            if (!users.containsKey(userAccount.getUserName())) {
                return null;
            }

            users.put(userAccount.getUserName(), userAccount);
            LOG.info(USER_ACC_LOG + userAccount.getUserName() + " has been updated");

            saveUsers();

            return userAccount;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUserAccount(String username) throws IOException {
        synchronized (users) {
            if (!users.containsKey(username)) {
                return false;
            }

            users.remove(username);
            LOG.info(USER_ACC_LOG + username + "has been deleted");

            saveUsers();

            return true;
        }
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public boolean logInUserAccount(String username, String password) throws IOException{
        synchronized (users) {
            UserAccount user = getUserAccount(username);

            if (user == null) {
                return false;
            }

            return user.getUserName().equals(username) && user.getPassword().equals(password);
        }  
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Integer> getCart(String username) throws IOException{
        synchronized (users) {
            UserAccount user = getUserAccount(username);

            if (user == null) {
                return null;
            }

            return user.getCart();
        }  
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public void clearCart(String username) throws IOException {
        synchronized (users) {
            UserAccount user = getUserAccount(username);

            user.clearCart();
            saveUsers();
        }  
    }
}
