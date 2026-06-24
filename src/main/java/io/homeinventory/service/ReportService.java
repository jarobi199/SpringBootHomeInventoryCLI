package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.ItemType;
import io.homeinventory.model.Item;
import io.homeinventory.model.Room;
import io.homeinventory.model.RoomSummary;
import io.homeinventory.repository.ItemRepository;
import io.homeinventory.repository.RoomRepository;
import io.homeinventory.util.BarChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ItemRepository itemRepository;
  @Autowired
    private RoomRepository roomRepository;

    public void generateHomeSummary() {
        int totalItems = itemRepository.findByUserId(SessionContext.getUser().getId()).size();
        int totalRooms = roomRepository.findByUserId(SessionContext.getUser().getId()).size();
        double totalEstimatedValue = itemRepository.findByUserId(SessionContext.getUser().getId()).stream().mapToDouble(Item::getEstimatedValue).sum();
        double totalDeprecatedValue = itemRepository.findByUserId(SessionContext.getUser().getId()).stream().mapToDouble(Item::getEstimatedValue).sum();

        System.out.println("HOME SUMMARY");
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]TOTAL ITEMS[/]",
                        "[*blue, bold]TOTAL ROOMS[/]",
                        "[*blue, bold]TOTAL ESTIMATED VALUE[/]",
                        "[*blue, bold]TOTAL DEPRECIATED VALUE[/]"
                )
                .row(String.valueOf(totalItems), String.valueOf(totalRooms), "$" + totalEstimatedValue, "$" + totalDeprecatedValue);
        table.render();
        System.out.println();

        var barChart = BarChartUtil.builder().title("TOTAL VALUE BY ITEM TYPE");
        for(ItemType itemType : ItemType.values()) {
            double totalByType = itemRepository.findByUserId(SessionContext.getUser().getId())
                    .stream().filter(item -> itemType.equals(item.getItemType()))
                    .mapToDouble(Item::getEstimatedValue).sum();
            barChart.bar(itemType.name(), totalByType);
        }
        barChart.showTotal(true);
        barChart.render();
    }


    public void generateRoomReport() {
        List<RoomSummary> roomSummaries = generateRoomSummaries();
        System.out.println("| ROOM SUMMARY |");
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "",
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]ITEM COUNT[/]",
                        "[*blue, bold]ESTIMATED TOTAL[/]",
                        "[*blue, bold]DEPRECIATED TOTAL[/]"
                );

        roomSummaries = roomSummaries.stream().sorted(Comparator.comparing(RoomSummary::totalEstimatedValue)).toList();
        for(RoomSummary roomSummary : roomSummaries) {
            table.row("", roomSummary.name(), roomSummary.type().name(), String.valueOf(roomSummary.itemCount()), "$" + roomSummary.totalEstimatedValue(), "$" + roomSummary.totalDepreciatedValue());

        }

        int grandTotalItemCount = roomSummaries.stream().mapToInt(RoomSummary::itemCount).sum();
        double grandTotalEstimatedTotal = roomSummaries.stream().mapToDouble(RoomSummary::totalEstimatedValue).sum();
        double grandDepreciatedValue = roomSummaries.stream().mapToDouble(RoomSummary::totalDepreciatedValue).sum();
        table.row( "[*blue, bold]TOTAL[/]", "", "", String.valueOf(grandTotalItemCount), "$" + grandTotalEstimatedTotal, "$" + grandDepreciatedValue);
        table.render();

    }

    public void generateInsuranceReport() {
        System.out.println("| INSURANCE REPORT |");
        System.out.println("====================================");
        System.out.println("NAME: " + SessionContext.getUser().getName());
        System.out.println("DATE AND TIME: " + LocalDateTime.now().toString());
        System.out.println("ADDRESS: 555 Anylane Drive, Anytown TX 30123");

        List<RoomSummary> roomSummaries = generateRoomSummaries();
        for (RoomSummary roomSummary : roomSummaries) {
            System.out.println(roomSummary.name());
            Table table = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "",
                            "[*blue, bold]NAME[/]",
                            "[*blue, bold]DESCRIPTION[/]",
                            "[*blue, bold]CATEGORY[/]",
                            "[*blue, bold]PURCHASE DATE[/]",
                            "[*blue, bold]ESTIMATED VALUE[/]",
                            "[*blue, bold]DEPRECIATED TOTAL[/]"
                    );
            for(Item item : roomSummary.items()) {
                table.row(item.getName(), item.getDescription(), item.getCategory().name(), item.getPurchaseDate().toString(), "$" + item.getEstimatedValue());
            }
            table.render();
            System.out.println("ROOM " +  roomSummary.name() + " TOTAL: $" + roomSummary.totalEstimatedValue());
        }
        System.out.println();
        System.out.println("----------------------");
        System.out.println("GRAND TOTAL OF ALL ROOMS: $" + roomSummaries.stream().mapToDouble(RoomSummary::totalEstimatedValue).sum());
        System.out.println("----------------------");
        System.out.println();
        System.out.println("====================================");
    }

    public void getValueByCategory() {

    }

    private List<RoomSummary> generateRoomSummaries() {
        List<Room> rooms = roomRepository.findByUserId(SessionContext.getUser().getId());
        List<RoomSummary> roomSummaries = new ArrayList<>();
        for(Room room : rooms) {
            List<Item> items = itemRepository.findByRoomId(room.getId());
            int itemCount = items.size();
            double estimatedTotal =  items.stream().mapToDouble(Item::getEstimatedValue).sum();
            double depreciatedTotal =  items.stream().mapToDouble(Item::calculateDepreciatedValue).sum();
            RoomSummary roomSummary = new RoomSummary(room.getName(), room.getRoomType(), itemCount, estimatedTotal, depreciatedTotal, items);
            roomSummaries.add(roomSummary);
        }

        return roomSummaries;
    }
}
