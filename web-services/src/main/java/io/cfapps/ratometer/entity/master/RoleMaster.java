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
@Table(name = "role_master")
public class RoleMaster extends AbstractBaseEntity {

    @Id
    @JsonIgnore
    @Column(name = "role_master_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long roleMasterId;

    @NotNull
    @Column(name = "role_name", unique = true)
    private String roleName;

    public Long getRoleMasterId() {
        return roleMasterId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
