package io.cfapps.ratometer.controller;

import io.cfapps.ratometer.entity.Rating;
import io.cfapps.ratometer.model.dto.RatingDTO;
import io.cfapps.ratometer.service.RatingService;
import io.cfapps.ratometer.service.UserService;
import io.cfapps.ratometer.service.master.CategoriesMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private static final Logger log = LoggerFactory.getLogger(RatingController.class);
    private final UserService userService;
    private final RatingService ratingService;
    private final CategoriesMasterService categoriesMasterService;


    public RatingController(UserService userService, RatingService ratingService, CategoriesMasterService categoriesMasterService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.categoriesMasterService = categoriesMasterService;
    }

    @PostMapping("/submit-user-ratings")
    public ResponseEntity<Response<String>> submitUserRatings(@RequestHeader("username") String username,
                                                              @RequestBody List<RatingDTO> ratingDTOS) {

        Long usersId = userService.findPrimaryKeyByUsername(username);

        if (ratingService.existsRatingByUsersId(usersId)) {
            return ResponseEntity.ok(Response.ok("Rating has been submitted!"));
        }

        try {
            for (RatingDTO ratingDTO : ratingDTOS) {
                Rating rating = new Rating();
                rating.setQuarter(ratingDTO.getQuarter());
                rating.setCategoryId(categoriesMasterService.findPrimaryKeyByCategoryUid(ratingDTO.getOptionUid()));
                rating.setUsersId(usersId);

                ratingService.save(rating);
            }
        } catch (Exception e) {
            log.error("Something went wrong while submitting user ratings for user : {}", username, e);
        }

        return ResponseEntity.ok(Response.ok("Rating(s) submitted successfully!"));
    }

    @PostMapping("/rating-exists")
    public ResponseEntity<Response<String>> submitUserRatings(@RequestBody RatingDTO ratingDTO) {

        Long usersId = userService.findPrimaryKeyByUsername(ratingDTO.getUsername());

        if (ratingService.existsRatingByUsersIdAndQuarter(usersId, ratingDTO.getQuarter())) {
            return ResponseEntity.ok(Response.ok("Rating has been submitted!"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response
                        .of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while checking rating for: %s", ratingDTO.getUsername()));
    }
}
