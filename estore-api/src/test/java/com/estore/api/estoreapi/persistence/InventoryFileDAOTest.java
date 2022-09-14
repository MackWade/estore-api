package com.estore.api.estoreapi.persistence;


import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the InventoryFileDAO
 *
 * @author SWEN-261 Project Team One
 */
@Tag("Persistence-Tier")
class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] products;
    ObjectMapper mockObjMapper;

    @BeforeEach
    void setupInvFileDAO() throws IOException {
        mockObjMapper = mock(ObjectMapper.class);
        products = new Product[3];
        products[0] = new Product("Cool Product", 0, "It's cool", 0, 10);
        products[1] = new Product("Lame Product", 1, "It's lame", 0, 10);
        products[2] = new Product("Average Product", 2, "It's average", 0, 10);

        when(mockObjMapper.readValue(new File("InvFileTest.txt"), Product[].class)).thenReturn(products);
        inventoryFileDAO = new InventoryFileDAO(mockObjMapper, "InvFileTest.txt");
    }

    @Test
    void testCreateNewProduct() {
        Product product = new Product("New Product", 0, "It's new and improved!", 0, 10);

        Product actual = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product));

        assertNotNull(actual);
        assertEquals(product.getName(), actual.getName());
        assertEquals(product.getDescription(), actual.getDescription());
        assertEquals(product.getQuantity(), actual.getQuantity());
        assertEquals(3, actual.getId());
    }

    @Test
    void testCreateNewProductIllegal() {
        Product product = new Product("Cool Product", 0, "It's redundant!", 0, 10);

        try {
            inventoryFileDAO.createProduct(product);
            assert(false);
        } catch (IllegalArgumentException e) {
            assert(true);
        } catch (IOException e) {
            assert(false);
        }
    }

    @Test
    void testGetInventory() throws IOException {
        Product[] expected = products;
        Product[] actual = inventoryFileDAO.getInventory();

        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
        assertEquals(expected[2], actual[2]);
    }

    @Test
    void testUpdateProduct() {
        Product updatedProduct = new Product("Cooler Product", 0, "Updated description", 5, 10);
        Product newResult = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(updatedProduct));

        assertNotNull(newResult);
        assertEquals(updatedProduct.getName(), newResult.getName());
        assertEquals(updatedProduct.getDescription(), newResult.getDescription());
        assertEquals(updatedProduct.getQuantity(), newResult.getQuantity());
        assertEquals(0, newResult.getId());
    }

    @Test
    void testUpdateNullProduct() throws IOException {
        Product result = inventoryFileDAO.updateProduct(new Product("NonExistant", -99, "NULL", 0, 0));
        assertNull(result);
    }

    @Test
    void testGetProduct() {
        Product product = new Product("New Product", 3, "It's new and improved!", 0, 10);
        Product newProduct = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product));

        Product result = assertDoesNotThrow(() -> inventoryFileDAO.getProduct(3));

        assertNotNull(result);
        assertEquals(newProduct.getName(), result.getName());
        assertEquals(newProduct.getDescription(), result.getDescription());
        assertEquals(newProduct.getQuantity(), result.getQuantity());
        assertEquals(newProduct.getId(), result.getId());
    }

    @Test
    void testNullProduct() throws IOException {
        Product result = inventoryFileDAO.getProduct(-99);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        boolean isProductDeleted = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(0));

        assertTrue(isProductDeleted);
    }

    @Test
    void testDeleteNullProduct() {
        boolean isProductDeleted = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(-99));

        assertFalse(isProductDeleted);
    }

    @Test
    void testFindNullProduct() {
        Product[] expected = new Product[0];
        Product[] actual = inventoryFileDAO.findProduct(null);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testFindOneProduct() {
        Product[] expected = new Product[1];
        expected[0] = products[0];
        Product[] actual = inventoryFileDAO.findProduct("Cool");

        assertArrayEquals(expected, actual);
    }

    @Test
    void TestFindNoProducts() {
        Product[] expected = new Product[0];
        Product[] actual = inventoryFileDAO.findProduct("Zebra");

        assertArrayEquals(expected, actual);
    }

    @Test
    void testFindMultipleProducts() {
        Product[] expected = products;
        Product[] actual = inventoryFileDAO.findProduct("Product");

        assertArrayEquals(expected, actual);
    }
}
