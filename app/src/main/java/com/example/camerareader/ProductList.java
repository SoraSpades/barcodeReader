package com.example.camerareader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class to manage product lists
 */
public class ProductList {

    private ArrayList<Product> lista;

    /**
     * Class initializer
     */
    public ProductList() {
        lista = new ArrayList<>();
    }

    /**
     * Add product to the list
     * @param p Product to add
     */
    public void addElement(Product p) {
        lista.add(p);
    }

    /**
     * Remove element by index
     * @param i index to delete
     */
    public void removeElement(int i) {
        lista.remove(i);
    }

    /**
     * Get a copy of the list of products
     * @return Copy of the product list
     */
    public ArrayList<Product> getList() {
        return new ArrayList<>(lista);
    }

    /**
     * Get i element of the list
     * @param i Index of the element to get
     * @return i-th Product of the list
     */
    public Product getElement(int i) {
        return lista.get(i);
    }

    /**
     * Get list iterator
     * @return Iterator of the original list
     */
    public Iterator<Product> getIterator() {
        return lista.iterator();
    }

    /**
     * Get the total price of the elements in the list
     * @return Sum of all the prices
     */
    public double calculateTotal() {
        double total = 0;
        for (Product p: lista) total += p.getPrice();
        return total;
    }
}
