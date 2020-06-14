package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class AssignTeamDTO implements Serializable {

    private UUID teamUid;
    private UUID userUid;

    public UUID getTeamUid() {
        return teamUid;
    }

    public void setTeamUid(UUID teamUid) {
        this.teamUid = teamUid;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }
}
