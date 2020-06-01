package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class OptionsDTO implements Serializable {
    private UUID uuid;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
