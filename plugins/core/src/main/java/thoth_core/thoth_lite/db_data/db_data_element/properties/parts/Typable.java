package thoth_core.thoth_lite.db_data.db_data_element.properties.parts;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Listed;

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
