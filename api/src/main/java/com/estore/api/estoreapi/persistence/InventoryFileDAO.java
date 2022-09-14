package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.IgnoreJacocoGenerated;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Implementation of data persistence through JSON files
 *
 * @author SWEN-261 Project Team One
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    private Map<Integer, Product> inventory;
    private ObjectMapper objectMapper;
    private static int nextID;
    private String filename;
    
    private static final String PRODUCT_LOG = "PRODUCT: ";

    /**
     * Constructor for an Inventory File DAO
     *
     * @param objectMapper JSON object for serialization and deserialization
     * @param filename     Filename for reading and writing data
     * @throws IOException when file could not be accessed
     */
    public InventoryFileDAO(ObjectMapper objectMapper, @Value("${inventory.file}") String filename) throws IOException {
        this.objectMapper = objectMapper;
        this.filename = filename;
        this.inventory = new TreeMap<>();

        loadProducts();
    }


    /**
     * * {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {

        synchronized (inventory) {
            if (inventory.containsValue(product)) {
                throw new IllegalArgumentException("Product already created");
            }

            Product newProduct = new Product(product.getName(), getNextID(), product.getDescription(), product.getPrice(), product.getQuantity());

            inventory.put(newProduct.getId(), newProduct);
            LOG.info(PRODUCT_LOG + newProduct.getName() + " created with an ID of " + newProduct.getId());

            saveProducts();

            return newProduct;
        }

    }


    /**
     * * {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) throws IOException {
        synchronized (inventory) {
            if (inventory.containsKey(id)) {
                return inventory.get(id);
            }

            return null;
        }
    }


    /**
     * * {@inheritDoc}
     */
    @Override
    public Product[] getInventory() {
        LOG.info("Retrieving inventory list");

        return inventory.values().toArray(new Product[0]);
    }


    /**
     * * {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized (inventory) {
            if (!inventory.containsKey(product.getId())) {
                return null;
            }

            inventory.put(product.getId(), product);
            LOG.info(PRODUCT_LOG + product.getId() + " has been updated ");

            saveProducts();

            return product;
        }
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized (inventory) {
            if (!inventory.containsKey(id)) {
                return false;
            }

            inventory.remove(id);

            LOG.info(PRODUCT_LOG + id + " has been deleted");
            saveProducts();

            return true;
        }
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public Product[] findProduct(String keyword) {
        if (keyword == null) {
            LOG.warning("keyword variable was null");

            return new Product[0];
        }

        synchronized (inventory) {
            List<Product> productList = new ArrayList<>();

            for (Product product : inventory.values()) {
                if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    productList.add(product);
                }
            }

            return productList.toArray(new Product[productList.size()]);
        }
    }

    /**
     * Create an ID to be passed in for a new Product
     *
     * @return New Product ID
     */
    private synchronized static int getNextID() {
        return ++nextID;
    }

    /**
     * Saves all products into a JSON for data persistence
     *
     * @return true if file was saved successfully
     *
     * @throws IOException when file could not be accessed
     */
    private boolean saveProducts() throws IOException {
        Product[] productArr = inventory.values().toArray(new Product[0]);

        objectMapper.writeValue(new File(filename), productArr);
        LOG.info(productArr.length + " products saved to file.");

        return true;
    }

    /**
     * Instantiates a new inventory file and writes an empty array into it
     *
     * @param objectMapper
     * @param file
     * @throws IOException
     */
    @IgnoreJacocoGenerated
    private static void initializeFile(ObjectMapper objectMapper, File file) throws IOException {
        if (!file.createNewFile()) {
            throw new IOException();
        }

        objectMapper.writeValue(file, new Product[0]);
    }

    /**
     * Loads all products from a JSON into memory and adjusts ID accordingly
     *
     * @return true if file was loaded successfully
     *
     * @throws IOException when file could not be accessed
     */
    @IgnoreJacocoGenerated
    private boolean loadProducts() throws IOException {
        inventory = new TreeMap<>();
        nextID = -1;

        Product[] products = new Product[0];
        File file = new File(filename);

        try {
            products = objectMapper.readValue(new File(filename), Product[].class);
        } catch (MismatchedInputException e) {
            file.delete();

            initializeFile(objectMapper, file);
        } catch (FileNotFoundException e) {
            initializeFile(objectMapper, file);
        }

        for (Product product : products) {
            inventory.put(product.getId(), product);
            LOG.info("Loaded: " + product);

            if (product.getId() > nextID) {
                nextID = product.getId();
            }
        }

        return true;
    }
}
