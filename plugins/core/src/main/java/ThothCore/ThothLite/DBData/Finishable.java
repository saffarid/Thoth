package ThothCore.ThothLite.DBData;

import java.util.Date;

public interface Finishable {

    /**
     * Функция возвращает дату завершения
     * */
    Date finishDate();

    /**
     * Действие при наступлении даты завершения
     * */
    void finish();

    /**
     * Информация об объекте
     * */
    String message();

}
