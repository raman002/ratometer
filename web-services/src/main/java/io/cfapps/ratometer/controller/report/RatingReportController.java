package io.cfapps.ratometer.controller.report;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.model.dto.report.RatingReportDTO;
import io.cfapps.ratometer.service.report.RatingReportService;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class RatingReportController implements ValidationErrorProcessor {

    private static final Logger log = LoggerFactory.getLogger(RatingReportService.class);
    private RatingReportService ratingReportService;

    public RatingReportController(RatingReportService ratingReportService) {
        this.ratingReportService = ratingReportService;
    }

    @GetMapping("/{teamUid}/get-rating-report-by-team")
    public ResponseEntity<Response<List<RatingReportDTO>>> getRatingReportByTeam(@PathVariable("teamUid")UUID teamUid) {
        try {
            return ResponseEntity.ok(Response.ok(ratingReportService.getRatingReportByTeamUid(teamUid)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while fetching report for the team : " + teamUid));
    }
}
