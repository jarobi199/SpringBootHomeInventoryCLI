package io.homeinventory.depreciation;

import io.homeinventory.interfaces.DepreciationStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AcceleratedDepreciationStrategy implements DepreciationStrategy {
    @Override
    public double calculate(double estimatedValue, LocalDate purchaseDate) {
        return estimatedValue * Math.pow(0.8, ChronoUnit.YEARS.between(purchaseDate, LocalDate.now()));
    }
}
