package com.example.shopping.Model;

import java.util.ArrayList;

public class Cart {
    private static Cart INSTANCE = null;
    private ArrayList<CartItem> items;

    private Cart() {
        items = new ArrayList<>();
    }

    public static synchronized Cart getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Cart();
        }

        return INSTANCE;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public int calculateQuantity() {
        int sum = 0;
        for (CartItem item : items) {
            sum += item.getQuantity();
        }

        return sum;
    }

    public float calculateTotal() {
        float total = 0;
        for (CartItem item : items) {
            total += item.getMoney() * item.getQuantity();
        }

        return total;
    }

    public void addItem(CartItem item) {
        for (CartItem elem : items) {
            if (elem.getId() == item.getId()) return;
        }

        items.add(item);
    }

    public void deleteItem(int index) {
        items.remove(index);
    }

    public void deleteItem(CartItem item) {
        items.remove(item);
    }

    public int isInCart(CartItem item) {
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getId() == item.getId()) return i;
        }

        return -1;
    }

}
