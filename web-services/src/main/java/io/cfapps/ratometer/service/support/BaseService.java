package io.cfapps.ratometer.service.support;

import io.cfapps.ratometer.entity.support.AbstractBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public abstract class BaseService<Repository extends JpaRepository<Entity, ID>, Entity extends AbstractBaseEntity, ID> {

    private Repository repository;

    protected BaseService(Repository repository) {
        this.repository = repository;
    }

    @Transactional
    public Entity save(Entity entity) {
        beforeSave(entity);
        /* Setting the UUID manually instead of generating it through hibernate since an additional
        query is fired to fetch the UUID by hibernate after the record is saved.
        */
        entity.setUuid(UUID.randomUUID());
        repository.save(entity);
        afterSave(entity);

        return entity;
    }

    @Transactional
    public Entity update(Entity entity) {
        beforeUpdate(entity);
        repository.save(entity);
        afterUpdate(entity);

        return entity;
    }

    @Transactional
    public void delete(Entity entity) {
        beforeDelete(entity);
        entity.setActive(false);
        entity.setDeleted(false);
        repository.save(entity);
        afterDelete(entity);
    }

    protected void afterUpdate(Entity entity) {
    }

    protected void beforeUpdate(Entity entity) {
    }

    protected void beforeSave(Entity entity) {
    }

    protected void afterSave(Entity entity) {
    }

    protected void beforeDelete(Entity entity) {
    }

    protected void afterDelete(Entity entity) {
    }
}
