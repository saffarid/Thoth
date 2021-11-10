package ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;

public interface Typable {

    /**
     * @return тип объекта
     * */
    Listed getType();

    /**
     * @param type устанавливаемый тип
     * */
    void setType(Listed type);

}
