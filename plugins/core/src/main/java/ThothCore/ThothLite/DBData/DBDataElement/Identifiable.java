package ThothCore.ThothLite.DBData.DBDataElement;

public interface Identifiable {

    /**
     * @return Идентификатор объекта
     * */
    String getId();
    /**
     * Функция устанавливает идентификатор объекта.
     * @param id устанавливаемый идентификатор
     * */
    void setId(String id);

}
