package io.cfapps.ratometer.service;

import io.cfapps.ratometer.entity.Role;
import io.cfapps.ratometer.entity.User;
import io.cfapps.ratometer.model.dto.UserDTO;
import io.cfapps.ratometer.repository.UserRepository;
import io.cfapps.ratometer.service.master.RoleMasterService;
import io.cfapps.ratometer.service.support.BaseService;
import io.cfapps.ratometer.util.security.EncryptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

import static io.cfapps.ratometer.config.MessageSourceConfig.getMessage;

@Service
@Transactional(readOnly = true)
public class UserService extends BaseService<UserRepository, User, Long> {

    private final UserRepository repository;
    private final RoleService roleService;

    public UserService(UserRepository repository,
                       RoleMasterService roleMasterService,
                       RoleService roleService) {
        super(repository);
        this.repository = repository;
        this.roleService = roleService;
    }

    public User findByUuid(UUID uuid) {
        return repository.findUserByUuid(uuid);
    }

    public void validateNewUser(UserDTO userDTO) {
        /*
        * Check if the given user's username or email already exists, If yes then disallow this operation.
        * */
        if (repository.existsUserByUsername(userDTO.getUsername()))
            throw new ValidationException(getMessage("usr.err.username.already-exists", userDTO.getUsername()));

        if (repository.existsUserByEmail(userDTO.getEmail()))
            throw new ValidationException(getMessage("usr.err.email.already-exists", userDTO.getEmail()));
    }

    public void validateExistingUser(User user, String param) {
        if (user == null)
            throw new ValidationException(getMessage("usr.err.not-found", param));

    }

    @Override
    protected void beforeSave(User entity) {
        entity.setCreatedBy(1L);
        entity.setUpdatedBy(1L);
        entity.setPassword(EncryptionUtils.getBcryptHash(entity.getPassword()));
    }

    @Override
    protected void afterSave(User entity) {
        setRolesAndAccounts(entity);
    }

    private void setRolesAndAccounts(User user) {
        Long pk = user.getUsersId();
        Role role = createRole(pk);
        roleService.save(role);
    }

    private Role createRole(Long userId) {
        Role role = new Role();
        role.setUserId(userId);
        // Primary key value for default RoleMaster i.e. USER
        role.setRoleMasterId(1L);
        role.setCreatedBy(userId);
        role.setUpdatedBy(userId);
        return role;
    }

    public List<User> findAllByIsDeleted() {
        return repository.findAllByIsDeleted(false);
    }


    public void copyProperties(UserDTO source, User target) {
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getFullName() != null) target.setFullName(source.getFullName());
        if (source.getUsername() != null) target.setUsername(source.getUsername());
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getContactNo() != null) target.setContactNo(source.getContactNo());
    }

    public void copyProperties(User source, UserDTO target) {
        target.setEmail(source.getEmail());
        target.setFullName(source.getFullName());
        target.setUsername(source.getUsername());
        target.setUuid(source.getUuid());
    }

    public User findByUsername(String username) {
        return repository.findUserByUsernameAndIsDeletedFalse(username);
    }

    public Long findPrimaryKeyByUsername(String username) {
        return repository.findPrimaryKeyByUsername(username);
    }
}
