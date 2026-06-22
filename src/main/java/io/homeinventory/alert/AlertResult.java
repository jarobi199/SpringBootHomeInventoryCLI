package io.homeinventory.alert;


import io.homeinventory.model.Item;

public record AlertResult (Item item, AlertType alertType, String message){}
