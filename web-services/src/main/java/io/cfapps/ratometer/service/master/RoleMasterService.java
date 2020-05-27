package io.cfapps.ratometer.service.master;

import io.cfapps.ratometer.entity.master.RoleMaster;
import io.cfapps.ratometer.repository.master.RoleMasterRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleMasterService extends BaseService<RoleMasterRepository, RoleMaster, Long> {

    private final RoleMasterRepository repository;

    public RoleMasterService(RoleMasterRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public RoleMaster getDefaultRole() {
        return repository.findRoleMasterByRoleName("USER");
    }

    public List<RoleMaster> findAllByIsDeleted() {
        return repository.findAllByIsDeleted(false);
    }
}
