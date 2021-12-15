package ThothCore.ThothLite;

public enum PeriodAutoupdateDatabase {

    NEVER(-1),
    ONE(1),
    FIVE(5),
    TEN(10),
    FEFTEEN(15),
    THIRTY(30),
    HOUR(60)
    ;

    private int period;

    PeriodAutoupdateDatabase(int period) {
        this.period = period;
    }
    public int getPeriod() {
        return period;
    }
}
