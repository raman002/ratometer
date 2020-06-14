package io.cfapps.ratometer.service.report;

import io.cfapps.ratometer.enums.CategoriesType;
import io.cfapps.ratometer.enums.RatingResult;
import io.cfapps.ratometer.model.dto.report.RatingReportDTO;
import io.cfapps.ratometer.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RatingReportService {

    private RatingRepository ratingRepository;

    public RatingReportService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingReportDTO> getRatingReportByTeamUid(UUID teamUid) {
        List<RatingReportDTO> ratingReportDTOS = ratingRepository
                .getRatingReportByTeamUid(teamUid, CategoriesType.OPTION);

        for (RatingReportDTO ratingReportDTO : ratingReportDTOS) {
            double rating = BigDecimal.valueOf(ratingReportDTO.getAverageRating()).setScale(1, RoundingMode.DOWN).doubleValue();
            ratingReportDTO.setAverageRating(rating);
            Double ratingValue = Math.floor(rating);
            ratingReportDTO.setRatingResult(RatingResult.getOrder(ratingValue.intValue()).getResultValue());
        }

        return ratingReportDTOS;
    }
}
