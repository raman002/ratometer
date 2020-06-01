package io.cfapps.ratometer.service.master;

import io.cfapps.ratometer.entity.master.TeamMaster;
import io.cfapps.ratometer.model.dto.TeamMasterDTO;
import io.cfapps.ratometer.repository.master.TeamMasterRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    public TeamMaster findByUid(UUID uid) {
        return repository.findByUid(uid);
    }

    public void copyProperties(TeamMaster source, TeamMasterDTO target) {
        target.setUuid(source.getUuid());
        target.setTeamName(source.getTeamName());
    }
}
