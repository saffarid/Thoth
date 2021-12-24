package thoth_core.thoth_lite.config;


public enum DayBeforeDelivery {

    NEVER(-1),
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5)
    ;
    private int day;
    DayBeforeDelivery(int day) {
        this.day = day;
    }
    public int getDay() {
        return day;
    }
}
