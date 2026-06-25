package io.homeinventory.alert;

import io.homeinventory.interfaces.AlertStrategy;
import io.homeinventory.model.ApplianceItem;
import io.homeinventory.model.Item;
import io.homeinventory.model.ServiceRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Component
public class ServiceOverdueStrategy implements AlertStrategy {

    @Override
    public boolean supports(Item item) {
        return item instanceof ApplianceItem;
    }

    @Override
    public AlertResult evaluate(Item item) {
        AlertResult alertResult = null;
        ApplianceItem applianceItem = (ApplianceItem) item;
        if(checkServiceOverdue(applianceItem)) {
            alertResult = new AlertResult(item, AlertType.SERVICE_OVERDUE, "This appliance is overdue for service!");
        }
        return alertResult;
    }

    private boolean checkServiceOverdue(ApplianceItem applianceItem) {
        boolean result =  (applianceItem.getServiceHistory().isEmpty()) && ((ChronoUnit.YEARS.between(applianceItem.getPurchaseDate(), LocalDate.now())) > 3);
        if(!result) {
            ServiceRecord recentServiceRecord = applianceItem.getServiceHistory().stream()
                    .sorted(Comparator.comparing(ServiceRecord::serviceDate).reversed()).toList().getFirst();
            if((ChronoUnit.YEARS.between(recentServiceRecord.serviceDate(), LocalDate.now())) > 3) {
                result = true;
            }
        }

        return result;
    }
}
