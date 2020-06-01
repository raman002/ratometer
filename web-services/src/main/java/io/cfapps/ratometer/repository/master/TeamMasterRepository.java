package io.cfapps.ratometer.repository.master;

import io.cfapps.ratometer.entity.master.TeamMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TeamMasterRepository extends JpaRepository<TeamMaster, Long> {

    List<TeamMaster> findAllByIsDeleted(Boolean isDeleted);

    @Query("select tm from TeamMaster tm where tm.uuid = :uid and tm.isActive = true and tm.isDeleted = false")
    TeamMaster findByUid(UUID uid);
}
