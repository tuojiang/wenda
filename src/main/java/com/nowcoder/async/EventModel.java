package com.nowcoder.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 18-9-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public class EventModel {
    private EventType type;
    private int actionId;
    private int entityId;
    private int entityType;
    private int entityOwnerId;

    private Map<String,String> exts = new HashMap<>();

    public EventModel() {
    }

    public EventModel SetExt(String key,String value){
        exts.put(key, value);
        return this;
    }

    public String getExt(String key){
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActionId() {
        return actionId;
    }

    public EventModel setActionId(int actionId) {
        this.actionId = actionId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
