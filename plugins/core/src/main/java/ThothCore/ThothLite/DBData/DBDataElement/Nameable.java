package ThothCore.ThothLite.DBData.DBDataElement;

public interface Nameable {

    /**
     * @return наименование объекта
     * */
    String getName();

    /**
     * Функция устанавливает наименование объекта
     * @param name устанавливаемое наименование объекта
     * */
    void setName(String name);

}
