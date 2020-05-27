package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoriesDTO implements Serializable {

    private Map<String, List<OptionsDTO>> subCategoriesMap = new HashMap<>();

    public Map<String, List<OptionsDTO>> getSubCategoriesMap() {
        return subCategoriesMap;
    }

    public void setSubCategoriesMap(Map<String, List<OptionsDTO>> subCategoriesMap) {
        this.subCategoriesMap = subCategoriesMap;
    }
}
