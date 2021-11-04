package com.example.camerareader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class to manage product lists
 */
public class ProductList {

    private HashMap<Product, Integer> lista;

    /**
     * Class initializer
     */
    public ProductList() {
        lista = new HashMap<>();
    }

    /**
     * Add product to the list
     * @param p Product to add
     */
    public void addElement(Product p) {
        if (lista.containsKey(p))
            lista.put(p, lista.get(p) + 1);
        else lista.put(p, 1);
    }

    /**
     * Remove one element by product
     * @param p Product to delete
     */
    public void removeOneElement(Product p) {
        if (!lista.containsKey(p)) return;
        int quantity = lista.get(p);
        if (quantity > 1) lista.put(p, quantity - 1);
    }

    /**
     * Remove all elements by product
     * @param p Product to delete
     */
    public void removeAllElement(Product p) {
        if (!lista.containsKey(p)) return;
        lista.remove(p);
    }

    /**
     * Get a copy of the list of products
     * @return Copy of the product list
     */
    public Set<Product> getList() {
        return lista.keySet();
    }

    /**
     * Get list entryset
     * @return Entryset of the original list
     */
    public Set<Map.Entry<Product, Integer>> getEntryset() {
        return lista.entrySet();
    }

    /**
     * Get the total price of the elements in the list
     * @return Sum of all the prices
     */
    public double calculateTotal() {
        double total = 0;
        for (Product p: lista.keySet()) total += (p.getPrice() * lista.get(p));
        return total;
    }

    /**
     * Get the length of the list
     * @return Length of the list
     */
    public int getLength() {
        return lista.keySet().toArray().length;
    }

    /**
     * Return the total number of elements in the list
     * @return Number of elements in the list
     */
    public int getProductNumber() {
        int total = 0;
        for (Product p : lista.keySet()) {
            total += lista.get(p);
        }
        return total;
    }
}
