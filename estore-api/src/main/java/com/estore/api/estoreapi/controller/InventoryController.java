package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles REST API requests for Product
 *
 * @author SWEN-261 Project Team One
 */
@RestController
@RequestMapping("inventory")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDAO;

    private static final String NOT_FOUND_MSG = "PRODUCT NOT FOUND";

    /**
     * Creates an Inventory Controller for handling requests
     *
     * @param inventoryDAO DAO for handling data persistence
     */
    public InventoryController(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }


    /**
     * Handles GET request for retrieving the entire inventory
     *
     * @return ResponseEntity with array of all products stored and HTTP Status: OK
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getInventory() {
        LOG.info("GET /inventory");

        Product[] inventory = inventoryDAO.getInventory();

        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }


    /**
     * Handles POST requests for creating a new product
     *
     * @param product New product to be created and stored
     * @return ResponseEntity with Product and HTTP Status: OK
     * ResponseEntity with HTTP Status: CONFLICT (Product already created)
     * ResponseEntity with HTTP Status: INTERNAL_SERVER_ERROR (IOException)
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.log(Level.INFO, "POST /inventory ", product);

        try {
            inventoryDAO.createProduct(product);
            LOG.info("Product created");

            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.severe("IOException: Could not create product");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (IllegalArgumentException e) {
            LOG.warning("IllegalArgumentException: Product has been already created");

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Put requests to update a product
     *
     * @param product: The product to be updated
     * @return: ResponseEntity with the product and HTTP Status of OK
     * ResponseEntity with Http Status of NOT FOUND
     * ResponseEntity with HTTP Status of INTERNAL SERVER ERROR
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.log(Level.INFO, "PUT /inventory ", product);

        try {
            Product newProduct = this.inventoryDAO.updateProduct(product);

            if (newProduct != null) {
                LOG.info("Product updated");

                return new ResponseEntity<>(newProduct, HttpStatus.OK);
            } else {
                LOG.severe("Product not Found");

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("INTERNAL_SERVER_ERROR");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET requests for getting a product
     *
     * @param id id will locate a product
     * @return ResponseEntity with Product and HTTP Status: OK
     * ResponseEntity with HTTP Status: NOT_FOUND
     * ResponseEntity with HTTP Status: INTERNAL_SERVER_ERROR (IOException)
     */

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.log(Level.INFO, "GET /inventory ", id);

        try {
            Product foundProduct = inventoryDAO.getProduct(id);
            if (foundProduct != null) {
                LOG.info("Product found");

                return new ResponseEntity<>(foundProduct, HttpStatus.OK);
            } else {
                LOG.info(NOT_FOUND_MSG);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("IOException: Could not find product");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles DELETE requests for deleting a product
     *
     * @param id: The id of the product to be deleted
     * @return: ResponseEntity with HTTP Status: OK
     * ResponseEntity with HTTP Status: NOT_FOUND
     * ResponseEntity with HTTp Status: INTERNAL_SERVER_ERROR
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.log(Level.INFO, "DELETE /inventory ", id);

        try {
            boolean isProductDeleted = inventoryDAO.deleteProduct(id);

            if (isProductDeleted) {
                LOG.info("Product deleted");

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                LOG.info(NOT_FOUND_MSG);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.severe("IOException");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET requests for searching a product
     *
     * @param keyword will find products that contain the text
     * @return ResponseEntity with an array of products and HTTP Status: OK
     * ResponseEntity with HTTP Status: NOT_FOUND
     * ResponseEntity with HTTP Status: INTERNAL_SERVER_ERROR (IOException)
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> findProduct(@RequestParam String keyword) {
        LOG.log(Level.INFO, "GET /inventory/?keyword=", keyword);

        try {
            Product[] product = inventoryDAO.findProduct(keyword);
            if (product.length != 0) {
                LOG.info("Product found");

                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                LOG.info(NOT_FOUND_MSG);

                return new ResponseEntity<>(new Product[0], HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.severe("INTERNAL_SERVER_ERROR");

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
