package io.homeinventory.model;

import io.homeinventory.depreciation.AcceleratedDepreciationStrategy;
import io.homeinventory.enums.Category;

import java.time.LocalDate;

public class ElectronicItem extends Item {
    private String serialNumber;
    private LocalDate warrantyExpiryDate;

    public ElectronicItem() {
        this.depreciationStrategy = new AcceleratedDepreciationStrategy();
    }

    public ElectronicItem(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate, String serialNumber, LocalDate warrantyExpiryDate) {
        super(userId, roomId, name, description, category, estimatedValue, notes, purchaseDate);
        this.serialNumber = serialNumber;
        this.warrantyExpiryDate = warrantyExpiryDate;
        this.depreciationStrategy = new AcceleratedDepreciationStrategy();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getWarrantyExpiryDate() {
        return warrantyExpiryDate;
    }

    public void setWarrantyExpiryDate(LocalDate warrantyExpiryDate) {
        this.warrantyExpiryDate = warrantyExpiryDate;
    }

    public boolean isUnderWarranty() {
        return warrantyExpiryDate.isAfter(LocalDate.now());
    }

    @Override
    public double calculateDepreciatedValue() {
        return depreciationStrategy.calculate(estimatedValue, purchaseDate);
    }
}
