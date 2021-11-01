package Database.Column;

public interface Unique extends TableColumn{

    boolean isUnique();
    TableColumn setUnique(boolean unique);

}
