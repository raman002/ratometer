package io.cfapps.ratometer.service.master;

import io.cfapps.ratometer.entity.master.CategoriesMaster;
import io.cfapps.ratometer.repository.master.CategoriesMasterRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoriesMasterService extends BaseService<CategoriesMasterRepository, CategoriesMaster, Long> {

    private final CategoriesMasterRepository repository;

    public CategoriesMasterService(CategoriesMasterRepository repository, CategoriesMasterRepository repository1) {
        super(repository);
        this.repository = repository1;
    }

    public List<CategoriesMaster> findAllByIsDeleted() {
        return repository.findAllByIsDeleted(false);
    }
}
