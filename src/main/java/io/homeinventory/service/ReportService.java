package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.model.Item;
import io.homeinventory.repository.ItemRepository;
import io.homeinventory.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]TOTAL ITEMS[/]",
                        "[*blue, bold]TOTAL ROOMS[/]",
                        "[*blue, bold]TOTAL ESTIMATED VALUE[/]",
                        "[*blue, bold]TOTAL DEPRECATED VALUE[/]"
                )
                .row(String.valueOf(totalItems), String.valueOf(totalRooms), "$" + totalEstimatedValue, "$" + totalDeprecatedValue);
    }

    public void generateRoomReport() {

    }

    public void generateInsuranceReport() {

    }

    public void getValueByCategory() {

    }
}
