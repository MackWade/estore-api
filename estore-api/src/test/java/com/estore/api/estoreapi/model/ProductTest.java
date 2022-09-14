package com.estore.api.estoreapi.model;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.*;

/**
 * Tests the product class
 *
 * @author SWEN-261 Project Team One
 */
@Tag("Persistence-Tier")
class ProductTest {
    @Test
    void testGetName() {
        Product product = new Product("Product", 0, "Description", 5, 5);

        String expected = "Product";

        assertEquals(expected, product.getName());
    }

    @Test
    void testGetId() {
        Product product = new Product("Product", 0, "Description", 5, 5);

        int expected = 0;

        assertEquals(expected, product.getId());
    }

    @Test

    void testGetDesc() {
        Product product = new Product("Product", 0, "Description", 5, 5);

        String expected = "Description";

        assertEquals(expected, product.getDescription());
    }

    @Test
    void testGetQuantity() {
        Product product = new Product("Product", 0, "Description", 5, 5);

        int expected = 5;

        assertEquals(expected, product.getQuantity());
    }

    @Test
    void testSetName() {
        Product product = new Product("Product", 0, "Description", 5, 5);
        product.setName("Better Product");

        String expected = "Better Product";

        assertEquals(expected, product.getName());
    }

    @Test
    void testSetDesc() {
        Product product = new Product("Product", 0, "Description", 5, 5);
        product.setDescription("Newer Description");

        String expected = "Newer Description";

        assertEquals(expected, product.getDescription());
    }

    @Test
    void testGetPrice() {
        Product product = new Product("Product", 0, "Description", 13.37, 5);

        double expected = 13.37;

        assertEquals(expected, product.getPrice(), .001);
    }

    @Test
    void testSetPrice() {
        Product product = new Product("Product", 0, "Description", 13.37, 5);
        product.setPrice(0);

        double expected = 0;

        assertEquals(expected, product.getPrice(), .001);
    }

    @Test
    void testSetQuantity() {
        Product product = new Product("Product", 0, "Description", 5, 5);
        product.setQuantity(0);

        int expected = 0;

        assertEquals(expected, product.getQuantity());
    }

    @Test
    void testToString() {
        Product product = new Product("Product", 0, "Description", 5, 5);

        String expected = "0 - Product";

        assertEquals(expected, product.toString());
    }

    @Test
    void testEquals() {
        Product productOne = new Product("Product", 0, "Description", 5, 5);
        Product productTwo = new Product("Product", 1, "Description", 5, 5);

        assertEquals(productOne, productTwo);
    }

    @Test
    void testNotEquals() {
        Product productOne = new Product("Product", 0, "Description", 5, 5);

        assertNotEquals(productOne, new Object());
    }
}
