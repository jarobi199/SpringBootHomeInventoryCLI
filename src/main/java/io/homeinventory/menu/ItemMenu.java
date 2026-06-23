package io.homeinventory.menu;

import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.enums.Material;
import io.homeinventory.interfaces.IMenu;
import io.homeinventory.model.ApplianceItem;
import io.homeinventory.model.Item;
import io.homeinventory.model.Room;
import io.homeinventory.service.ItemService;
import io.homeinventory.service.RoomService;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ItemMenu implements IMenu {

    @Autowired
    private RoomMenu roomMenu;

    @Autowired
    ItemService itemService;

    @Autowired
    RoomService roomService;

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
        Item item = listItemsAndSelect(ItemType.APPLIANCE);
        ApplianceItem  applianceItem = (ApplianceItem) item;
        System.out.println("Enter the service date (yyyy-MM-dd):");
        LocalDate date = InputHandler.getDateInput();
        System.out.println("Enter the description:");
        String description = InputHandler.getStringInput();
        System.out.println("Enter the cost:");
        double cost = InputHandler.getDoubleInput();
        System.out.println("Enter the serviced by:");
        String servicedBy = InputHandler.getStringInput();

        itemService.addServiceRecord(date, description, cost, servicedBy, applianceItem);
        System.out.println("Service record added!");
    }

    public void deleteItem() {
    }

    public void editItem() {
    }

    public void viewItemDetail() {
        Item item = listItemsAndSelect();
        itemService.viewItemDetail(item);
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
                    System.out.println("Enter warranty expiry date (yyyy-MM-dd):");
                    LocalDate expiryDate = LocalDate.parse(InputHandler.getStringInput());
                    itemService.addElectronicItem(SessionContext.getUser().getId(), room.getId(), name, description, category, estimatedValue, notes, purchaseDate, serialNumber, expiryDate);
                    System.out.println("Electronic item added successfully!");
                }
                case APPLIANCE -> {
                    System.out.println("Enter the model number:");
                    int modelNumber = InputHandler.getIntegerInput();
                    itemService.addApplianceItem(SessionContext.getUser().getId(), room.getId(), name, description, category, estimatedValue, notes, purchaseDate, modelNumber);
                    System.out.println("Appliance item added successfully!");
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
                    System.out.println("Furniture  item added successfully!");
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

    public Item listItemsAndSelect() {
        int m = 1;
        Item item = null;
        List<Item> items = itemService.findItemsByUserId(SessionContext.getUser().getId());
        Map<String, Room> roomMap = roomService.findRoomsByUserId(SessionContext.getUser().getId()).stream()
                .collect(Collectors.toMap(
                        Room::getId,
                        Function.identity()
                ));

        if (!items.isEmpty()) {
            for (Item i : items) {
                System.out.println("[" + m + "] " + i.getName() + " (" + i.getItemType().name() + ") " + roomMap.get(i.getRoomId()).getName() + " $" + i.getEstimatedValue());
                m++;
            }
            System.out.println("Please select a room:");

            int itemIndex = InputHandler.getIntegerInput() - 1;
            item = items.get(itemIndex);
        }

        return item;
    }

    public Item listItemsAndSelect(ItemType itemType) {
        int m = 1;
        Item item = null;
        List<Item> items = itemService.findItemsByUserId(SessionContext.getUser().getId()).stream().filter(i -> i.getItemType().equals(itemType)).toList();
        Map<String, Room> roomMap = roomService.findRoomsByUserId(SessionContext.getUser().getId()).stream()
                .collect(Collectors.toMap(
                        Room::getId,
                        Function.identity()
                ));

        if (!items.isEmpty()) {
            for (Item i : items) {
                System.out.println("[" + m + "] " + i.getName() + " (" + i.getItemType().name() + ") " + roomMap.get(i.getRoomId()).getName() + " $" + i.getEstimatedValue());
                m++;
            }
            System.out.println("Please select a room:");

            int itemIndex = InputHandler.getIntegerInput() - 1;
            item = items.get(itemIndex);
        }

        return item;
    }

}
