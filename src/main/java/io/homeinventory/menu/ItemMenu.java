package io.homeinventory.menu;

import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.interfaces.IMenu;
import io.homeinventory.model.Room;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ItemMenu implements IMenu {

    @Autowired
    private RoomMenu roomMenu;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> listAllItems();
                case 2 -> addItem();
                case 3 -> viewItemDetail();
                case 4 -> editItem();
                case 5 -> deleteItem();
                case 6 -> addServiceRecord();
            }
        }
        while (choice != 0);
    }

    public void addServiceRecord() {
    }

    public void deleteItem() {
    }

    public void editItem() {
    }

    public void viewItemDetail() {
    }

    public void addItem() {
        System.out.println("Enter the type of item (ELECTRONIC, FURNITURE, APPLIANCE):");
        ItemType itemType = ItemType.valueOf(InputHandler.getStringInput().toUpperCase());
        Room room = roomMenu.listRoomsAndSelect();
        if (room != null) {
            System.out.println("Enter the name:");
            String name = InputHandler.getStringInput();
            System.out.println("Enter the description:");
            String description = InputHandler.getStringInput();
            System.out.println("Enter the category");
            System.out.println("(ENTERTAINMENT, COMPUTING, KITCHEN_APPLIANCE, LAUNDRY");
            System.out.println("SEATING, STORAGE, BEDROOM_FURNITURE, LIGHTING, OTHER):");
            Category category = Category.valueOf(InputHandler.getStringInput().toUpperCase());
            System.out.println("Enter the estimated value:");
            double estimatedValue = InputHandler.getDoubleInput();
            System.out.println("Enter the purchase date (yyyy-MM-dd):");
            LocalDate purchaseDate = LocalDate.parse(InputHandler.getStringInput());
            System.out.println("Enter the notes");
            String notes = InputHandler.getStringInput();
        }
        else
        {
            System.out.println("You can create an item if there are no rooms created!\n");
        }
    }

    public void listAllItems() {
    }

    @Override
    public void printOptions() {
        System.out.println("[1] List all items");
        System.out.println("[2] Add item");
        System.out.println("[3] View item detail");
        System.out.println("[4] Edit item");
        System.out.println("[5] Delete item");
        System.out.println("[6] Add service record");
        System.out.println("[0] Exit");
    }
}
