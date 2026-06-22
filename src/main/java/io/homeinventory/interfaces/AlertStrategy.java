package io.homeinventory.interfaces;

import io.homeinventory.alert.AlertResult;
import io.homeinventory.model.Item;

public interface AlertStrategy {
    boolean supports(Item item);
    AlertResult evaluate(Item item);
}
