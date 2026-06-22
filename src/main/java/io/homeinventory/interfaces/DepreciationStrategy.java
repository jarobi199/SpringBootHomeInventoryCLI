package io.homeinventory.interfaces;

import java.time.LocalDate;

public interface DepreciationStrategy {
    double calculate(double estimatedValue, LocalDate purchaseDate);
}
