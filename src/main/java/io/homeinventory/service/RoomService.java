package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.RoomType;
import io.homeinventory.model.Room;
import io.homeinventory.repository.ItemRepository;
import io.homeinventory.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ItemRepository itemRepository;

    public List<Room> findRoomsByUserId(String userId) {
        return roomRepository.findByUserId(userId);
    }

    public void addRoom(String name, String description, RoomType roomType) {
        Room room = new Room(SessionContext.getUser().getId(), name, description, roomType);
        roomRepository.save(room);
    }

    public void displayRooms() {
        List<Room> rooms = roomRepository.findByUserId(SessionContext.getUser().getId());
        System.out.println("ROOMS");
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]DESCRIPTION[/]",
                        "[*blue, bold]TYPE[/]"
                );
        for (Room room : rooms) {
            table.row(room.getName(), room.getDescription(), room.getRoomType().name());
        }
        table.render();
    }

    // findByUserId(), save(), delete() — rejects if items exist
}
