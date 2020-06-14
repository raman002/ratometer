package io.cfapps.ratometer.model.dto;

import java.io.Serializable;

public class RatingReportDTO implements Serializable {

    private String teamName;
    private Double averageRating;
    private String ratingResult;
    private Integer quarter;

    public RatingReportDTO() {
    }

    public RatingReportDTO(String teamName, Double averageRating, Integer quarter) {
        this.teamName = teamName;
        this.averageRating = averageRating;
        this.quarter = quarter;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getRatingResult() {
        return ratingResult;
    }

    public void setRatingResult(String ratingResult) {
        this.ratingResult = ratingResult;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }
}
