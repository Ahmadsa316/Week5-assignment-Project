package org.example.week4_assignment.model;

public class CartRecord {

    private final int id;
    private final int totalItems;
    private final double totalCost;
    private final String language;

    public CartRecord(int id, int totalItems, double totalCost, String language) {
        this.id = id;
        this.totalItems = totalItems;
        this.totalCost = totalCost;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getLanguage() {
        return language;
    }
}