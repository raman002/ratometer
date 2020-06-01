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
    @SequenceGenerator(name = "role_master_seq", sequenceName = "role_master_role_master_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_master_seq")
    private Long pk;

    @NotNull
    @Column(name = "role_name", unique = true)
    private String roleName;

    public Long getPk() {
        return pk;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
