package com.example.camerareader;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private final String _id;
    private final String name;
    private final double price;
    private final String img;
    private final int quantity;

    /**
     * Constructor of the class Product
     * @param product JSON Object from which extract data
     * @throws JSONException Exception thrown when the object is incorrect
     */
    public Product(@NonNull JSONObject product) throws JSONException{
        _id = product.getString("_id");
        name = product.getString("name");
        price = product.getDouble("price");
        img = product.getString("img");
        quantity = product.getInt("quantity");
    }

    /**
     * Get product id
     * @return String - id of the product
     */
    public String get_id() {
        return _id;
    }

    /**
     * Get product name
     * @return String - Name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Get product price
     * @return Double - Price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get product image path
     * @return String - Path to the img
     */
    public String getImg() {
        return img;
    }

    /**
     * Get product quantity
     * @return Int - Product Quantity
     */
    public int getQuantity() {
        return quantity;
    }
}
