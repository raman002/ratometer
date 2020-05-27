package io.cfapps.ratometer.repository.master;

import io.cfapps.ratometer.entity.master.TeamMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMasterRepository extends JpaRepository<TeamMaster, Long> {

    List<TeamMaster> findAllByIsDeleted(Boolean isDeleted);
}
