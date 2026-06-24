package io.homeinventory.menu;

import io.homeinventory.interfaces.IMenu;
import io.homeinventory.service.ReportService;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportMenu implements IMenu {

    @Autowired
    private ReportService reportService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> homeSummary();
                case 2 -> roomReport();
                case 3 -> insuranceReport();
                case 4 -> valueByCategory();
            }
        }
        while (choice != 0);
    }

    public void valueByCategory() {
    }

    public void insuranceReport() {
    }

    public void roomReport() {
    }

    public void homeSummary() {
        reportService.generateHomeSummary();
    }

    @Override
    public void printOptions() {
        System.out.println("[1] Home summary");
        System.out.println("[2] Room-by-room report");
        System.out.println("[3] Insurance report");
        System.out.println("[4] Value by category");
        System.out.println("[0] Back");
    }

}
