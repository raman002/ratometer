package io.cfapps.ratometer.repository.master;

import io.cfapps.ratometer.entity.master.CategoriesMaster;
import io.cfapps.ratometer.entity.master.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesMasterRepository extends JpaRepository<CategoriesMaster, Long> {

    List<CategoriesMaster> findAllByIsDeleted(Boolean isDeleted);
}
