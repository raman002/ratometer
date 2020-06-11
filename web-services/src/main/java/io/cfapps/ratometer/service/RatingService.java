package io.cfapps.ratometer.service;

import io.cfapps.ratometer.entity.Rating;
import io.cfapps.ratometer.repository.RatingRepository;
import io.cfapps.ratometer.service.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RatingService extends BaseService<RatingRepository, Rating, Long> {

    private final RatingRepository repository;

    public RatingService(RatingRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Boolean existsRatingByUsersId(Long usersId) {
        return repository.existsRatingByUsersId(usersId);
    }

    public Boolean existsRatingByUsersIdAndQuarter(Long usersId, Integer quarter) {
        return repository.existsRatingByUsersIdAndQuarter(usersId, quarter);
    }
}