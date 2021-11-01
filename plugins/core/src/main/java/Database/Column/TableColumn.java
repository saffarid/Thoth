package Database.Column;

import Database.Column.Implements.CustomColumn;
import Database.Column.Implements.PrimaryKeyAutoncrement;
import Database.Column.Implements.PrimaryKeyCustom;
import Database.Exceptions.NotSupportedOperation;
import Database.Table;

public interface TableColumn {

    String getName();
    TableColumn setName(String name) throws NotSupportedOperation;

    DataTypes getType();
    TableColumn setType(DataTypes type) throws NotSupportedOperation;

    Table getTable();
    TableColumn setTable(Table table);

    String comandForCreate();

     static TableColumn getInstance(ColumnTypes type){
        switch (type){
            case CUSTOM_COLUMN:{
                return new CustomColumn();
            }
            case PRIMARYKEY_CUSTOM:{
                return new PrimaryKeyCustom();
            }
            case PRIMARYKEY_AUTOINCREMENT:{
                return new PrimaryKeyAutoncrement();
            }
            default:{
                return null;
            }
        }
    }
}
