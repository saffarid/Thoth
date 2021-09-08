package ThothCore.Thoth;

import Database.DataBaseSQL;
import ThothCore.EmptyDatabase.EmptyDatabase;

public class DataBase extends DataBaseSQL {
    public DataBase() {
        super();
    }

    public DataBase(EmptyDatabase template) {
        super(template);
    }
}
