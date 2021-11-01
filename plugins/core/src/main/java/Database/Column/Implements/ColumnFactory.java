package Database.Column.Implements;

import Database.Column.ColumnTypes;
import Database.Column.TableColumn;

public class ColumnFactory {

    public TableColumn getColumn(ColumnTypes type){
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
