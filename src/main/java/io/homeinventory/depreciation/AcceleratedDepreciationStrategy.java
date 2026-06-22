package io.homeinventory.depreciation;

import io.homeinventory.interfaces.DepreciationStrategy;

import java.time.LocalDate;

// ElectronicItem — ~20% per year: value × (0.80 ^ yearsOwned)
public class AcceleratedDepreciationStrategy implements DepreciationStrategy {
    @Override
    public double calculate(double estimatedValue, LocalDate purchaseDate) {
        return 0; // TODO
    }
}
