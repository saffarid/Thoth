package ThothCore.ThothLite;

import Database.Table;

import java.sql.SQLException;
import java.util.List;

public class ThothLite {

    private DataBaseLite database;

    public ThothLite() throws SQLException, ClassNotFoundException {

        database = new DataBaseLite();

    }

    public List<Table> getTables(){
        return database.getTables();
    }
}
