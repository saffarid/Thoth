package ThothCore.ThothLite.DBData.DBDataElement.Properties.Parts;

import java.time.LocalDate;

public interface Startable {

    /**
     * @return  дата начала
     * */
    LocalDate startDate();

    /**
     * @param startDate дата начала
     * */
    void setStartDate(String startDate);

}