package io.homeinventory.depreciation;

import io.homeinventory.interfaces.DepreciationStrategy;

import java.time.LocalDate;

// FurnitureItem — ~5% per year: value × max(0.20, 1 − 0.05 × yearsOwned)
public class LinearDepreciationStrategy implements DepreciationStrategy {
    @Override
    public double calculate(double estimatedValue, LocalDate purchaseDate) {
        return 0; // TODO
    }
}
