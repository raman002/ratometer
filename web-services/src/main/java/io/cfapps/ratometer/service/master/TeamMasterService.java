package io.cfapps.ratometer.service.master;

import io.cfapps.ratometer.entity.master.TeamMaster;
import io.cfapps.ratometer.repository.master.TeamMasterRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamMasterService extends BaseService<TeamMasterRepository, TeamMaster, Long> {

    private final TeamMasterRepository repository;

    public TeamMasterService(TeamMasterRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<TeamMaster> findAllByIsDeleted() {
        return repository.findAllByIsDeleted(false);
    }
}
