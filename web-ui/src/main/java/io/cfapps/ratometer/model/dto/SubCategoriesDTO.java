package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.*;

public class SubCategoriesDTO implements Serializable {

    private Long pk;
    private UUID uuid;
    private String name;

    private List<OptionsDTO> options = new ArrayList<>();


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OptionsDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsDTO> options) {
        this.options = options;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }
}
