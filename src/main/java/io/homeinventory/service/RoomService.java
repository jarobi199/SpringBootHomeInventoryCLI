package io.homeinventory.service;

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

    public List<Room> getRoomsByUser(String userId) {
        return roomRepository.findByUserId(userId);
    }

    public void addRoom(String name, String description, RoomType roomType) {
        Room room = new Room(SessionContext.getUser().getId(), name, description, roomType);
        roomRepository.save(room);
    }
    // findByUserId(), save(), delete() — rejects if items exist
}
