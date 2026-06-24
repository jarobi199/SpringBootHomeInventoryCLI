package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.RoomType;
import io.homeinventory.model.Item;
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

    public void listRoomContents(Room room) {
        List<Item> items = itemRepository.findByRoomId(room.getId());
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]DESCRIPTION[/]",
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]CATEGORY[/]",
                        "[*blue, bold]ESTIMATED VALUE[/]",
                        "[*blue, bold]DEPRECIATED VALUE[/]"
                );
        for (Item item : items) {
            table.row(item.getName(), item.getDescription(), item.getItemType().name(),
                    item.getCategory().name(), "$" + item.getEstimatedValue(), "$" + item.calculateDepreciatedValue());
        }
        table.render();
    }

    public void deleteRoom(Room room) {
        int count = itemRepository.findByRoomId(room.getId()).size();
        if (count == 0) {
            roomRepository.delete(room);
            System.out.println("Room deleted!");
        }
        else
        {
            System.out.println("This room cannot be deleted because it has items in it!");
        }
    }

    // findByUserId(), save(), delete() — rejects if items exist
}
