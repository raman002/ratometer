package io.cfapps.ratometer.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cfapps.ratometer.entity.support.AbstractBaseEntity;
import io.cfapps.ratometer.enums.CategoriesType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "categories_master")
public class CategoriesMaster extends AbstractBaseEntity {

    @Id
    @Column(name = "categories_master_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @NotNull
    @Column(unique = true)
    private String name;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true)
    private CategoriesType categoriesType;

    @Column(name = "option_order_id")
    private Integer optionOrderId;

    public Long getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public CategoriesType getCategoriesType() {
        return categoriesType;
    }

    public void setCategoriesType(CategoriesType categoriesType) {
        this.categoriesType = categoriesType;
    }

    public Integer getOptionOrderId() {
        return optionOrderId;
    }

    public void setOptionOrderId(Integer optionOrderId) {
        this.optionOrderId = optionOrderId;
    }
}
