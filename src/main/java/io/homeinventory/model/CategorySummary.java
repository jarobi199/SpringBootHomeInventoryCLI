package io.homeinventory.model;

import io.homeinventory.enums.Category;

public record CategorySummary(Category category, double totalValue, long itemCount){}