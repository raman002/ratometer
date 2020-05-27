package io.cfapps.ratometer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cfapps.ratometer.entity.support.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "teams")
public class Team extends BaseEntity {

    @Id
    @JsonIgnore
    @Column(name = "teams_id")
    @SequenceGenerator(name = "teams_seq", sequenceName = "teams_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_seq")
    private Long pk;

    @JsonIgnore
    @Column(name = "users_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "teams_master_id")
    private Long teamsMasterId;

    public Long getPk() {
        return pk;
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
}
