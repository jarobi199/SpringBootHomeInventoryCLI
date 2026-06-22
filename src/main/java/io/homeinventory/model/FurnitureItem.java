package io.homeinventory.model;

import io.homeinventory.depreciation.LinearDepreciationStrategy;
import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.enums.Material;

import java.time.LocalDate;

public class FurnitureItem extends Item {
    private int widthCm;
    private int heightCm;
    private int depthCm;
    private Material material;

    public FurnitureItem() {
        this.depreciationStrategy = new LinearDepreciationStrategy();
    }

    public FurnitureItem(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate, int widthCm, int heightCm, int depthCm, Material material) {
        super(userId, roomId, name, description, category, estimatedValue, notes, purchaseDate);
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.depthCm = depthCm;
        this.material = material;
    }

    public int getWidthCm() {
        return widthCm;
    }

    public void setWidthCm(int widthCm) {
        this.widthCm = widthCm;
    }

    public int getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }

    public int getDepthCm() {
        return depthCm;
    }

    public void setDepthCm(int depthCm) {
        this.depthCm = depthCm;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDimensions() {
        return widthCm + "cm x " + heightCm + "cm x " + depthCm + "cm";
    }

    @Override
    public double calculateDepreciatedValue() {
        return depreciationStrategy.calculate(estimatedValue, purchaseDate);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.FURNITURE;
    }

}
