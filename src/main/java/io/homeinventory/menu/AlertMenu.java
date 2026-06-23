package io.homeinventory.menu;

import io.homeinventory.alert.AlertType;
import io.homeinventory.interfaces.IMenu;
import io.homeinventory.service.AlertService;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlertMenu implements IMenu {

    @Autowired
    private AlertService alertService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> viewAllAlerts();
                case 2 -> warrantyExpiryAlerts();
                case 3 -> serviceOverdueAlerts();
                case 4 -> highValueItems();
            }
        }
        while (choice != 0);
    }

    public void highValueItems() {
        alertService.displayAlerts(AlertType.HIGH_VALUE_ITEM);
    }

    public void serviceOverdueAlerts() {
        alertService.displayAlerts(AlertType.SERVICE_OVERDUE);
    }

    public void warrantyExpiryAlerts() {
        alertService.displayAlerts(AlertType.WARRANTY_EXPIRY);
    }

    public void viewAllAlerts() {
        alertService.displayAlerts(null);
    }

    @Override
    public void printOptions() {
        System.out.println("[1] View all alerts");
        System.out.println("[2] Warranty expiry alerts");
        System.out.println("[3] Service overdue alerts");
        System.out.println("[4] High value items");
        System.out.println("[0] Back");
    }
}
