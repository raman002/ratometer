package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class AssignTeamDTO implements Serializable {

    private UUID teamId;
    private UUID userId;

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
