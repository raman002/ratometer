package io.cfapps.ratometer.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cfapps.ratometer.entity.support.AbstractBaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "teams_master")
public class TeamMaster extends AbstractBaseEntity {

    @Id
    @JsonIgnore
    @Column(name = "teams_master_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long teamsMasterId;

    @NotNull
    @Column(name = "name", unique = true)
    private String teamName;

    public Long getTeamsMasterId() {
        return teamsMasterId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
