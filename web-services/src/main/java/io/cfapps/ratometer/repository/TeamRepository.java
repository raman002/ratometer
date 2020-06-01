package io.cfapps.ratometer.repository;

import io.cfapps.ratometer.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByIsDeleted(Boolean isDeleted);

    @Query("select tm.teamName from TeamMaster tm join Team t on t.teamsMasterId = tm.pk" +
            " join User u on u.pk = t.userId where u.isActive = true and u.isDeleted = false" +
            " and tm.isActive = true and tm.isDeleted = false and u.username = :username")
    List<String> fetchTeamsByUsername(@Param("username") String username);

    @Query("select count(t.pk) from Team  t join TeamMaster tm on t.teamsMasterId = tm.pk" +
            " where tm.pk = :teamsMasterId group by t.teamsMasterId")
    Long getTeamSize(@Param("teamsMasterId") Long teamsMasterId);
}
