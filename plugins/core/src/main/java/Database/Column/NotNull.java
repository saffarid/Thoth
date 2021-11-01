package Database.Column;

public interface NotNull extends TableColumn{

    boolean isNotNull();
    TableColumn setNotNull(boolean notNull);

}
