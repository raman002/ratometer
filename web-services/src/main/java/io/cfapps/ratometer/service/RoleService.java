package io.cfapps.ratometer.service;

import io.cfapps.ratometer.entity.Role;
import io.cfapps.ratometer.repository.RoleRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RoleService extends BaseService<RoleRepository, Role, Long> {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<String> getRoleNamesByUserId(Long userId) {
        return repository.findRoleNamesByUserId(userId);
    }

    public List<String> getRoleNamesByUsername(String username) {
        return repository.getRoleNamesByUsername(username);
    }
}
