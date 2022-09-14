package com.estore.api.estoreapi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Tests the Inventory Controller
 *
 * @author SWEN-261 Project Team One
 */
@Tag("Controller-Tier")
class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO mockInventoryDAO;

    @BeforeEach
    void setupInventoryController() {
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }

    @Test
    void testCreateProduct() throws IOException {
        Product product = new Product("Product", 0, "A cool, new product", 5, 10);

        when(mockInventoryDAO.createProduct(product)).thenReturn(product);

        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testCreateProductConflict() throws IOException {
        Product product = new Product("Product", 1, "A lame, copy product", 5, 10);

        doThrow(new IllegalArgumentException()).when(mockInventoryDAO).createProduct(product);

        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testCreateProductHandleIOException() throws IOException {
        Product product = new Product("Product", 1, "A lame, copy product", 5, 10);

        doThrow(new IOException()).when(mockInventoryDAO).createProduct(product);

        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetProductEmpty() {
        Product[] inventory = {};

        when(mockInventoryDAO.getInventory()).thenReturn(inventory);

        ResponseEntity<Product[]> response = inventoryController.getInventory();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventory, response.getBody());
    }

    @Test
    void testGetProductOne() {
        Product[] inventory = {new Product("Product", 0, "A product.", 0, 10)};

        when(mockInventoryDAO.getInventory()).thenReturn(inventory);

        ResponseEntity<Product[]> response = inventoryController.getInventory();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventory, response.getBody());
    }

    @Test
    void testGetProductMulti() {
        Product[] inventory = {
                new Product("Product", 0, "A product.", 0, 10),
                new Product("Another Product", 1, "Electric Boogaloo", 2, 10),
                new Product("A Third Product?!?!", 2, "It's getting out of hand", 1, 10),
        };

        when(mockInventoryDAO.getInventory()).thenReturn(inventory);

        ResponseEntity<Product[]> response = inventoryController.getInventory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(inventory, response.getBody());
    }

    @Test
    void testUpdateProduct() throws IOException {
        Product updatedProduct = new Product("oof", 1, "product new", 10, 10);

        when(mockInventoryDAO.updateProduct(updatedProduct)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = inventoryController.updateProduct(updatedProduct);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    void testUpdateProductProductNotAvailable() throws IOException {
        Product updatedProduct = new Product("oof", 3, "product new", 10, 10);

        when(mockInventoryDAO.updateProduct(updatedProduct)).thenReturn(null);

        ResponseEntity<Product> response = inventoryController.updateProduct(updatedProduct);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateProductIOException() throws IOException {
        Product updatedProduct = new Product("oof", 3, "product new", 10, 10);

        doThrow(new IOException()).when(mockInventoryDAO).updateProduct(updatedProduct);


        ResponseEntity<Product> response = inventoryController.updateProduct(updatedProduct);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetProduct() throws IOException {
        Product product = new Product("New Product", 3, "It's new and improved!", 0, 10);

        when(mockInventoryDAO.getProduct(3)).thenReturn(product);

        ResponseEntity<Product> response = inventoryController.getProduct(3);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductNotFound() throws IOException {
        ResponseEntity<Product> response = inventoryController.getProduct(66);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetProductHandleIOException() throws IOException {
        doThrow(new IOException()).when(mockInventoryDAO).getProduct(4);

        ResponseEntity<Product> response = inventoryController.getProduct(4);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteProduct() throws IOException {
        when(mockInventoryDAO.deleteProduct(3)).thenReturn(true);

        ResponseEntity<Product> response = inventoryController.deleteProduct(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteProductNotFound() throws IOException {
        ResponseEntity<Product> response = inventoryController.deleteProduct(7);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindOneProduct() throws IOException {
        Product[] expectedArray = {new Product("Product2", 1, "Electric Boogaloo", 2, 10)};

        when(mockInventoryDAO.findProduct("Product2")).thenReturn(expectedArray);

        ResponseEntity<Product[]> response = inventoryController.findProduct("Product2");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedArray, response.getBody());
    }

    @Test
    void testFindMultipleProduct() throws IOException {
        Product[] inventory = {
                new Product("Product", 0, "A product.", 0, 10),
                new Product("Another Product", 1, "Electric Boogaloo", 2, 10),
                new Product("A Third Product?!?!", 2, "It's getting out of hand", 1, 10),
        };

        when(mockInventoryDAO.findProduct("Product")).thenReturn(inventory);

        ResponseEntity<Product[]> response = inventoryController.findProduct("Product");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventory, response.getBody());
    }

    @Test
    void testProductNotFound() throws IOException {
        when(mockInventoryDAO.findProduct("Zebra")).thenReturn(new Product[0]);

        ResponseEntity<Product[]> response = inventoryController.findProduct("Zebra");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteProductHandleIOException() throws IOException {
        doThrow(new IOException()).when(mockInventoryDAO).deleteProduct(5);

        ResponseEntity<Product> response = inventoryController.deleteProduct(5);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFindProductHandleIOException() throws IOException {
        doThrow(new IOException()).when(mockInventoryDAO).findProduct("Zebra");

        ResponseEntity<Product[]> response = inventoryController.findProduct("Zebra");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
