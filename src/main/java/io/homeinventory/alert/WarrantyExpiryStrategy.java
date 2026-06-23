package io.homeinventory.alert;

import io.homeinventory.interfaces.AlertStrategy;
import io.homeinventory.model.ElectronicItem;
import io.homeinventory.model.Item;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class WarrantyExpiryStrategy implements AlertStrategy {

    @Override
    public boolean supports(Item item) {
        return item instanceof ElectronicItem;
    }

    @Override
    public AlertResult evaluate(Item item) {
        AlertResult alertResult = null;
        ElectronicItem electronicItem = (ElectronicItem) item;
        if((ChronoUnit.YEARS.between(electronicItem.getWarrantyExpiryDate(), LocalDate.now())) < 30) {
            alertResult = new AlertResult(item, AlertType.WARRANTY_EXPIRY, "The warranty is about to expire on this electronic item!");
        }

        return alertResult;
    }
}
