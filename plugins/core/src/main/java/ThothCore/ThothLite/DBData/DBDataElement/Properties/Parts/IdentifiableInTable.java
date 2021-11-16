package ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts;

/**
 * Интерфейс используется для "хранения" идентификатора таблицы в БД
 * */
public interface IdentifiableInTable {

    void setIdInTable(Object idInTable);
    Object getIdInTable();

}
