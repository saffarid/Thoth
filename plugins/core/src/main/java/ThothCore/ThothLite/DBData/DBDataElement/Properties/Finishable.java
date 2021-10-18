package ThothCore.ThothLite.DBData.DBDataElement.Properties;

import java.time.LocalDate;

public interface Finishable {

    int AUTOFINISH = 1;

    /**
     * @return дата завершения
     * */
    LocalDate finishDate();

    /**
     * @param finishDate дата завершения
     * */
    void setFinishDate(String finishDate);

    /**
     * Действие при наступлении даты завершения
     * */
    void finish();

    /**
     * Информация об объекте
     * */
    String message();

}
