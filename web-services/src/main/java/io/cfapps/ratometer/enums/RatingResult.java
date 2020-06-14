package io.cfapps.ratometer.enums;

public enum RatingResult {
    INITIAL("Initial", 1),
    PARTIALLY("Partially", 2),
    OPERATING("Operating", 3),
    ADAPTING("Adapting", 4),
    INNOVATING("Innovating", 5);

    private String result;
    private int order;

    RatingResult(String result, int order) {
        this.result = result;
        this.order = order;
    }

    public String getResultValue() {
        return result;
    }

    public static RatingResult getOrder(int order) {
        for (RatingResult value : RatingResult.values()) {
            if (value.order == order) return value;
        }

        return null;
    }
}
