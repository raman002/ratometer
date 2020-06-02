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

    public RatingService(RatingRepository repository, RatingRepository repository1) {
        super(repository);
        this.repository = repository1;
    }

    public Boolean existsRatingByUsersId(Long usersId) {
        return repository.existsRatingByUsersId(usersId);
    }
}