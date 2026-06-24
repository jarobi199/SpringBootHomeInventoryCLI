package io.homeinventory.model;

import java.time.LocalDateTime;
import java.util.List;

public record InsuranceReport (String ownerName, LocalDateTime reportDateTime, double totalEstimatedValue, double totalDepreciatedValue, List<RoomSummary> roomSummaries) {}

