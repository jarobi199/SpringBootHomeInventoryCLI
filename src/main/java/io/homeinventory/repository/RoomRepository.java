package io.homeinventory.repository;

import io.homeinventory.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room>  findByUserId(String userId);
    void deleteByUserId(String userId);

}
