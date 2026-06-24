package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.enums.ItemType;
import io.homeinventory.model.Item;
import io.homeinventory.model.Room;
import io.homeinventory.repository.ItemRepository;
import io.homeinventory.repository.RoomRepository;
import io.homeinventory.util.BarChartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                        "[*blue, bold]TOTAL DEPRECATED VALUE[/]"
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
        List<Room> rooms = roomRepository.findByUserId(SessionContext.getUser().getId());
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]ITEM COUNT[/]",
                        "[*blue, bold]ESTIMATED TOTAL[/]",
                        "[*blue, bold]DEPRECATED TOTAL[/]"
                );
        for(Room room : rooms) {
            int itemCount = itemRepository.findByRoomId(room.getId()).size();
            double estimatedTotal =  itemRepository.findByRoomId(room.getId()).stream().mapToDouble(Item::getEstimatedValue).sum();
            double depreciatedTotal =  itemRepository.findByRoomId(room.getId()).stream().mapToDouble(Item::calculateDepreciatedValue).sum();

        }
        table.render();
    }

    public void generateInsuranceReport() {

    }

    public void getValueByCategory() {

    }
}
