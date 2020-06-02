package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class RatingDTO implements Serializable {

    private UUID optionUid;
    private Integer orderId;

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
}
