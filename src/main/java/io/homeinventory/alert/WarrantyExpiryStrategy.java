package io.homeinventory.alert;

import io.homeinventory.interfaces.AlertStrategy;
import io.homeinventory.model.ElectronicItem;
import io.homeinventory.model.Item;
import org.springframework.stereotype.Component;

// Fires when an ElectronicItem warranty expires within 30 days
@Component
public class WarrantyExpiryStrategy implements AlertStrategy {

    @Override
    public boolean supports(Item item) {
        return item instanceof ElectronicItem;
    }

    @Override
    public AlertResult evaluate(Item item) {
        return null; // TODO
    }
}
