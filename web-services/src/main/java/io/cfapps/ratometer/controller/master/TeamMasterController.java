package io.cfapps.ratometer.controller.master;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.master.TeamMaster;
import io.cfapps.ratometer.model.dto.TeamMasterDTO;
import io.cfapps.ratometer.service.TeamService;
import io.cfapps.ratometer.service.master.TeamMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamMasterController implements ValidationErrorProcessor {

    private final TeamService teamService;
    private final TeamMasterService teamMasterService;

    public TeamMasterController(TeamService teamService, TeamMasterService teamMasterService) {
        this.teamService = teamService;
        this.teamMasterService = teamMasterService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response<List<TeamMasterDTO>>> getAll() {
        List<TeamMaster> teamList = teamMasterService.findAllByIsDeleted();
        List<TeamMasterDTO> teamMasterDTOS = new ArrayList<>();

        for (TeamMaster teamMaster : teamList) {
            TeamMasterDTO teamMasterDTO = new TeamMasterDTO();
            teamMasterDTO.setSize(teamService.getTeamSize(teamMaster.getPk()));
            teamMasterService.copyProperties(teamMaster, teamMasterDTO);
            teamMasterDTOS.add(teamMasterDTO);
        }

        return ResponseEntity.ok(Response.ok(teamMasterDTOS));
    }
}
