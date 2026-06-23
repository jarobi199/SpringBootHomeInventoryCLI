package io.homeinventory.depreciation;

import io.homeinventory.interfaces.DepreciationStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LinearDepreciationStrategy implements DepreciationStrategy {
    @Override
    public double calculate(double estimatedValue, LocalDate purchaseDate) {
        return estimatedValue * Math.max(0.2, 1 - 0.05 * ChronoUnit.YEARS.between(purchaseDate, LocalDate.now()));
    }
}
