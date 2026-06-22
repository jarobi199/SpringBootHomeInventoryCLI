package io.homeinventory.alert;

public enum AlertType {
    WARRANTY_EXPIRY("Warranty Expiry"),
    HIGH_VALUE_ITEM("High Value Item"),
    SERVICE_OVERDUE("Service Overdue");

    private final String displayName;

    AlertType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
