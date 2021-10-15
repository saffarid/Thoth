package ThothCore.ThothLite.DBData.DBDataElement;

import java.util.Date;

public interface Finishable {

    /**
     * @return дата завершения
     * */
    Date finishDate();

    /**
     * @param finishDate дата завершения
     * */
    void setFinishDate(Date finishDate);

    /**
     * Действие при наступлении даты завершения
     * */
    void finish();

    /**
     * Информация об объекте
     * */
    String message();

}
