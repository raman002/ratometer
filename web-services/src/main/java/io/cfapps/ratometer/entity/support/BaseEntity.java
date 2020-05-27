package io.cfapps.ratometer.entity.support;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity extends AbstractBaseEntity {

    @Column(name = "created_by", updatable = false, nullable = false)
    protected Long createdBy;

    @Column(name = "updated_by", nullable = false)
    protected Long updatedBy;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    protected LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    protected LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    protected LocalDateTime deletedOn;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(LocalDateTime deletedOn) {
        this.deletedOn = deletedOn;
    }
}
