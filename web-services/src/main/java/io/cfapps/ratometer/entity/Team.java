package io.cfapps.ratometer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cfapps.ratometer.entity.support.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "teams")
public class Team extends BaseEntity {

    @Id
    @JsonIgnore
    @Column(name = "teams_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long teamsId;

    @JsonIgnore
    @Column(name = "users_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "teams_master_id")
    private Long teamsMasterId;

    @Column(name = "teams_master_uid")
    private UUID teamMasterUid;

    public Long getTeamsId() {
        return teamsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamsMasterId() {
        return teamsMasterId;
    }

    public void setTeamsMasterId(Long roleMasterId) {
        this.teamsMasterId = roleMasterId;
    }

    public UUID getTeamMasterUid() {
        return teamMasterUid;
    }

    public void setTeamMasterUid(UUID teamMasterUid) {
        this.teamMasterUid = teamMasterUid;
    }
}
