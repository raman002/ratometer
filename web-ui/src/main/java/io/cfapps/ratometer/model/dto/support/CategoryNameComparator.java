package io.cfapps.ratometer.model.dto.support;

import io.cfapps.ratometer.model.dto.CategoriesDTO;

import java.util.Comparator;

public class CategoryNameComparator implements Comparator<CategoriesDTO> {

    @Override
    public int compare(CategoriesDTO o1, CategoriesDTO o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
