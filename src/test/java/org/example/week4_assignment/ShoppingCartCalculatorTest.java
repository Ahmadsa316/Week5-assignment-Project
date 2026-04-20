package org.example.week4_assignment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartCalculatorTest {

    @Test
    void testCalculateItemCost() {
        double result = ShoppingCartCalculator.calculateItemCost(10.0, 3);
        assertEquals(30.0, result);
    }

    @Test
    void testAddToTotal() {
        double result = ShoppingCartCalculator.addToTotal(50.0, 25.0);
        assertEquals(75.0, result);
    }
}