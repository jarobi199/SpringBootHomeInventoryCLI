package io.homeinventory.menu;

import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.RoomType;
import io.homeinventory.interfaces.IMenu;
import io.homeinventory.model.Room;
import io.homeinventory.service.RoomService;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMenu implements IMenu {

    @Autowired
    private RoomService roomService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> listAllRooms();
                case 2 -> addRoom();
            }
        }
        while (choice != 0);
    }

    public void listAllRooms() {
        roomService.displayRooms();
    }

    public void addRoom() {
        System.out.println("Enter the name of the room:");
        String name = InputHandler.getStringInput();
        System.out.println("Enter the description of the room:");
        String description = InputHandler.getStringInput();
        System.out.println("Enter the room type (LIVING_ROOM, BEDROOM, KITCHEN, BATHROOM, OFFICE, GARAGE, BASEMENT, OTHER):");
        RoomType roomType = RoomType.valueOf(InputHandler.getStringInput());

        roomService.addRoom(name, description, roomType);
        System.out.println("Room added!");
    }

    @Override
    public void printOptions() {
        System.out.println("[1] List all rooms");
        System.out.println("[2] Add room");
        System.out.println("[3] View room contents");
        System.out.println("[4] Delete room");
        System.out.println("[0] Exit");
        System.out.println("Please make a selection:");
    }

    public Room listRoomsAndSelect() {
        int i = 1;
        Room room = null;
        List<Room> rooms = roomService.findRoomsByUserId(SessionContext.getUser().getId());

        if (!rooms.isEmpty()) {
            for (Room r : rooms) {
                System.out.println("[" + i + "] " + r.getName() + " (" + r.getRoomType().name() + ")");
                i++;
            }
            System.out.println("Please select a room:");

            int tripIndex = InputHandler.getIntegerInput() - 1;
            room = rooms.get(tripIndex);
        }

        return room;
    }

}
