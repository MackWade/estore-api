package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.IgnoreJacocoGenerated;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a Product entity as a part of an Inventory in an E-Store
 *
 * @author SWEN-261 Project Team One
 */
public class Product {
    @JsonProperty("name") private String name;
    @JsonProperty("id") private final int id;
    @JsonProperty("description") private String description;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;

    /**
     * Constructor for a product
     *
     * @param name        Name of the product
     * @param id          ID of the product
     * @param description Description of the product
     * @param quantity    Quantity of the product
     */
    public Product(@JsonProperty("name") String name, @JsonProperty("id") int id, @JsonProperty("description") String description, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Getter for the product name
     *
     * @return Product Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the product name
     *
     * @param name The new name for a product
     */
    @IgnoreJacocoGenerated
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the product ID
     *
     * @return Product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the product description
     *
     * @return Product Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the product description
     *
     * @param description The new description for a product
     */
    @IgnoreJacocoGenerated

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the product price
     *
     * @return Product Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for the product price
     *
     * @param price The new price for a product
     */
    @IgnoreJacocoGenerated
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for the product quantity
     *
     * @return Product Quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter for the product quantity
     *
     * @param quantity The new quantity of the product
     */
    @IgnoreJacocoGenerated
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @IgnoreJacocoGenerated
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product other = (Product) obj;

            return name.equals(other.getName());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @IgnoreJacocoGenerated
    public String toString() {
        return id + " - " + name;
    }
}
