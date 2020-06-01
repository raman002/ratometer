package io.cfapps.ratometer.model.dto.support;

import io.cfapps.ratometer.model.dto.CategoriesDTO;
import io.cfapps.ratometer.model.dto.SubCategoriesDTO;

import java.util.Comparator;

public class SubCategoryNameComparator implements Comparator<SubCategoriesDTO> {

    @Override
    public int compare(SubCategoriesDTO o1, SubCategoriesDTO o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
