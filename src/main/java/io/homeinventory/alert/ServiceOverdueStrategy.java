package io.homeinventory.alert;

import io.homeinventory.interfaces.AlertStrategy;
import io.homeinventory.model.ApplianceItem;
import io.homeinventory.model.Item;
import org.springframework.stereotype.Component;

// Fires when an ApplianceItem has no service records and is 3+ years old,
// or when the most recent service record is 2+ years old
@Component
public class ServiceOverdueStrategy implements AlertStrategy {

    @Override
    public boolean supports(Item item) {
        return item instanceof ApplianceItem;
    }

    @Override
    public AlertResult evaluate(Item item) {
        return null; // TODO
    }
}
