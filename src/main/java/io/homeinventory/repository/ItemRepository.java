package io.homeinventory.repository;

import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByUserId(String userId);
    List<Item> findByRoomId(String roomId);
    List<Item> findByUserIdAndCategory(String userId, Category category);
    List<Item> findByUserIdAndItemType(String userId, ItemType itemType);
    void deleteByRoomId(String roomId);
    void deleteByUserId(String userId);
}
