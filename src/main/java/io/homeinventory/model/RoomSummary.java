package io.homeinventory.model;

import io.homeinventory.enums.RoomType;

import java.util.List;

public record RoomSummary (String name, RoomType type, int itemCount, double totalEstimatedValue, double totalDepreciatedValue, List<Item> items) {}