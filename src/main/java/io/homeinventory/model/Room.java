package io.homeinventory.model;

import io.homeinventory.enums.RoomType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String userId;
    private String name;
    private String description;
    private RoomType roomType;

    public Room() {
        //No argument constructor
    }
    public Room(String id, String userId, String name, String description, RoomType roomType) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.roomType = roomType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
