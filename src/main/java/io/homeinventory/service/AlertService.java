package io.homeinventory.service;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.homeinventory.alert.AlertManager;
import io.homeinventory.alert.AlertResult;
import io.homeinventory.alert.AlertType;
import io.homeinventory.authentication.SessionContext;
import io.homeinventory.model.Item;
import io.homeinventory.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AlertManager alertManager;


    public void displayAlerts(AlertType alertType) {
        List<Item> items = itemRepository.findByUserId(SessionContext.getUser().getId());
        List<AlertResult> results = alertManager.evaluate(items);
        if(alertType != null) {
            results = results.stream().filter(result -> result.alertType().equals(alertType)).toList();
        }
        displayAlertResults(results);
    }

    public void displayAlertResults(List<AlertResult> alertResults) {
        if(alertResults.isEmpty()) {
            System.out.println("No alerts found.\n");
        }
        else
        {
            System.out.println("ALERTS");
            Table table = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]ALERT TYPE[/]",
                            "[*blue, bold]ITEM NAME[/]",
                            "[*blue, bold]ITEM TYPE[/]",
                            "[*blue, bold]MESSAGE[/]"
                    );
            for (AlertResult alertResult : alertResults) {
                table.row(alertResult.alertType().getDisplayName(), alertResult.item().getName(), alertResult.item().getItemType().name(), alertResult.message());
            }
            table.render();
        }
    }
}
