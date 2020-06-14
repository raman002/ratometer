package io.cfapps.ratometer.model.dto.support;

import io.cfapps.ratometer.model.dto.OptionsDTO;

import java.util.Comparator;

public class OptionNameComparator implements Comparator<OptionsDTO> {
    @Override
    public int compare(OptionsDTO o1, OptionsDTO o2) {
        return o1.getOptionOrderId().compareTo(o2.getOptionOrderId());
    }
}
