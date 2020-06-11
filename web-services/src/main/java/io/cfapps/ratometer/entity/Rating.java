package io.cfapps.ratometer.entity;

import io.cfapps.ratometer.entity.support.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "rating")
public class Rating extends BaseEntity {
    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "users_id", nullable = false)
    private Long usersId;

    @Column(nullable = false)
    private Integer quarter;

    @Column(name = "teams_id", nullable = false)
    private Long teamsId;

    public Long getRatingId() {
        return ratingId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Long getTeamsId() {
        return teamsId;
    }

    public void setTeamsId(Long teamsId) {
        this.teamsId = teamsId;
    }
}
