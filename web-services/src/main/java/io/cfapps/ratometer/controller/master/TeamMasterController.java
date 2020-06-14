package io.cfapps.ratometer.controller.master;

import io.cfapps.ratometer.config.MessageSourceConfig;
import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.master.TeamMaster;
import io.cfapps.ratometer.model.dto.TeamMasterDTO;
import io.cfapps.ratometer.service.TeamService;
import io.cfapps.ratometer.service.master.TeamMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamMasterController implements ValidationErrorProcessor {
    private static final Logger log = LoggerFactory.getLogger(TeamMasterController.class);
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
            teamMasterDTO.setSize(teamService.getTeamSize(teamMaster.getTeamsMasterId()));
            teamMasterService.copyProperties(teamMaster, teamMasterDTO);
            teamMasterDTOS.add(teamMasterDTO);
        }

        return ResponseEntity.ok(Response.ok(teamMasterDTOS));
    }

    @PostMapping("/create-team")
    public ResponseEntity<Response<String>> createTeam(@RequestBody @Valid TeamMasterDTO teamMasterDTO,
                                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return processValidationErrors(bindingResult);
        }

        String teamName = teamMasterDTO.getTeamName();

        if (teamMasterService.isTeamExist(teamName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.of(HttpStatus.CONFLICT,
                    MessageSourceConfig.getMessage("team.err.already-exists", teamName)));
        }

        try {
            TeamMaster teamMaster = new TeamMaster();
            teamMaster.setTeamName(teamName);
            teamMasterService.save(teamMaster);

            return ResponseEntity.ok(Response.ok(MessageSourceConfig.getMessage("team.ok.created")));

        } catch (Exception ex) {
            log.error("Something went wrong while saving team: {}", teamName, ex);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.of(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong while saving team : " + teamName));
    }
}
