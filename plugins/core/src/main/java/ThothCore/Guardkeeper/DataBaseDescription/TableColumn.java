package ThothCore.Guardkeeper.DataBaseDescription;

public class TableColumn extends Database.TableColumn {

    public TableColumn(String name, String type, boolean isUnique, boolean isPrimaryKey, boolean isNotNull) {
        this.name = name;
        this.type = type;
        this.isUnique = isUnique;
        this.isPrimaryKey = isPrimaryKey;
        this.isNotNull = (isPrimaryKey)?(true):(isNotNull);
    }

    public TableColumn() {
        name = "";
        type = "";
        isUnique = false;
        isPrimaryKey = false;
        isNotNull = false;
    }

}
