package ThothCore.ThothLite;

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

}
