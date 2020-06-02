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
    @SequenceGenerator(name = "rating_id_seq", sequenceName = "rating_rating_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_id_seq")
    private Long ratingId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "users_id", nullable = false)
    private Long usersId;

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
}
