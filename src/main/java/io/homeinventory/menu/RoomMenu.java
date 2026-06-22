package io.homeinventory.menu;

import io.homeinventory.authentication.SessionContext;
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

    }

    @Override
    public void printOptions() {

    }

    public Room listRoomsAndSelect() {
        int i = 1;
        Room room = null;
        List<Room> rooms = roomService.getRoomsByUser(SessionContext.getUser().getId());

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
