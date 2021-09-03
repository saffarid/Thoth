package ThothCore.SandBox;

import Database.ContentValues;
import Database.DataBaseManager;
import Database.Table;
import Database.WhereValues;

import java.sql.SQLException;
import java.util.List;

public class SandBox {

    /**
     * Менеджер для работы с запросами БД
     * */
    private DataBaseManager dbManager;

    public SandBox() throws SQLException, ClassNotFoundException {
        dbManager = new DataBaseManager();
    }

    /**
     * Функция создает таблицу в пользовательской БД
     * */
    public void createTable(Table table){

    }

    /**
     * Функция создает триггер для пользовательской БД
     * */
    public void createTrigger(){

    }

//    /**
//     * Функция возвращает список созданных таблиц
//     * */
//    public List<Table> getTablesList(){
//        dbManager.select()
//    }

    /**
     * Функция вносит запись в таблицу
     * */
    public void insert(Table table,
                       ContentValues contentValues){

    }

    /**
     * Функция удаляет запись из таблицы
     * */
    public void removeRow(Table table,
                          WhereValues whereValues){

    }

    /**
     * Функция удаляет таблицу из БД
     * */
    public void removeTable(Table table){

    }

    /**
     * Функция редактирует запись в таблице пользовательской БД
     * */
    public void update(Table table,
                       ContentValues contentValues,
                       WhereValues whereValues){

    }

}
