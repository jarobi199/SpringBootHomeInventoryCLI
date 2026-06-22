package io.homeinventory.depreciation;

import io.homeinventory.interfaces.DepreciationStrategy;

import java.time.LocalDate;

// ApplianceItem — ~10% per year: value × max(0.10, 1 − 0.10 × yearsOwned)
public class ModerateDepreciationStrategy implements DepreciationStrategy {
    @Override
    public double calculate(double estimatedValue, LocalDate purchaseDate) {
        return 0; // TODO
    }
}
