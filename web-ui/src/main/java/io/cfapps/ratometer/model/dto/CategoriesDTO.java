package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesDTO implements Serializable {

    private Long pk;
    private  String name;
    private Long parentCategoryId;
    private String categoriesType;

    private Map<String, List<SubCategoriesDTO>> categoriesMap = new HashMap<>();

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
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

    public Map<String, List<SubCategoriesDTO>> getCategoriesMap() {
        return categoriesMap;
    }

    public void setCategoriesMap(Map<String, List<SubCategoriesDTO>> categoriesMap) {
        this.categoriesMap = categoriesMap;
    }
}
