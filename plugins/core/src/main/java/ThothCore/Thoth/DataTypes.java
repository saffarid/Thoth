package ThothCore.Thoth;

/**
 * Класс предназначен для передачи пользователю информации о существующих типах данных
 * */
public class DataTypes {

    /**
     * Как тип данных будет отображаться у пользователя на экране
     * */
    private String user;
    /**
     * Какой тип данных соответсвует в Java
     * */
    private String java;
    /**
     * Как этот тип записывается в запросах SQL
     * */
    private String sql;

    public DataTypes(String user, String java, String sql) {
        this.user = user;
        this.java = java;
        this.sql = sql;
    }

    public String getUser() {
        return user;
    }

    public String getJava() {
        return java;
    }

    public String getSql() {
        return sql;
    }

    @Override
    public String toString() {
        return getUser();
    }
}
