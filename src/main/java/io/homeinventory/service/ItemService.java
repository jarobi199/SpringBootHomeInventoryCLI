package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.Category;
import io.homeinventory.enums.ItemType;
import io.homeinventory.enums.Material;
import io.homeinventory.model.*;
import io.homeinventory.repository.ItemRepository;
import io.homeinventory.repository.RoomRepository;
import io.homeinventory.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Map<String, Room> roomMap = roomRepository.findByUserId(SessionContext.getUser().getId()).stream()
                .collect(Collectors.toMap(
                        Room::getId,
                        Function.identity()
                ));
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
            table.row(item.getName(), item.getDescription(),  roomMap.get(item.getRoomId()).getName(),  item.getItemType().name(), item.getCategory().name(), "$" + item.getEstimatedValue());
        }
        table.render();

    }

    public List<Item>  findItemsByRoomId(String roomId) {
        return itemRepository.findByRoomId(roomId);
    }

    public List<Item> findItemsByUserId(String userId) {
        return itemRepository.findByUserId(userId);
    }

    public void viewItemDetail(Item item) {
        Optional<Room> optionalRoom =  roomRepository.findById(item.getRoomId());
        String roomName = optionalRoom.isPresent() ? optionalRoom.get().getName() : "";

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
                            "[*blue, bold]DEPRECIATED VALUE[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]NOTES[/]",
                            "[*blue, bold]MODEL NUMBER[/]",
                            "[*blue, bold]YEARS OWNED[/]"
                    );
            table.row(applianceItem.getName(), applianceItem.getDescription(), roomName, applianceItem.getItemType().name(), applianceItem.getCategory().name(), "$" + applianceItem.getEstimatedValue(),
                    "$" + applianceItem.calculateDepreciatedValue(), applianceItem.getPurchaseDate().toString(),
                    applianceItem.getNotes(), String.valueOf(applianceItem.getModelNumber()), String.valueOf(ChronoUnit.YEARS.between(applianceItem.getPurchaseDate(), LocalDate.now())));
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
                serviceRecordTable.render();
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
                            "[*blue, bold]DEPRECIATED VALUE[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]NOTES[/]",
                            "[*blue, bold]WIDTH[/]",
                            "[*blue, bold]HEIGHT[/]",
                            "[*blue, bold]DEPTH[/]",
                            "[*blue, bold]MATERIAL[/]",
                            "[*blue, bold]YEARS OWNED[/]"
                    );
            furnitureItemTable.row(furnitureItem.getName(), furnitureItem.getDescription(), roomName, furnitureItem.getItemType().name(), furnitureItem.getCategory().name(), "$" + furnitureItem.getEstimatedValue(),
                    "$" + furnitureItem.calculateDepreciatedValue(), furnitureItem.getPurchaseDate().toString(), furnitureItem.getNotes(),
                    String.valueOf(furnitureItem.getWidthCm()), String.valueOf(furnitureItem.getHeightCm()), String.valueOf(furnitureItem.getDepthCm()), String.valueOf(furnitureItem.getMaterial()), String.valueOf(ChronoUnit.YEARS.between(furnitureItem.getPurchaseDate(), LocalDate.now())));
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
                            "[*blue, bold]DEPRECIATED VALUE[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]NOTES[/]",
                            "[*blue, bold]MODEL NUMBER[/]",
                            "[*blue, bold]WARRANTY EXPIRATION DATE[/]",
                            "[*blue, bold]YEARS OWNED[/]"
                    );
            electronicItemTable.row(electronicItem.getName(), electronicItem.getDescription(), roomName, electronicItem.getItemType().name(), electronicItem.getCategory().name(), InputHandler.formatAsMoney(electronicItem.getEstimatedValue()),
                    InputHandler.formatAsMoney(electronicItem.calculateDepreciatedValue()), electronicItem.getPurchaseDate().toString(), electronicItem.getNotes(),
                    electronicItem.getSerialNumber(), electronicItem.getWarrantyExpiryDate().toString(), String.valueOf(ChronoUnit.YEARS.between(electronicItem.getPurchaseDate(), LocalDate.now())));
            electronicItemTable.render();
        }
    }

    public void addServiceRecord(LocalDate date, String description, double cost, String servicedBy, ApplianceItem applianceItem) {
        ServiceRecord serviceRecord = new ServiceRecord(date, description, cost, servicedBy);
        applianceItem.addServiceRecord(serviceRecord);
        itemRepository.save(applianceItem);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

}
