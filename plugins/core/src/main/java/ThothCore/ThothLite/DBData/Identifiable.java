package ThothCore.ThothLite.DBData;

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
