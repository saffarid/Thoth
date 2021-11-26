package ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;

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
    Finance getCurrency();

    /**
     * Функция устанавливает валюту.
     * @param currency валюта
     * */
    void setCurrency(Finance currency);

}
