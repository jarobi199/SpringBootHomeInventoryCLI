package io.homeinventory.alert;

import io.homeinventory.interfaces.AlertStrategy;
import io.homeinventory.model.Item;
import org.springframework.stereotype.Component;

// Fires when item estimatedValue exceeds SessionContext.getUser().getHighValueThreshold()
@Component
public class HighValueItemStrategy implements AlertStrategy {


    @Override
    public boolean supports(Item item) {
        return false;
    }

    @Override
    public AlertResult evaluate(Item item) {
        return null;
    }
}
