package io.homeinventory.model;

import io.homeinventory.depreciation.ModerateDepreciationStrategy;
import io.homeinventory.enums.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplianceItem extends Item {
    private int modelNumber;
    private List<ServiceRecord> serviceHistory;

    public ApplianceItem() {
        this.depreciationStrategy = new ModerateDepreciationStrategy();
        this.serviceHistory = new ArrayList<ServiceRecord>();
    }

    public ApplianceItem(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate, int modelNumber, List<ServiceRecord> serviceHistory) {
        super(userId, roomId, name, description, category, estimatedValue, notes, purchaseDate);
        this.modelNumber = modelNumber;
        this.serviceHistory = serviceHistory;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(int modelNumber) {
        this.modelNumber = modelNumber;
    }

    public List<ServiceRecord> getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(List<ServiceRecord> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public void addServiceRecord(ServiceRecord record) {
        serviceHistory.add(record);
    }

    @Override
    public double calculateDepreciatedValue() {
        return depreciationStrategy.calculate(estimatedValue, purchaseDate);
    }
}
