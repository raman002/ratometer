package io.cfapps.ratometer.repository;

import io.cfapps.ratometer.entity.Rating;
import io.cfapps.ratometer.enums.CategoriesType;
import io.cfapps.ratometer.model.dto.report.RatingReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Boolean existsRatingByUsersId(Long usersId);

    Boolean existsRatingByUsersIdAndQuarter(Long usersId, Integer quarter);

    @Query("select new io.cfapps.ratometer.model.dto.report.RatingReportDTO(tm.teamName, avg(cm.optionOrderId), r.quarter)" +
            " from TeamMaster tm" +
            " join Team t on t.teamMasterUid = tm.uuid" +
            " join Rating r on r.usersId = t.userId" +
            " join CategoriesMaster cm on cm.categoriesMasterId = r.categoryId" +
            " where cm.categoriesType = :categoryType and tm.uuid = :teamUid and tm.isActive = true and tm.isDeleted = false" +
            " group by r.quarter")
    List<RatingReportDTO> getRatingReportByTeamUid(@Param("teamUid") UUID teamUid, @Param("categoryType") CategoriesType categoryType);
}
