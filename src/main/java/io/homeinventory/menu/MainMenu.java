package io.homeinventory.menu;

import io.homeinventory.interfaces.IMenu;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainMenu implements IMenu {

    @Autowired
    private AuthenticateMenu authenticateMenu;
    @Autowired
    private ItemMenu itemMenu;
    @Autowired
    private RoomMenu roomMenu;
    @Autowired
    private SettingsMenu settingsMenu;
    @Autowired
    private AlertMenu alertMenu;
    @Autowired
    private GoodbyeMenu goodbyeMenu;

    public void show() {
        int choice = 0;
        IMenu menu;

        displayTitle();
        authenticateMenu.show();
        System.out.println();

        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            menu = switch (choice) {
                case 1 -> roomMenu;
                case 2 -> itemMenu;
                case 4 -> alertMenu;
                case 5 -> settingsMenu;
                case 0 -> goodbyeMenu;
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            };
            menu.show();
        }
        while (choice != 0);

        InputHandler.closeInput();
    }

    public void printOptions() {
        System.out.println("[1] Rooms");
        System.out.println("[2] Items");
        System.out.println("[3] Reports");
        System.out.println("[4] Alerts");
        System.out.println("[5] Settings");
        System.out.println("[0] Exit");
        System.out.println("Please make a selection:");
    }

    public void displayTitle() {
        System.out.println("===========================================================");
        System.out.println("       Welcome to the Home Inventory Application!");
        System.out.println("============================================================");
    }

}
