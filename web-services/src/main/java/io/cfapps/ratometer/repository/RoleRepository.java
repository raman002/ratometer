package io.cfapps.ratometer.repository;

import io.cfapps.ratometer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByIsDeleted(Boolean isDeleted);

    @Query("select rm.roleName from Role r join RoleMaster rm on r.roleMasterId = rm.pk" +
            " where r.userId = :userId and r.isActive = true and r.isDeleted = false")
    List<String> findRoleNamesByUserId(@Param("userId") Long userId);

    @Query("select rm.roleName from Role r join RoleMaster rm on r.roleMasterId = rm.pk" +
            " join User u on u.pk = r.userId" +
            " where u.username = :username and r.isActive = true and r.isDeleted = false")
    List<String> getRoleNamesByUsername(@Param("username") String username);
}
