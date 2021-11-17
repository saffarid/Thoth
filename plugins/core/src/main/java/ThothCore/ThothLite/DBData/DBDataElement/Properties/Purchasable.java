package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Composite;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.HasPartner;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts.Pricing;

public interface Purchasable
    extends Identifiable
        , Composite
        , HasPartner
        , Finishable
{

    /**
     * Функция возвращает статус доставки
     * */
    boolean isDelivered();
    /**
     * Функция устанавливает покупку в доставленное состояние
     * */
    void delivered();

}
