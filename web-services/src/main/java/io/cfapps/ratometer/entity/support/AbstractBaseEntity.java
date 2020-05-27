package io.cfapps.ratometer.entity.support;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Column(nullable = false, updatable = false)
    protected UUID uuid;

    @Version
    protected Integer version;

    @Column(name = "is_active")
    protected Boolean isActive;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
