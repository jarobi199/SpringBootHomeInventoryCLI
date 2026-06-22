package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.PendingTable;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.enums.Material;
import io.homeinventory.model.*;
import io.homeinventory.repository.ItemRepository;
import io.homeinventory.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void addElectronicItem(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate, String serialNumber, LocalDate expiryDate) {
        ElectronicItem electronicItem = new ElectronicItem(userId, roomId, name, description, category, estimatedValue, notes, purchaseDate, serialNumber, expiryDate);
        itemRepository.save(electronicItem);
    }

    public void addApplianceItem(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate, int modelNumber) {
        ApplianceItem applianceItem = new ApplianceItem(userId, roomId, name, description, category, estimatedValue, notes, purchaseDate, modelNumber);
        itemRepository.save(applianceItem);
    }

    public void addFurnitureItem(String userId, String roomId, String name, String description, Category category, double estimatedValue, String notes, LocalDate purchaseDate, int width, int height, int depth, Material material) {
        FurnitureItem furnitureItem = new FurnitureItem(userId, roomId, name, description, category, estimatedValue, notes, purchaseDate, width, height, depth, material);
        itemRepository.save(furnitureItem);
    }

    public void listAllItems() {
        List<Item> items = itemRepository.findByUserId(SessionContext.getUser().getId());
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]DESCRIPTION[/]",
                        "[*blue, bold]ROOM[/]",
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]CATEGORY[/]",
                        "[*blue, bold]ESTIMATED VALUE[/]"
                );
        for (Item item : items) {
            Optional<Room> optionalRoom = roomRepository.findById(item.getRoomId());
            table.row(item.getName(), item.getDescription(), optionalRoom.isPresent() ? optionalRoom.get().getName() : "",  item.getItemType().name(), item.getCategory().name(), "$" + item.getEstimatedValue());
        }
        table.render();

    }

    public List<Item> findItemsByUserId(String userId) {
        return itemRepository.findByUserId(userId);
    }

    public void viewItemDetail(Item item) {
        PendingTable table = Clique.table(TableType.BOX_DRAW);

        if(ItemType.APPLIANCE.equals(item.getItemType())) {

        }
        else if(ItemType.FURNITURE.equals(item.getItemType())) {

        }
        else
        {

        }
    }

    // findByUserId(), findByRoomId(), findByUserIdAndCategory(), save(), delete()
}
