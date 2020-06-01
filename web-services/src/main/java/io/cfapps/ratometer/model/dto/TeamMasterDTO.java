package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class TeamMasterDTO implements Serializable {

    private UUID uuid;
    private String teamName;
    private Long size;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
