package Database.Column.Implements;

import Database.Column.Autoincrement;
import Database.Column.DataTypes;
import Database.Column.PrimaryKey;
import Database.Column.TableColumn;
import Database.Exceptions.NotSupportedOperation;

public class PrimaryKeyAutoncrement
        extends Column
        implements PrimaryKey, Autoincrement {

    private final String templateForCreate = "`%1s` %2s primary key autoincrement";

    public PrimaryKeyAutoncrement() {
        name = "id";
        type = DataTypes.ID;
    }

    @Override
    public TableColumn setName(String name) throws NotSupportedOperation {
        throw new NotSupportedOperation();
    }

    @Override
    public TableColumn setType(DataTypes type) throws NotSupportedOperation {
        throw new NotSupportedOperation();
    }

    @Override
    public String comandForCreate() {
        return String.format(templateForCreate, name, type.getDataType());
    }
}
