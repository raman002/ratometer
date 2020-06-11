package io.cfapps.ratometer.repository;

import io.cfapps.ratometer.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Boolean existsRatingByUsersId(Long usersId);

    Boolean existsRatingByUsersIdAndQuarter(Long usersId, Integer quarter);
}
