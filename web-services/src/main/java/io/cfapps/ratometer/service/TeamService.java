package io.cfapps.ratometer.service;

import io.cfapps.ratometer.entity.Role;
import io.cfapps.ratometer.repository.RoleRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamService extends BaseService<RoleRepository, Role, Long> {

    private final RoleRepository repository;

    public TeamService(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<String> getRoleNamesByUserId(Long userId) {
        return repository.findRoleNamesByUserId(userId);
    }
}
