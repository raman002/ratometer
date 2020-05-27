package io.cfapps.ratometer.controller.master;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.master.TeamMaster;
import io.cfapps.ratometer.service.master.TeamMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamMasterController implements ValidationErrorProcessor {

    private final TeamMasterService teamMasterService;

    public TeamMasterController(TeamMasterService teamMasterService) {
        this.teamMasterService = teamMasterService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response<List<TeamMaster>>> getAll() {
        return ResponseEntity.ok(Response.ok(teamMasterService.findAllByIsDeleted()));
    }
}
