package io.homeinventory.alert;

import io.homeinventory.authentication.SessionContext;
import io.homeinventory.interfaces.AlertStrategy;
import io.homeinventory.model.Item;
import org.springframework.stereotype.Component;

@Component
public class HighValueItemStrategy implements AlertStrategy {

    @Override
    public boolean supports(Item item) {
        return (item != null);
    }

    @Override
    public AlertResult evaluate(Item item) {
        AlertResult alertResult = null;
        if(item.getEstimatedValue() > SessionContext.getUser().getHighValueThreshold()) {
            alertResult = new AlertResult(item, AlertType.HIGH_VALUE_ITEM, "This is a high value item! Please ensure that this item is explicitly listed on the insurance policy.");
        }

        return alertResult;
    }
}
