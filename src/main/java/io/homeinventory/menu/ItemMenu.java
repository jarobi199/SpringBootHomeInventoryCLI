package io.homeinventory.menu;

import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.enums.Material;
import io.homeinventory.interfaces.IMenu;
import io.homeinventory.model.Room;
import io.homeinventory.service.ItemService;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ItemMenu implements IMenu {

    @Autowired
    private RoomMenu roomMenu;
    @Autowired
    ItemService itemService;

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
            switch (itemType) {
                case ELECTRONIC -> {
                    System.out.println("Enter the serial number:");
                    String serialNumber = InputHandler.getStringInput();
                    System.out.println("Enter warrany expiry date (yyyy-MM-dd):");
                    LocalDate expiryDate = LocalDate.parse(InputHandler.getStringInput());
                    itemService.addElectronicItem(SessionContext.getUser().getId(), room.getId(), name, description, category, estimatedValue, notes, purchaseDate, serialNumber, expiryDate);
                    System.out.println("Electronic item added successfully!");
                }
                case APPLIANCE -> {
                    System.out.println("Enter the model number:");
                    int modelNumber = InputHandler.getIntegerInput();
                    itemService.addApplianceItem(SessionContext.getUser().getId(), room.getId(), name, description, category, estimatedValue, notes, purchaseDate, modelNumber);
                    System.out.println("Appliance  item added successfully!");
                }
                case FURNITURE -> {
                    System.out.println("Enter the width (cm):");
                    int width = InputHandler.getIntegerInput();
                    System.out.println("Enter the height (cm):");
                    int height = InputHandler.getIntegerInput();
                    System.out.println("Enter the depth (cm):");
                    int depth = InputHandler.getIntegerInput();
                    System.out.println("Enter the type of material:");
                    Material material = Material.valueOf(InputHandler.getStringInput().toUpperCase());
                    itemService.addFurnitureItem(SessionContext.getUser().getId(), room.getId(), name, description, category, estimatedValue, notes, purchaseDate, width, height, depth, material);
                    System.out.println("Appliance  item added successfully!");
                }
            }
        }
        else
        {
            System.out.println("You can create an item if there are no rooms created!\n");
        }
    }

    public void listAllItems() {
        itemService.listAllItems();
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
