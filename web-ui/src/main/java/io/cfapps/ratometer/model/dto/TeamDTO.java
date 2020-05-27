package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class TeamDTO implements Serializable {

    private Integer index;
    private String teamName;
    private Integer size;
    private UUID uuid;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
