package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class RatingDTO implements Serializable {

    private UUID optionUid;
    private Integer orderId;
    private Integer quarter;
    private String username;

    public UUID getOptionUid() {
        return optionUid;
    }

    public void setOptionUid(UUID optionUid) {
        this.optionUid = optionUid;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
