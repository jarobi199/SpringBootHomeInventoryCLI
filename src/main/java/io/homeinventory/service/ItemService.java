package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.Category;
import io.homeinventory.enums.Material;
import io.homeinventory.model.ApplianceItem;
import io.homeinventory.model.ElectronicItem;
import io.homeinventory.model.FurnitureItem;
import io.homeinventory.model.Item;
import io.homeinventory.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

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
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]CATEGORY[/]",
                        "[*blue, bold]ESTIMATED VALUE[/]"
                );
        for (Item item : items) {
            table.row(item.getName(), item.getDescription(), item.getItemType().name(), item.getCategory().name(), "$" + item.getEstimatedValue());
        }
        table.render();

    }

    public static void main(String[] args) {
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]DESCRIPTION[/]",
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]CATEGORY[/]",
                        "[*blue, bold]ESTIMATED VALUE[/]"
                );
        table.render();
    }
    // findByUserId(), findByRoomId(), findByUserIdAndCategory(), save(), delete()
}
