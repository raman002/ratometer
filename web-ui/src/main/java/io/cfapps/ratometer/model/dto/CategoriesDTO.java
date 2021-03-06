package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.*;

public class CategoriesDTO implements Serializable {

    private Long categoriesMasterId;
    private  String name;
    private Long parentCategoryId;
    private String categoriesType;
    private UUID uuid;
    private Integer optionOrderId;

    private List<SubCategoriesDTO> subCategories = new ArrayList<>();

    public Long getCategoriesMasterId() {
        return categoriesMasterId;
    }

    public void setCategoriesMasterId(Long categoriesMasterId) {
        this.categoriesMasterId = categoriesMasterId;
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

    public String getCategoriesType() {
        return categoriesType;
    }

    public void setCategoriesType(String categoriesType) {
        this.categoriesType = categoriesType;
    }

    public List<SubCategoriesDTO> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoriesDTO> subCategories) {
        this.subCategories = subCategories;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getOptionOrderId() {
        return optionOrderId;
    }

    public void setOptionOrderId(Integer optionOrderId) {
        this.optionOrderId = optionOrderId;
    }
}
