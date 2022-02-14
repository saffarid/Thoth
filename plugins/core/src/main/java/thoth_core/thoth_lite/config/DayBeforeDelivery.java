package thoth_core.thoth_lite.config;

public enum DayBeforeDelivery
    implements ConfigEnums
{

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
    @Override
    public String getText() {
        return String.valueOf(day);
    }
}
