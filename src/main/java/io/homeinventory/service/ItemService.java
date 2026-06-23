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
        System.out.println("ITEM DETAIL");
        if(ItemType.APPLIANCE.equals(item.getItemType())) {
            ApplianceItem applianceItem = (ApplianceItem) item;
            Table table = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]NAME[/]",
                            "[*blue, bold]DESCRIPTION[/]",
                            "[*blue, bold]ROOM[/]",
                            "[*blue, bold]TYPE[/]",
                            "[*blue, bold]CATEGORY[/]",
                            "[*blue, bold]ESTIMATED VALUE[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]NOTES[/]",
                            "[*blue, bold]MODEL NUMBER[/]"
                    );
            table.row(applianceItem.getName(), applianceItem.getDescription(), applianceItem.getItemType().name(), applianceItem.getCategory().name(), "$" + applianceItem.getEstimatedValue(),
                    applianceItem.getPurchaseDate().toString(), applianceItem.getNotes(), String.valueOf(applianceItem.getModelNumber()));
            table.render();
            System.out.println();

            List<ServiceRecord> serviceRecords = applianceItem.getServiceHistory();
            if(serviceRecords != null) {
                System.out.println("SERVICE HISTORY");
                Table serviceRecordTable = Clique.table(TableType.BOX_DRAW)
                        .headers(
                                "[*blue, bold]SERVICE DATE[/]",
                                "[*blue, bold]DESCRIPTION[/]",
                                "[*blue, bold]COST[/]",
                                "[*blue, bold]SERVICED BY[/]"
                        );
                for (ServiceRecord serviceRecord : serviceRecords) {
                    serviceRecordTable.row(serviceRecord.serviceDate().toString(), serviceRecord.description(), "$" + serviceRecord.cost(), serviceRecord.servicedBy());
                }
                table.render();
            }
        }
        else if(ItemType.FURNITURE.equals(item.getItemType())) {
            FurnitureItem furnitureItem = (FurnitureItem) item;
            Table furnitureItemTable = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]NAME[/]",
                            "[*blue, bold]DESCRIPTION[/]",
                            "[*blue, bold]ROOM[/]",
                            "[*blue, bold]TYPE[/]",
                            "[*blue, bold]CATEGORY[/]",
                            "[*blue, bold]ESTIMATED VALUE[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]NOTES[/]",
                            "[*blue, bold]WIDTH[/]",
                            "[*blue, bold]HEIGHT[/]",
                            "[*blue, bold]DEPTH[/]",
                            "[*blue, bold]MATERIAL[/]"
                    );
            furnitureItemTable.row(furnitureItem.getName(), furnitureItem.getDescription(), furnitureItem.getItemType().name(), furnitureItem.getCategory().name(), "$" + furnitureItem.getEstimatedValue(),
                    furnitureItem.getPurchaseDate().toString(), furnitureItem.getNotes(), String.valueOf(furnitureItem.getWidthCm()), String.valueOf(furnitureItem.getHeightCm()), String.valueOf(furnitureItem.getDepthCm()), String.valueOf(furnitureItem.getMaterial()));
            furnitureItemTable.render();
        }
        else
        {
            ElectronicItem electronicItem = (ElectronicItem) item;
            Table electronicItemTable = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]NAME[/]",
                            "[*blue, bold]DESCRIPTION[/]",
                            "[*blue, bold]ROOM[/]",
                            "[*blue, bold]TYPE[/]",
                            "[*blue, bold]CATEGORY[/]",
                            "[*blue, bold]ESTIMATED VALUE[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]NOTES[/]",
                            "[*blue, bold]MODEL NUMBER[/]"
                    );
            electronicItemTable.row(electronicItem.getName(), electronicItem.getDescription(), electronicItem.getItemType().name(), electronicItem.getCategory().name(), "$" + electronicItem.getEstimatedValue(),
                    electronicItem.getPurchaseDate().toString(), electronicItem.getNotes(), electronicItem.getSerialNumber(), electronicItem.getWarrantyExpiryDate().toString());
            electronicItemTable.render();
        }
    }

    // findByUserId(), findByRoomId(), findByUserIdAndCategory(), save(), delete()
}
