package ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;

/**
 * Реализация интерфейса делает объект исчисляемым
 * */
public interface Countable {

    /**
     * @return кол-во
     * */
    Double getCount();

    /**
     * @param count новое кол-во
     * */
    void setCount(Double count);

    /**
     * @return единицы измерения
     * */
    Listed getCountType();

    /**
     * @param countType новая единица измерения
     * */
    void setCountType(Listed countType);

}
