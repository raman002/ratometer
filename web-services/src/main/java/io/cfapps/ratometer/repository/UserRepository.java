package io.cfapps.ratometer.repository;

import io.cfapps.ratometer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIsDeleted(Boolean isDeleted);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    User findUserByUuid(UUID uuid);
    User findUserByUsernameAndIsDeletedFalse(String username);
}
