package io.cfapps.ratometer.model.dto;

import java.io.Serializable;

public class OptionsDTO implements Serializable {
    private Long value;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
