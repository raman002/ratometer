package io.cfapps.ratometer.service;

import io.cfapps.ratometer.entity.Team;
import io.cfapps.ratometer.repository.TeamRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamService extends BaseService<TeamRepository, Team, Long> {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<String> fetchTeamsByUsername(String username) {
        return repository.fetchTeamsByUsername(username);
    }

    public Long getTeamSize(Long teamsMasterId) {
        return repository.getTeamSize(teamsMasterId);
    }
}