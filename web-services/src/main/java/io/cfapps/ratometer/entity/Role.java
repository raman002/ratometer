package io.cfapps.ratometer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cfapps.ratometer.entity.support.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    @JsonIgnore
    @Column(name = "roles_id")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    private Long pk;

    @JsonIgnore
    @Column(name = "users_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "role_master_id")
    private Long roleMasterId;

    public Long getPk() {
        return pk;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleMasterId() {
        return roleMasterId;
    }

    public void setRoleMasterId(Long roleMasterId) {
        this.roleMasterId = roleMasterId;
    }
}
