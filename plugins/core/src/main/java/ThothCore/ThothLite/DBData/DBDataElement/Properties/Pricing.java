package ThothCore.ThothLite.DBData.DBDataElement.Properties;


/**
 * Ценовое свойство объекта
 * */
public interface Pricing {

    /**
     * @return цена объекта
     * */
    Double getPrice();

    /**
     * Функция устанавливает цену
     * @param price цена объекта
     * */
    void setPrice(Double price);

    /**
     * @return валюта
     * */
    String getCurrency();

    /**
     * Функция устанавливает валюту.
     * @param currency валюта
     * */
    void setCurrency(String currency);

}
