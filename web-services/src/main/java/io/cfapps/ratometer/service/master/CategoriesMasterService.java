package io.cfapps.ratometer.service.master;

import io.cfapps.ratometer.entity.master.CategoriesMaster;
import io.cfapps.ratometer.repository.master.CategoriesMasterRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CategoriesMasterService extends BaseService<CategoriesMasterRepository, CategoriesMaster, Long> {

    private final CategoriesMasterRepository repository;

    public CategoriesMasterService(CategoriesMasterRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<CategoriesMaster> findAllByIsDeleted() {
        return repository.findAllByIsDeleted(false);
    }

    public Long findPrimaryKeyByCategoryUid(UUID uuid) {
        return repository.findPrimaryKeyByCategoryUid(uuid);
    }
}
