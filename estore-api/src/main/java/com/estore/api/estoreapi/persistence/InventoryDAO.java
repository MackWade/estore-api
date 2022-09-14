package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Product;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Interface for Product object persistence
 *
 * @author SWEN-261 Project Team One
 */
@Component
public interface InventoryDAO {
    /**
     * Creates and saves a Product object
     *
     * @param product Product being created
     * @return Product passed in if creation is successful
     *
     * @throws IOException when file could not be accessed
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Finds a product
     *
     * @param id ID is being searched
     * @return Product passed in if ID is found
     *
     * @throws IOException when file could not be accessed
     */
    Product getProduct(int id) throws IOException;


    /**
     * Retrieves a list of all products stored
     *
     * @return Array of products stored
     */
    Product[] getInventory();

    /**
     * Updates a Product
     *
     * @param product: the product being updated
     * @throws IOException: if the file can't be accessed
     * @return: successfully updated if the operation was successful failed otherwise
     */
    Product updateProduct(Product product) throws IOException;
    
    /**
     * Deletes a product
     *
     * @param id: the product ID to be deleted
     * @throws IOException: if the file can't be accessed
     * @return: true if a product was deleted, false otherwise
     */
    boolean deleteProduct(int id) throws IOException;

    /**
     * Searches for a Product
     *
     * @param keyword: keyword is being searched
     * @throws IOException: if the file can't be accessed
     * @return: An array of products that contain the keyword
     */
    Product[] findProduct(String keyword) throws IOException;
}
