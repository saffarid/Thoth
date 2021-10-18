package ThothCore.ThothLite.DBData.Tables;

import Database.TableColumn;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface TableReadable {
    void readTable(List<HashMap<String, Object>> data) throws ParseException;
    void readTable(ResultSet resultSet);
    void readTableWithTableColumn(List<HashMap<TableColumn, Object>> data);
}
