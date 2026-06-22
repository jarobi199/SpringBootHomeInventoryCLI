package io.homeinventory.service;

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
    // findByUserId(), save(), delete() — rejects if items exist
}
