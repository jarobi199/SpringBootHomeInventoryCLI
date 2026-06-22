package io.homeinventory.model;

import java.time.LocalDate;

public record  ServiceRecord(LocalDate serviceDate, String description, double cost, String servicedBy) {}
