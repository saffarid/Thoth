package ThothCore.ThothLite;

import Database.Table;
import ThothCore.ThothLite.DBData.DBData;

import java.sql.SQLException;
import java.util.List;

public class ThothLite {

    private DataBaseLite database;
    private DBData dbData;

    public ThothLite() throws SQLException, ClassNotFoundException {

        dbData = DBData.getInstance();
        database = new DataBaseLite();

    }

}
