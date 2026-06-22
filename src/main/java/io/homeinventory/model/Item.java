package io.homeinventory.model;

import io.homeinventory.enums.Category;
import io.homeinventory.interfaces.DepreciationStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "items")
public abstract class Item {
    @Id
    protected String id;
    protected String userId;
    protected String roomId;
    protected String name;
    protected String description;
    protected Category category;
    protected double estimatedValue;
    protected LocalDate purchaseDate;
    protected String notes;
    @Transient
    protected DepreciationStrategy depreciationStrategy;

    public Item() {
        //No argument constructor
    }

    public Item(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate) {
        this.userId = userId;
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.estimatedValue = estimatedValue;
        this.notes = notes;
        this.purchaseDate = purchaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DepreciationStrategy getDepreciationStrategy() {
        return depreciationStrategy;
    }

    public void setDepreciationStrategy(DepreciationStrategy depreciationStrategy) {
        this.depreciationStrategy = depreciationStrategy;
    }

    public abstract double calculateDepreciatedValue();
}
