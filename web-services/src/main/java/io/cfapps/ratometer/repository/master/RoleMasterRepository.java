package io.cfapps.ratometer.repository.master;

import io.cfapps.ratometer.entity.master.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {

    List<RoleMaster> findAllByIsDeleted(Boolean isDeleted);

    RoleMaster findRoleMasterByRoleName(String roleName);
}
