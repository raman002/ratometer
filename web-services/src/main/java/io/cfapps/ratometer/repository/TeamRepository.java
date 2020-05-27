package io.cfapps.ratometer.repository;

import io.cfapps.ratometer.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByIsDeleted(Boolean isDeleted);
}
