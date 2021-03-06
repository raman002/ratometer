package io.cfapps.ratometer.controller;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.Team;
import io.cfapps.ratometer.model.dto.AssignTeamDTO;
import io.cfapps.ratometer.service.TeamService;
import io.cfapps.ratometer.service.UserService;
import io.cfapps.ratometer.service.master.TeamMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController implements ValidationErrorProcessor {

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);
    private final TeamService teamService;
    private final TeamMasterService teamMasterService;
    private final UserService userService;

    public TeamController(TeamService teamService, TeamMasterService teamMasterService, UserService userService) {
        this.teamService = teamService;
        this.teamMasterService = teamMasterService;
        this.userService = userService;
    }

    @PostMapping("/assign-teams")
    public ResponseEntity<Response<String>> assignTeams(@RequestBody List<AssignTeamDTO> assignTeamDTOS) {
        try {
            List<Team> teams = new ArrayList<>();

            for (AssignTeamDTO teamDTO : assignTeamDTOS) {
                Team team = new Team();
                team.setTeamMasterUid(teamDTO.getTeamUid());
                team.setTeamsMasterId(teamMasterService.findByUid(teamDTO.getTeamUid()).getTeamsMasterId());
                team.setUserId(userService.findByUuid(teamDTO.getUserUid()).getUsersId());

                teams.add(team);
            }
            teamService.saveAll(teams);
            return ResponseEntity.ok(Response.ok("Team(s) are assigned successfully!"));
        } catch (Exception e) {

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Response.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while assigning teams to the members!"));
    }
}
